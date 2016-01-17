package com.currency.converter.service;

import com.currency.converter.model.User;

/**
 * @author Miguel del Prado Aranda
 * @email m.delpradoaranda@gmail.com
 */

public interface UserService {

	User findById( Integer id );

	void saveOrUpdate( User user );

	void delete( Integer id );

	User findByEmail( String email );
}