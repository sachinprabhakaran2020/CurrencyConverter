package com.anz.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.anz.model.ConversionRate;

@Repository
public interface ConversionRateRepository extends CrudRepository<ConversionRate, Long>{
	
	@Query("SELECT u.conversionRate FROM ConversionRate u WHERE u.fromCurrency = :fromCurrency and u.toCurrency = :toCurrency")
	public Double findConversionRate(@Param("fromCurrency") String fromCurrency, @Param("toCurrency") String toCurrency);

}
