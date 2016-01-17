package com.currency.converter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.currency.converter.dao.UserDao;
import com.currency.converter.model.User;

/**
 * @author Miguel del Prado Aranda
 * @email m.delpradoaranda@gmail.com
 */

@Service( "userService" )
public class UserServiceImpl implements UserService {

	UserDao userDao;
	PasswordEncoder passwordEncoder;

	@Autowired
	public void setUserDao( UserDao userDao ) {
		this.userDao = userDao;
	}

	@Autowired
	public void setPasswordEncoder( PasswordEncoder passwordEncoder ) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User findById( Integer id ) {
		return userDao.findById( id );
	}

	@Override
	public void saveOrUpdate( User user ) {

		if ( findById( user.getUserID() ) == null ) {
			user.setPassword( passwordEncoder.encode( user.getPassword() ) );
			userDao.save( user );
		} else {
			if ( !userDao.findById( user.getUserID() ).getPassword().equals( user.getPassword() ) ) {
				user.setPassword( passwordEncoder.encode( user.getPassword() ) );
			}
			userDao.update( user );
		}
	}

	@Override
	public void delete( Integer id ) {
		userDao.delete( id );
	}

	@Override
	public User findByEmail( String email ) {
		return userDao.findByEmail( email );
	}

}