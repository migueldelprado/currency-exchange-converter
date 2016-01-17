/**
 * 
 */
package com.currency.converter.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Miguel del Prado Aranda
 * @email m.delpradoaranda@gmail.com
 */
public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;

	public Integer id;

	public CustomUserDetails( String username, String password, Integer id, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked, Collection< ? extends GrantedAuthority > authorities ) {
		super( username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities );

		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId( Integer id ) {
		this.id = id;
	}

}
