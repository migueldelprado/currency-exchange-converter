package com.currency.converter.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.currency.converter.model.User;
import com.currency.converter.service.UserService;

/**
 * @author Miguel del Prado Aranda
 * @email m.delpradoaranda@gmail.com
 */

@Component
public class UserFormValidator implements Validator {

	@Autowired
	@Qualifier( "emailValidator" )
	EmailValidator emailValidator;

	@Autowired
	@Qualifier( "zipCodeValidator" )
	ZipCodeValidator zipCodeValidator;

	@Autowired
	UserService userService;

	@Override
	public boolean supports( Class< ? > clazz ) {
		return User.class.equals( clazz );
	}

	@Override
	public void validate( Object target, Errors errors ) {

		User user = ( User ) target;

		ValidationUtils.rejectIfEmptyOrWhitespace( errors, "name", "NotEmpty.userForm.name" );
		ValidationUtils.rejectIfEmptyOrWhitespace( errors, "email", "NotEmpty.userForm.email" );
		if ( !user.getEmail().isEmpty() && !emailValidator.valid( user.getEmail() ) ) {
			errors.rejectValue( "email", "Pattern.userForm.email" );
		}

		if ( user.getUserID() == null && userService.findByEmail( user.getEmail() ) != null ) {
			errors.rejectValue( "email", "User.AlreadyExist" );
		}

		ValidationUtils.rejectIfEmptyOrWhitespace( errors, "dateOfBirth", "NotEmpty.userForm.dateOfBirth" );

		ValidationUtils.rejectIfEmptyOrWhitespace( errors, "password", "NotEmpty.userForm.password" );
		ValidationUtils.rejectIfEmptyOrWhitespace( errors, "confirmPassword", "NotEmpty.userForm.confirmPassword" );
		if ( !user.getPassword().equals( user.getConfirmPassword() ) ) {
			errors.rejectValue( "confirmPassword", "Diff.userform.confirmPassword" );
		}

		ValidationUtils.rejectIfEmptyOrWhitespace( errors, "street", "NotEmpty.userForm.street" );
		ValidationUtils.rejectIfEmptyOrWhitespace( errors, "zipCode", "NotEmpty.userForm.zipCode" );
		if ( user.getZipCode() != null && !zipCodeValidator.valid( user.getZipCode().toString() ) ) {
			errors.rejectValue( "zipCode", "NotValid.zipCode" );
		}

		ValidationUtils.rejectIfEmptyOrWhitespace( errors, "city", "NotEmpty.userForm.city" );

		if ( user.getCountry().equals( "NONE" ) ) {
			errors.rejectValue( "country", "NotEmpty.userForm.country" );
		}

	}
}