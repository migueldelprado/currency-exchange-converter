package com.currency.converter.service;

import java.util.List;

import com.currency.converter.model.CurrencyExchangeQuery;

/**
 * @author Miguel del Prado Aranda
 * @email m.delpradoaranda@gmail.com
 */

public interface CurrencyExchangeQueryService {

	List< CurrencyExchangeQuery > findAll( Integer userId );

	void save( CurrencyExchangeQuery currencyExchangeQuery );

	void delete( Integer id );

}