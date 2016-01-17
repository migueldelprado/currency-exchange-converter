/**
 * 
 */
package com.currency.converter.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Miguel del Prado Aranda
 * @email m.delpradoaranda@gmail.com
 */

@Controller
public class LoginController {

	private final Logger logger = LoggerFactory.getLogger( LoginController.class );

	@RequestMapping( value = "/login", method = RequestMethod.GET )
	public String doLoginLogout( @RequestParam( value = "error", required = false ) boolean error,
			@RequestParam( value = "logout", required = false ) boolean logout, ModelMap model ) {
		logger.debug( "doLoginLogout()" );

		if ( error == true ) {
			logger.debug( "doLoginLogout() error in authentication" );
			model.put( "error", true );
		} else {
			model.put( "error", false );
		}
		if ( logout == true ) {
			logger.debug( "doLoginLogout() logout" );
			model.put( "logout", true );
		} else {
			model.put( "logout", false );
		}

		return "login/login";
	}
}
