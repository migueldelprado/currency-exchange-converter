/**
 * 
 */
package com.currency.converter.model;

/**
 * @author Miguel del Prado Aranda
 * @email m.delpradoaranda@gmail.com
 */
public class CurrencyExchangeRate {

	private String origin;
	private String target;
	private Double rate;

	public CurrencyExchangeRate( String origin, String target, Double rate ) {
		this.origin = origin;
		this.target = target;
		this.rate = rate;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin( String origin ) {
		this.origin = origin;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget( String target ) {
		this.target = target;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate( Double rate ) {
		this.rate = rate;
	}

}
