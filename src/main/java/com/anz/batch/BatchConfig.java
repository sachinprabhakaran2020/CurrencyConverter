package com.anz.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import com.anz.model.CurrencyMapping;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Value("classPath:/input/CurrencyMappingMatrix.csv")
	private Resource currencyMappingResource;
	
	@Bean
	public Job readCSVFileJob() {
		return jobBuilderFactory.get("readCSVFileJob").incrementer(new RunIdIncrementer()).start(step()).build();
	}

	@Bean
	public Step step() {
		return stepBuilderFactory.get("step").<CurrencyMapping, CurrencyMapping>chunk(5).reader(reader())
				.processor(processor()).writer(writer()).build();
	}

	@Bean
	public FlatFileItemReader<CurrencyMapping> reader() {
		FlatFileItemReader<CurrencyMapping> itemReader = new FlatFileItemReader<CurrencyMapping>();
		itemReader.setLineMapper(lineMapper());
		itemReader.setLinesToSkip(1);
		itemReader.setResource(currencyMappingResource);
		return itemReader;
	}

	@Bean
	public LineMapper<CurrencyMapping> lineMapper() {
		DefaultLineMapper<CurrencyMapping> lineMapper = new DefaultLineMapper<CurrencyMapping>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames(new String[] { "id", "fromCurrency", "toCurrency", "process" });
		lineTokenizer.setIncludedFields(new int[] { 0, 1, 2, 3 });
		BeanWrapperFieldSetMapper<CurrencyMapping> fieldSetMapper = new BeanWrapperFieldSetMapper<CurrencyMapping>();
		fieldSetMapper.setTargetType(CurrencyMapping.class);
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		return lineMapper;
	}

	@Bean
	public JdbcBatchItemWriter<CurrencyMapping> writer() {
		JdbcBatchItemWriter<CurrencyMapping> itemWriter = new JdbcBatchItemWriter<CurrencyMapping>();
		itemWriter.setDataSource(dataSource());
		itemWriter.setSql(
				"INSERT INTO CURRENCY_MAPPING (ID, FROM_CURRENCY, TO_CURRENCY, PROCESS) VALUES (:id, :fromCurrency, :toCurrency, :process)");
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<CurrencyMapping>());
		return itemWriter;
	}

//	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
		return embeddedDatabaseBuilder.addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
				.addScript("classpath:org/springframework/batch/core/schema-h2.sql").addScript("classpath:CurrencyMapping.sql")
				.addScript("classpath:ConversionRate.sql").addScript("classpath:data.sql")
				.setType(EmbeddedDatabaseType.H2).build();
	}

	@Bean
	public ItemProcessor<CurrencyMapping, CurrencyMapping> processor() {
		return new DBLogProcessor();
	}
}
