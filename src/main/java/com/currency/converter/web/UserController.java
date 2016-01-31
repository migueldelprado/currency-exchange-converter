package com.currency.converter.web;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.currency.converter.model.User;
import com.currency.converter.security.CustomUserDetails;
import com.currency.converter.service.UserService;
import com.currency.converter.validator.UserFormValidator;

/**
 * @author Miguel del Prado Aranda
 * @email m.delpradoaranda@gmail.com
 */

@Controller
public class UserController {

	private final Logger logger = LoggerFactory.getLogger( UserController.class );

	@Autowired
	UserFormValidator userFormValidator;

	@InitBinder
	protected void initBinder( WebDataBinder binder ) {
		binder.setValidator( userFormValidator );
	}

	private UserService userService;

	@Autowired
	public void setUserService( UserService userService ) {
		this.userService = userService;
	}

	@Autowired
	private MessageSource messageSource;

	// save or update user
	@RequestMapping( value = "/users", method = RequestMethod.POST )
	public String saveOrUpdateUser( @ModelAttribute( "userForm" ) @Validated User user, BindingResult result, Model model,
			final RedirectAttributes redirectAttributes ) {
		logger.debug( "saveOrUpdateUser() : {}", user );

		if ( result.hasErrors() ) {
			return "users/userform";
		} else {

			redirectAttributes.addFlashAttribute( "css", "success" );
			if ( userService.findById( user.getUserID() ) == null ) {
				redirectAttributes.addFlashAttribute( "msg", messageSource.getMessage( "User.Added", null, LocaleContextHolder.getLocale() ) );
			} else {
				redirectAttributes.addFlashAttribute( "msg", messageSource.getMessage( "User.Updated", null, LocaleContextHolder.getLocale() ) );
			}

			userService.saveOrUpdate( user );

			return "redirect:/users/" + user.getUserID();
		}
	}

	// show add user form
	@RequestMapping( value = "/users/add", method = RequestMethod.GET )
	public String showAddUserForm( Model model ) {
		logger.debug( "showAddUserForm()" );

		User user = new User();

		model.addAttribute( "userForm", user );
		return "users/userform";

	}

	// show update form
	@RequestMapping( value = "/users/{id}/update", method = RequestMethod.GET )
	public String showUpdateUserForm( @PathVariable( "id" ) int id, Model model ) {
		logger.debug( "showUpdateUserForm() : {}", id );

		if ( !getIsUserLogged() ) {
			return "security_error";
		} else {
			CustomUserDetails customUserDetails = ( CustomUserDetails ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if ( !customUserDetails.getId().equals( id ) ) {
				return "security_error";
			} else {
				User user = userService.findById( id );
				user.setConfirmPassword( user.getPassword() );
				model.addAttribute( "userForm", user );
				return "users/userform";
			}
		}
	}

	// show user
	@RequestMapping( value = "/users/{id}", method = RequestMethod.GET )
	public String showUser( @PathVariable( "id" ) int id, Model model ) {
		logger.debug( "showUser() id: {}", id );

		User user = userService.findById( id );
		if ( user == null ) {
			model.addAttribute( "css", "error" );
			model.addAttribute( "msg", messageSource.getMessage( "User.NotFound", null, LocaleContextHolder.getLocale() ) );
		}
		model.addAttribute( "user", user );

		return "users/show";
	}

	@ExceptionHandler( EmptyResultDataAccessException.class )
	public ModelAndView handleEmptyData( HttpServletRequest req, Exception ex ) {
		logger.debug( "handleEmptyData()" );
		logger.error( "Request: {}, error ", req.getRequestURL(), ex );

		ModelAndView model = new ModelAndView();
		model.setViewName( "/users/{id}" );
		model.addObject( "msg", messageSource.getMessage( "User.NotFound", null, LocaleContextHolder.getLocale() ) );

		return model;
	}

	public boolean getIsUserLogged() {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		return ( !name.equals( "anonymousUser" ) ) ? true : false;
	}
}