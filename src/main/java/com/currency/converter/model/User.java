package com.currency.converter.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Miguel del Prado Aranda
 * @email m.delpradoaranda@gmail.com
 */

@Entity
@Table( name = "User", uniqueConstraints = @UniqueConstraint( columnNames = "EMail" ) )
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	public Integer userID;
	public String name;
	public String email;
	public Date dateOfBirth;
	public String password;
	@JsonIgnore
	public String confirmPassword;

	public String street;
	public Integer zipCode;
	public String city;
	public String country;

	@JsonIgnore
	private Set< CurrencyExchangeQuery > currencyExchangeQuery = new HashSet< CurrencyExchangeQuery >( 0 );

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "UserID", unique = true, nullable = false )
	public Integer getUserID() {
		return userID;
	}

	public void setUserID( Integer userID ) {
		this.userID = userID;
	}

	@Column( name = "Name", nullable = false )
	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	@Column( name = "EMail", unique = true, nullable = false )
	public String getEmail() {
		return email;
	}

	public void setEmail( String email ) {
		this.email = email;
	}

	@Column( name = "Password", nullable = false )
	public String getPassword() {
		return password;
	}

	public void setPassword( String password ) {
		this.password = password;
	}

	@Transient
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword( String confirmPassword ) {
		this.confirmPassword = confirmPassword;
	}

	@DateTimeFormat( pattern = "dd/MM/yyyy" )
	@Column( name = "DtDateOfBirth", nullable = false )
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth( Date dateOfBirth ) {
		this.dateOfBirth = dateOfBirth;
	}

	@Column( name = "Street", nullable = false )
	public String getStreet() {
		return street;
	}

	public void setStreet( String street ) {
		this.street = street;
	}

	@Column( name = "ZipCode", nullable = false )
	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode( Integer zipCode ) {
		this.zipCode = zipCode;
	}

	@Column( name = "City", nullable = false )
	public String getCity() {
		return city;
	}

	public void setCity( String city ) {
		this.city = city;
	}

	@Column( name = "Country", nullable = false )
	public String getCountry() {
		return country;
	}

	public void setCountry( String country ) {
		this.country = country;
	}

	@OneToMany( fetch = FetchType.LAZY, mappedBy = "user" )
	public Set< CurrencyExchangeQuery > getCurrencyExchangeQuery() {
		return this.currencyExchangeQuery;
	}

	public void setCurrencyExchangeQuery( Set< CurrencyExchangeQuery > currencyExchangeQuery ) {
		this.currencyExchangeQuery = currencyExchangeQuery;
	}
}
