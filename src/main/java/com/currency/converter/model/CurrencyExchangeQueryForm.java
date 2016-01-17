package com.currency.converter.model;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * @author Miguel del Prado Aranda
 * @email m.delpradoaranda@gmail.com
 */

@Component( "exchangeQueryForm" )
@Scope( value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS )
public class CurrencyExchangeQueryForm {

	String originCurrency;
	String destinationCurrency;
	Double exchangeRate;
	Double quantityOrigin;

	public CurrencyExchangeQueryForm() {
		this.originCurrency = "USD";
		this.destinationCurrency = "EUR";
		this.quantityOrigin = new Double( 1 );
	}

	public String getOriginCurrency() {
		return originCurrency;
	}

	public void setOriginCurrency( String originCurrency ) {
		this.originCurrency = originCurrency;
	}

	public String getDestinationCurrency() {
		return destinationCurrency;
	}

	public void setDestinationCurrency( String destinationCurrency ) {
		this.destinationCurrency = destinationCurrency;
	}

	public Double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate( Double exchangeRate ) {
		this.exchangeRate = exchangeRate;
	}

	public Double getQuantityOrigin() {
		return quantityOrigin;
	}

	public void setQuantityOrigin( Double quantityOrigin ) {
		this.quantityOrigin = quantityOrigin;
	}

}
