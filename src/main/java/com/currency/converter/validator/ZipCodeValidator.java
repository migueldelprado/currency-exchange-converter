package com.currency.converter.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

/**
 * @author Miguel del Prado Aranda
 * @email m.delpradoaranda@gmail.com
 */

@Component( "zipCodeValidator" )
public class ZipCodeValidator {

	private Pattern pattern;
	private Matcher matcher;

	private static final String ZIPCODE_PATTERN = "^[0-9]{5}";

	public ZipCodeValidator() {
		pattern = Pattern.compile( ZIPCODE_PATTERN );
	}

	public boolean valid( final String zipCode ) {

		matcher = pattern.matcher( zipCode );
		return matcher.matches();

	}
}