package com.anz.helper;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anz.constants.ProcessConstants;
import com.anz.model.CurrencyConverter;
import com.anz.repository.ConversionRateImpl;
import com.anz.repository.CurrencyRepositoryImpl;

@Component("currencyConverterHelper")
public class CurrencyConverterHelper {
	
	@Autowired
	CurrencyRepositoryImpl currencyRepositoryImpl;
	
	@Autowired
	ConversionRateImpl conversionRateImpl;
	
	/**
	 * Call when direct conversion rates are available
	 * @param currencyConverter
	 * @return
	 */
	public double directConversion(CurrencyConverter currencyConverter) {
		return convertCurrency(currencyConverter.getFromCurrency(),currencyConverter.getToCurrency(),currencyConverter.getAmount());
	}
	
	/**
	 * Call when conversion has to done through USD
	 * @param currencyConverter
	 * @return
	 */
	public Double usdConversion(CurrencyConverter currencyConverter) {
		double usdRate = convertCurrency(currencyConverter.getFromCurrency(),ProcessConstants.USD.toString(),currencyConverter.getAmount());
		try {
			usdRate =  populateAndConvert(ProcessConstants.USD.toString(),currencyConverter.getToCurrency(),usdRate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usdRate;
	}
	
	/**
	 * Call when conversion has to done through EUR
	 * @param currencyConverter
	 * @return
	 */
	public double eurConversion(CurrencyConverter currencyConverter) {
		double eurRate = convertCurrency(currencyConverter.getFromCurrency(),ProcessConstants.EUR.toString(),currencyConverter.getAmount());
		try {
			eurRate = populateAndConvert(ProcessConstants.EUR.toString(),currencyConverter.getToCurrency(),eurRate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return eurRate;
	}
	
	/**
	 * Call when from and to currencies have to inverted and converted
	 * @param currencyConverter
	 * @return
	 */
	public double invertedConversion(CurrencyConverter currencyConverter) {
		return convertCurrency(currencyConverter.getToCurrency(),currencyConverter.getFromCurrency(),currencyConverter.getAmount());
	}
	
	/**
	 * Call when conversion process is based on ratio
	 * @param currencyConverter
	 * @return
	 */
	public double ratioConversion(CurrencyConverter currencyConverter) {
		return convertCurrencyBasedOnRatio(currencyConverter.getToCurrency(),currencyConverter.getFromCurrency(),currencyConverter.getAmount());
	}
	
	/**
	 * THis method fetches the conversion rate and applies it to the amount
	 * @param fromCurrency
	 * @param toCurrency
	 * @param amount
	 * @return
	 */
	private Double convertCurrency(String fromCurrency, String toCurrency, double amount) {
		double convertedCurrencyValue = 0d;
		double conversionRate = 0;
		try {
			conversionRate = conversionRateImpl.fetchConversionRate(fromCurrency, toCurrency);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		convertedCurrencyValue = amount*conversionRate;
		return convertedCurrencyValue;
	}
	
	/**
	 * Convert currencies based on ratio
	 * @param fromCurrency
	 * @param toCurrency
	 * @param amount
	 * @return
	 */
	private Double convertCurrencyBasedOnRatio(String fromCurrency, String toCurrency, double amount) {
		double convertedCurrencyValue = 0d;
		double conversionRate = 0;
		try {
			conversionRate = conversionRateImpl.fetchConversionRate(fromCurrency, toCurrency);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		convertedCurrencyValue = amount*conversionRate;
		return convertedCurrencyValue;
	}
	
	/**
	 * Call to check the conversion process to make
	 * @param currencyConverter
	 * @return
	 */
	public Double convertCurrencies(CurrencyConverter currencyConverter) {
		double convertedAmount = 0d;
		String process = null;
		try {
			process = getProcess(currencyConverter.getFromCurrency(),currencyConverter.getToCurrency());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(process!=null && !process.isEmpty()) {
			currencyConverter.setProcess(process);
			convertedAmount = convertBasedOnProcess(currencyConverter);
		}
		
		return convertedAmount;
	}
	
	/**
	 * Call to check the process name
	 * @param fromCurrency
	 * @param toCurrency
	 * @return
	 * @throws SQLException 
	 */
	private String getProcess(String fromCurrency,String toCurrency) throws SQLException {
		return currencyRepositoryImpl.fetchProcess(fromCurrency, toCurrency);
	}
	
	/**
	 * Identify the conversion process to take
	 * @param currencyConverter
	 * @return
	 */
	private Double convertBasedOnProcess(CurrencyConverter currencyConverter) {
		double convertedAmount = 0d;
		switch(ProcessConstants.valueOf(currencyConverter.getProcess())) {
	      case D:
	        System.out.println("Direct conversion");
	        convertedAmount = directConversion(currencyConverter);
	        break;
	      case USD:
	        System.out.println("Convert via USD");
	        convertedAmount = usdConversion(currencyConverter);
	        break;
	      case EUR:
	        System.out.println("Convert via EUR");
	        convertedAmount = eurConversion(currencyConverter);
	        break;
	      case INV:
		    System.out.println("Convert via Inversion of currencies");
		    convertedAmount = invertedConversion(currencyConverter);
		    break;
		  default : 
			  System.out.println("Convert via Ratio");
			  convertedAmount = ratioConversion(currencyConverter);
			  break;
	    }
		return convertedAmount;
	}
	
	/**
	 * Special method for converting indirect currency mappings
	 * @param fromCurrency
	 * @param toCurrency
	 * @param amount
	 * @return
	 * @throws SQLException 
	 */
	private double populateAndConvert(String fromCurrency, String toCurrency, double amount) throws SQLException {
		CurrencyConverter converter = new CurrencyConverter();
		converter.setFromCurrency(fromCurrency);
		converter.setToCurrency(toCurrency);
		converter.setAmount(amount);
		converter.setProcess(getProcess(converter.getFromCurrency(),converter.getToCurrency()));
		return convertBasedOnProcess(converter);
	}

}
