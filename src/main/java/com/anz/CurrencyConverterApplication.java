package com.anz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.anz.service.CurrencyConverterService;

@SpringBootApplication
public class CurrencyConverterApplication implements CommandLineRunner{
	
	@Autowired
	CurrencyConverterService currencyConverterService;
	
	public static void main(String[] args) {
		SpringApplication.run(CurrencyConverterApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		currencyConverterService.showGUI();
	}

}