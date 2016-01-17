package com.currency.converter.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Miguel del Prado Aranda
 * @email m.delpradoaranda@gmail.com
 */

@Entity
@Table( name = "CurrencyExchangeQuery" )
public class CurrencyExchangeQuery implements Serializable {

	private static final long serialVersionUID = 1L;

	Integer id;
	String originCurrency;
	String destinationCurrency;
	Double exchangeRate;
	Date createdDate;
	Double quantityOrigin;
	@JsonIgnore
	User user;

	@Id
	@GeneratedValue( strategy = IDENTITY )
	@Column( name = "ID", unique = true, nullable = false )
	public Integer getId() {
		return id;
	}

	public void setId( Integer id ) {
		this.id = id;
	}

	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "UserID", nullable = false )
	public User getUser() {
		return user;
	}

	public void setUser( User user ) {
		this.user = user;
	}

	@Column( name = "OriginCurrency", nullable = false )
	public String getOriginCurrency() {
		return originCurrency;
	}

	public void setOriginCurrency( String originCurrency ) {
		this.originCurrency = originCurrency;
	}

	@Column( name = "DestinationCurrency", nullable = false )
	public String getDestinationCurrency() {
		return destinationCurrency;
	}

	public void setDestinationCurrency( String destinationCurrency ) {
		this.destinationCurrency = destinationCurrency;
	}

	@Column( name = "ExchangeRate", nullable = false )
	public Double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate( Double exchangeRate ) {
		this.exchangeRate = exchangeRate;
	}

	@Column( name = "CreatedDate", nullable = false )
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate( Date createdDate ) {
		this.createdDate = createdDate;
	}

	@Column( name = "QuantityOrigin", nullable = false )
	public Double getQuantityOrigin() {
		return quantityOrigin;
	}

	public void setQuantityOrigin( Double quantityOrigin ) {
		this.quantityOrigin = quantityOrigin;
	}

}
