package com.anz.batch;

import org.springframework.batch.item.ItemProcessor;

import com.anz.model.CurrencyMapping;

public class DBLogProcessor implements ItemProcessor<CurrencyMapping, CurrencyMapping> {

	@Override
	public CurrencyMapping process(CurrencyMapping item) throws Exception {
		System.out.println("Inserting Currency Mapping : " + item);
        return item;
	}

}
