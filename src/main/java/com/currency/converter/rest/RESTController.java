package com.currency.converter.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.currency.converter.model.CurrencyExchangeQuery;
import com.currency.converter.model.User;
import com.currency.converter.service.CurrencyExchangeQueryService;
import com.currency.converter.service.UserService;

/**
 * @author Miguel del Prado Aranda
 * @email m.delpradoaranda@gmail.com
 */

@RestController
public class RESTController {
	private final Logger logger = LoggerFactory.getLogger( RESTController.class );

	@Autowired
	UserService userService;

	@Autowired
	CurrencyExchangeQueryService currencyExchangeQueryService;

	// Retrieve User User
	@RequestMapping( value = "/restful/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity< User > getUser( @PathVariable( "id" ) Integer id ) {
		logger.debug( "Fetching User with id " + id );
		User user = userService.findById( id );
		if ( user == null ) {
			logger.debug( "User with id " + id + " not found" );
			return new ResponseEntity< User >( HttpStatus.NOT_FOUND );
		}
		return new ResponseEntity< User >( user, HttpStatus.OK );
	}

	// Create a User
	@RequestMapping( value = "/restful/users/add", method = RequestMethod.POST )
	public ResponseEntity< Void > createUser( @RequestBody User user, UriComponentsBuilder ucBuilder ) {
		logger.debug( "Creating User " + user.getName() );

		if ( userService.findByEmail( user.getEmail() ) != null ) {
			logger.debug( "A User with name " + user.getName() + " already exist" );
			return new ResponseEntity< Void >( HttpStatus.CONFLICT );
		}

		userService.saveOrUpdate( user );

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation( ucBuilder.path( "/restful/users/{id}" ).buildAndExpand( user.getUserID() ).toUri() );
		return new ResponseEntity< Void >( headers, HttpStatus.CREATED );
	}

	// Update a User
	@RequestMapping( value = "/restful/users/update/{id}", method = RequestMethod.PUT )
	public ResponseEntity< User > updateUser( @PathVariable( "id" ) Integer id, @RequestBody User user ) {
		logger.debug( "Updating User " + id );

		User currentUser = userService.findById( id );

		if ( currentUser == null ) {
			logger.debug( "User with id " + id + " not found" );
			return new ResponseEntity< User >( HttpStatus.NOT_FOUND );
		}

		if( user.getName() != null ) {
			currentUser.setName( user.getName() );
		}
		
		if( user.getEmail() != null ) {
			currentUser.setEmail( user.getEmail() );
		}
		
		if( user.getDateOfBirth() != null ) {
			currentUser.setDateOfBirth( user.getDateOfBirth() );
		}
		
		if( user.getStreet() != null ) {
			currentUser.setStreet( user.getStreet() );
		}
		
		if( user.getZipCode() != null ) {
			currentUser.setZipCode( user.getZipCode() );
		}
		
		if( user.getCity() != null ) {
			currentUser.setCity( user.getCity() );
		}
		
		if( user.getCountry() != null ) {
			currentUser.setCountry( user.getCountry() );
		}

		if ( userService.findByEmail( user.getEmail() ) != null ) {
			return new ResponseEntity< User >( HttpStatus.CONFLICT );
		} else {
			userService.saveOrUpdate( currentUser );
			return new ResponseEntity< User >( currentUser, HttpStatus.OK );
		}

	}

	@RequestMapping( value = "/restful/currency_exchange/{id}", method = RequestMethod.GET )
	public ResponseEntity< List< CurrencyExchangeQuery >> QueriesForUser( @PathVariable( "id" ) Integer id ) {
		List< CurrencyExchangeQuery > currencyExchangeQueries = currencyExchangeQueryService.findAll( id );
		if ( currencyExchangeQueries.isEmpty() ) {
			return new ResponseEntity< List< CurrencyExchangeQuery >>( HttpStatus.NO_CONTENT );
		}
		return new ResponseEntity< List< CurrencyExchangeQuery >>( currencyExchangeQueries, HttpStatus.OK );
	}
}
