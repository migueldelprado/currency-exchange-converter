package com.currency.converter.dao;

import com.currency.converter.model.User;

/**
 * @author Miguel del Prado Aranda
 * @email m.delpradoaranda@gmail.com
 */

public interface UserDao {

	User findById( Integer id );

	void save( User user );

	void update( User user );

	void delete( Integer id );

	User findByEmail( String email );
}