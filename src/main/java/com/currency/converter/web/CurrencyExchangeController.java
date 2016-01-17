package com.currency.converter.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.currency.converter.api.CurrencyExchangeRateAPI;
import com.currency.converter.api.CurrencyRatesAPI;
import com.currency.converter.memorycache.Cache;
import com.currency.converter.model.CurrencyExchangeQuery;
import com.currency.converter.model.CurrencyExchangeQueryForm;
import com.currency.converter.model.CurrencyExchangeRate;
import com.currency.converter.security.CustomUserDetails;
import com.currency.converter.service.CurrencyExchangeQueryService;
import com.currency.converter.service.UserService;

/**
 * @author Miguel del Prado Aranda
 * @email m.delpradoaranda@gmail.com
 */

@Controller
public class CurrencyExchangeController {

	private final Logger logger = LoggerFactory.getLogger( CurrencyExchangeController.class );

	private CurrencyExchangeQueryService exchangeQueryService;
	private CurrencyExchangeQueryForm exchangeQueryForm;
	private UserService userService;

	private final static long cacheTimeToLive = 200;
	private final static long cacheTimerInterval = 500;
	private final static Cache< String, HashMap< String, Double > > cache = new Cache< String, HashMap< String, Double > >( cacheTimeToLive, cacheTimerInterval );

	@Autowired
	public void setExchangeQueryService( CurrencyExchangeQueryService exchangeQueryService ) {
		this.exchangeQueryService = exchangeQueryService;
	}

	@Autowired
	public void setExchangeQueryForm( CurrencyExchangeQueryForm exchangeQueryForm ) {
		this.exchangeQueryForm = exchangeQueryForm;
	}

	@Autowired
	public void setUserService( UserService userService ) {
		this.userService = userService;
	}

	@RequestMapping( value = "/", method = RequestMethod.GET )
	public String index( Model model ) {
		logger.debug( "index()" );
		return "redirect:/currency_exchange/list";
	}

