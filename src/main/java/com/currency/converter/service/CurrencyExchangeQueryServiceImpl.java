package com.currency.converter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.currency.converter.dao.CurrencyExchangeQueryDao;
import com.currency.converter.model.CurrencyExchangeQuery;

/**
 * @author Miguel del Prado Aranda
 * @email m.delpradoaranda@gmail.com
 */

@Service( "exchangeQueryService" )
public class CurrencyExchangeQueryServiceImpl implements CurrencyExchangeQueryService {

	CurrencyExchangeQueryDao exchangeQueryDao;

	@Autowired
	public void setExchangeQueryDao( CurrencyExchangeQueryDao exchangeQueryDao ) {
		this.exchangeQueryDao = exchangeQueryDao;
	}

	@Override
	public List< CurrencyExchangeQuery > findAll( Integer userId ) {
		return exchangeQueryDao.findAll( userId );
	}

	@Override
	public void save( CurrencyExchangeQuery currencyExchangeQuery ) {
		exchangeQueryDao.save( currencyExchangeQuery );
	}

	@Override
	public void delete( Integer id ) {
		exchangeQueryDao.delete( id );

	}

}