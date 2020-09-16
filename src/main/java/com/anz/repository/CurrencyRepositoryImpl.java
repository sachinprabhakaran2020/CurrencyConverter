package com.anz.repository;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.anz.model.CurrencyMapping;

@Component("currencyRepositoryImpl")
public class CurrencyRepositoryImpl {
	
	@Autowired
	CurrencyRepository currencyRepository;
	
	public String fetchProcess(String fromCurrency, String toCurrency) throws SQLException{
		String process = null;
		process = currencyRepository.findConversionProcess(fromCurrency, toCurrency);
		return process;
	}

}