	// list page queries
	@RequestMapping( value = "/currency_exchange/list", method = RequestMethod.GET )
	public String showList( Model model ) {
		logger.debug( "showList()" );
		CustomUserDetails customUserDetails = ( CustomUserDetails ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		model.addAttribute( "list", exchangeQueryService.findAll( customUserDetails.getId() ) );
		return "currency_exchange/list";

	}

	// delete query
	@RequestMapping( value = "/currency_exchange/{id}/delete", method = RequestMethod.POST )
	public String deleteQuery( @PathVariable( "id" ) int id, final RedirectAttributes redirectAttributes ) {
		logger.debug( "deleteQuery() : {}", id );

		exchangeQueryService.delete( id );

		redirectAttributes.addFlashAttribute( "css", "success" );
		redirectAttributes.addFlashAttribute( "msg", "User is deleted!" );

		return "redirect:/currency_exchange/list";
	}

	// add query
	@RequestMapping( value = "/currency_exchange/new", method = RequestMethod.GET )
	public String showNew( Model model ) {
		logger.debug( "showNew()" );

		Double rate = new Double( 1 );

		HashMap< String, Double > cacheContentHashMap = cache.get( this.exchangeQueryForm.getOriginCurrency() );

		if ( cacheContentHashMap == null ) {
			CurrencyExchangeRateAPI currencyExchangeRateAPI = CurrencyRatesAPI.sendRequest( this.exchangeQueryForm.getOriginCurrency() );
			cacheContentHashMap = currencyExchangeRateAPI.getQuotes();
			cache.put( this.exchangeQueryForm.getOriginCurrency(), currencyExchangeRateAPI.getQuotes() );
		}

		rate = cacheContentHashMap.get( this.exchangeQueryForm.getOriginCurrency().concat( this.exchangeQueryForm.getDestinationCurrency() ) );

		CurrencyExchangeQuery currencyExchangeQuery = new CurrencyExchangeQuery();

		currencyExchangeQuery.setOriginCurrency( this.exchangeQueryForm.getOriginCurrency() );
		currencyExchangeQuery.setDestinationCurrency( this.exchangeQueryForm.getDestinationCurrency() );
		currencyExchangeQuery.setQuantityOrigin( this.exchangeQueryForm.getQuantityOrigin() );
		currencyExchangeQuery.setExchangeRate( this.exchangeQueryForm.getQuantityOrigin() * rate );

		model.addAttribute( "currencyExchangeQueryForm", currencyExchangeQuery );
		model.addAttribute( "rate", rate );

		populateDefaultModel( model );

		return "currency_exchange/new";
	}

	// add query
	@RequestMapping( value = "/currency_exchange/new", method = RequestMethod.POST )
	public String showNew( @ModelAttribute( "currencyExchangeQueryForm" ) CurrencyExchangeQuery currencyExchangeQuery,
			@RequestParam( required = false, value = "add" ) String add, Model model, final RedirectAttributes redirectAttributes ) {
		logger.debug( "showNew()" );

		this.exchangeQueryForm.setOriginCurrency( currencyExchangeQuery.getOriginCurrency() );
		this.exchangeQueryForm.setDestinationCurrency( currencyExchangeQuery.getDestinationCurrency() );
		this.exchangeQueryForm.setQuantityOrigin( currencyExchangeQuery.getQuantityOrigin() );
		this.exchangeQueryForm.setExchangeRate( currencyExchangeQuery.getExchangeRate() );
		currencyExchangeQuery.setUser( userService.findById( ( ( CustomUserDetails ) SecurityContextHolder.getContext().getAuthentication().getPrincipal() )
				.getId() ) );

		if ( add != null ) {
			redirectAttributes.addFlashAttribute( "css", "success" );
			redirectAttributes.addFlashAttribute( "msg", "Added!!" );

			exchangeQueryService.save( currencyExchangeQuery );
		}

		return "redirect:/currency_exchange/new";
	}

	// current currency exchange
	@RequestMapping( value = "/currency_exchange/current", method = RequestMethod.GET )
	public String currentExchangeRates( Model model ) {
		logger.debug( "currentExchangeRates()" );

		List< CurrencyExchangeRate > exchangeRateList = new ArrayList< CurrencyExchangeRate >();

		// at the moment origin can be just USD because of free version of the
		// API
		String originCurrency = "USD";

		HashMap< String, Double > cacheContentHashMap = cache.get( originCurrency );

		if ( cacheContentHashMap == null ) {
			CurrencyExchangeRateAPI currencyExchangeRateAPI = CurrencyRatesAPI.sendRequest( originCurrency );
			cacheContentHashMap = currencyExchangeRateAPI.getQuotes();
			cache.put( originCurrency, currencyExchangeRateAPI.getQuotes() );
		}

		for ( Map.Entry< String, Double > entry : cacheContentHashMap.entrySet() ) {
			if ( !entry.getKey().substring( 3 ).equals( originCurrency ) ) {
				CurrencyExchangeRate exchangeRate = new CurrencyExchangeRate( originCurrency, entry.getKey().substring( 3 ), entry.getValue() );
				exchangeRateList.add( exchangeRate );
			}
		}

		model.addAttribute( "current", exchangeRateList );

		populateDefaultModel( model );

		return "currency_exchange/current";
	}

	private void populateDefaultModel( Model model ) {
		// at the moment origin can be just USD because of free version of the
		// API
		Map< String, String > originCurrencyList = new LinkedHashMap< String, String >();
		originCurrencyList.put( "USD", "USD" );
		// originCurrencyList.put( "EUR", "EUR" );
		// originCurrencyList.put( "GBP", "GBP" );
		// originCurrencyList.put( "NZD", "NZD" );
		// originCurrencyList.put( "AUD", "AUD" );
		// originCurrencyList.put( "HUF", "HUF" );
		model.addAttribute( "originCurrencyList", originCurrencyList );

		Map< String, String > destinationCurrenyList = new LinkedHashMap< String, String >();
		destinationCurrenyList.put( "EUR", "EUR" );
		destinationCurrenyList.put( "GBP", "GBP" );
		destinationCurrenyList.put( "NZD", "NZD" );
		destinationCurrenyList.put( "AUD", "AUD" );
		destinationCurrenyList.put( "HUF", "HUF" );
		model.addAttribute( "destinationCurrenyList", destinationCurrenyList );
	}

}