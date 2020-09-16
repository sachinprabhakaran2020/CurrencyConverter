package com.anz.repository;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("conversionRateImpl")
public class ConversionRateImpl {
	
	@Autowired
	ConversionRateRepository conversionRateRepository;
	
	public Double fetchConversionRate(String fromCurrency, String toCurrency) throws SQLException{
		Double conversionRate=1d;
		conversionRate = conversionRateRepository.findConversionRate(fromCurrency, toCurrency);
		return conversionRate;
	}

}
