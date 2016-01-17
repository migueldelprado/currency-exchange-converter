/**
 * 
 */
package com.currency.converter.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.currency.converter.dao.UserDao;

/**
 * @author Miguel del Prado Aranda
 * @email m.delpradoaranda@gmail.com
 */

@Service
@Transactional( readOnly = true )
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao( UserDao userDao ) {
		this.userDao = userDao;
	}

	@Transactional( readOnly = true )
	@Override
	public UserDetails loadUserByUsername( final String username ) throws UsernameNotFoundException {

		com.currency.converter.model.User user = userDao.findByEmail( username );

		CustomUserDetails userDetails = buildUserForAuthentication( user, buildUserAuthority() );

		Authentication authentication = new UsernamePasswordAuthenticationToken( userDetails, userDetails.getPassword(), userDetails.getAuthorities() );

		SecurityContextHolder.getContext().setAuthentication( authentication );

		return buildUserForAuthentication( user, buildUserAuthority() );
	}

	private CustomUserDetails buildUserForAuthentication( com.currency.converter.model.User user, List< GrantedAuthority > authorities ) {
		return new CustomUserDetails( user.getName(), user.getPassword(), user.getUserID(), true, true, true, true, authorities );
	}

	private List< GrantedAuthority > buildUserAuthority() {

		Set< GrantedAuthority > setAuths = new HashSet< GrantedAuthority >();
		List< GrantedAuthority > Result = new ArrayList< GrantedAuthority >( setAuths );

		return Result;
	}

}
