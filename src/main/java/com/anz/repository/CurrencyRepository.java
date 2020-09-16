package com.anz.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.anz.model.CurrencyMapping;

/**
 * CRUD activities on CURRENCY_MAPPING table
 * @author sachin
 *
 */
@Repository
public interface CurrencyRepository extends CrudRepository<CurrencyMapping, Long>{
	
	@Query("SELECT u.process FROM CurrencyMapping u WHERE u.fromCurrency = :fromCurrency and u.toCurrency = :toCurrency")
	public String findConversionProcess(
	  @Param("fromCurrency") String fromCurrency, 
	  @Param("toCurrency") String toCurrency);

}
