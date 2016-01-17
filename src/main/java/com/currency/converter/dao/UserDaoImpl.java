package com.currency.converter.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.sf.ehcache.CacheManager;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import com.currency.converter.model.User;

/**
 * @author Miguel del Prado Aranda
 * @email m.delpradoaranda@gmail.com
 */

public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings( "unchecked" )
	@Override
	@Cacheable( "UserFindById" )
	public User findById( Integer id ) {
		Query query = entityManager.createQuery( "FROM User WHERE UserID = :id" ).setParameter( "id", id );
		List< User > list = query.getResultList();

		if ( !list.isEmpty() ) {
			return ( User ) list.get( 0 );
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public void save( User user ) {
		entityManager.persist( user );
		CacheManager.getInstance().getEhcache( "UserFindById" ).removeAll();
		CacheManager.getInstance().getEhcache( "UserFindByEmail" ).removeAll();

	}

	@Override
	@Transactional
	public void update( User user ) {
		entityManager.merge( user );
		CacheManager.getInstance().getEhcache( "UserFindById" ).removeAll();
		CacheManager.getInstance().getEhcache( "UserFindByEmail" ).removeAll();
	}

	@Override
	@Transactional
	public void delete( Integer id ) {
		entityManager.remove( findById( id ) );
		CacheManager.getInstance().getEhcache( "UserFindById" ).removeAll();
		CacheManager.getInstance().getEhcache( "UserFindByEmail" ).removeAll();
	}

	@SuppressWarnings( "unchecked" )
	@Override
	@Cacheable( "UserFindByEmail" )
	public User findByEmail( String email ) {
		Query query = entityManager.createQuery( "FROM User WHERE EMail = :email" ).setParameter( "email", email );
		List< User > list = query.getResultList();

		if ( !list.isEmpty() ) {
			return ( User ) list.get( 0 );
		} else {
			return null;
		}
	}

}