package com.currency.converter.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.sf.ehcache.CacheManager;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import com.currency.converter.model.CurrencyExchangeQuery;

/**
 * @author Miguel del Prado Aranda
 * @email m.delpradoaranda@gmail.com
 */

public class CurrencyExchangeQueryDaoImpl implements CurrencyExchangeQueryDao {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings( "unchecked" )
	@Override
	@Cacheable( "CurrencyExchangeQueryFindAll" )
	public List< CurrencyExchangeQuery > findAll( Integer userId ) {
		Query query = entityManager.createQuery( "FROM CurrencyExchangeQuery WHERE UserId = :userId ORDER BY CreatedDate DESC" )
				.setParameter( "userId", userId ).setMaxResults( 10 );
		return query.getResultList();
	}

	@Override
	@Transactional
	public void save( CurrencyExchangeQuery currencyExchangeQuery ) {
		currencyExchangeQuery.setCreatedDate( new Date() );
		entityManager.persist( currencyExchangeQuery );
		CacheManager.getInstance().getEhcache( "CurrencyExchangeQueryFindAll" ).removeAll();
		CacheManager.getInstance().getEhcache( "CurrencyExchangeQueryFindById" ).removeAll();
	}

	@Override
	@Transactional
	public void delete( Integer id ) {
		entityManager.remove( findById( id ) );
		CacheManager.getInstance().getEhcache( "CurrencyExchangeQueryFindAll" ).removeAll();
		CacheManager.getInstance().getEhcache( "CurrencyExchangeQueryFindById" ).removeAll();

	}

	@SuppressWarnings( "unchecked" )
	@Override
	@Cacheable( "CurrencyExchangeQueryFindById" )
	public CurrencyExchangeQuery findById( Integer id ) {
		Query query = entityManager.createQuery( "FROM CurrencyExchangeQuery WHERE ID = :id" ).setParameter( "id", id );
		List< CurrencyExchangeQuery > list = query.getResultList();

		if ( !list.isEmpty() ) {
			return ( CurrencyExchangeQuery ) list.get( 0 );
		} else {
			return null;
		}
	}
}