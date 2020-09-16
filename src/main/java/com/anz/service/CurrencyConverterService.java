package com.anz.service;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anz.helper.CurrencyConverterHelper;
import com.anz.model.CurrencyConverter;
import com.anz.repository.CurrencyRepository;

/**
 * Business logic for currency conversion is written here
 * @author sachin
 *
 */
@Component("currencyConverterService")
public class CurrencyConverterService {
	
	@Autowired
	CurrencyConverterHelper currencyConverterHelper;
	
	@Autowired
	CurrencyRepository currencyRepository;
	
	/**
	 * To prompt users for input and show converted currency as output
	 */
	public void showGUI() {
		CurrencyConverter currencyConverter = new CurrencyConverter();
		try(Scanner scan = new Scanner(System.in)){
			System.out.println("Welcome to Currency Converter");
			System.out.println("***********************************************");
			boolean validFromCurrency = false;
			while(!validFromCurrency) {
				System.out.println("Please enter a valid From-Currency : ");
				String fromCurrency = scan.nextLine();
				if(fromCurrency!=null 
						&& !fromCurrency.isEmpty()
						&& fromCurrency.toCharArray().length==3) {
					currencyConverter.setFromCurrency(fromCurrency);
					validFromCurrency = true;
				}
			}
			
			boolean validToCurrency = false;
			while(!validToCurrency) {
				System.out.println("Please enter a valid To-Currency : ");
				String toCurrency = scan.nextLine();
				if(toCurrency!=null 
						&& !toCurrency.isEmpty()
						&& toCurrency.toCharArray().length==3) {
					currencyConverter.setToCurrency(toCurrency);
					validToCurrency = true;
				}
			}
			
			boolean validAmount = false;
			while(!validAmount) {
				System.out.println("Please enter the Amount : ");
				double amount = scan.nextDouble();
				if(amount!=0) {
					currencyConverter.setAmount(amount);
					validAmount = true;
				}
			}
			System.out.println("From Currency : "+currencyConverter.getFromCurrency()
					+"  Amount : "+currencyConverter.getAmount()
					+" \n To Currency : "+currencyConverter.getToCurrency()
					+"  Amount : "+currencyConverterHelper.convertCurrencies(currencyConverter));
			System.out.println("************************************************");
		}catch(Exception e) {
			System.out.println("Exception occured : "+e.getMessage());
		}
	}
	
}
