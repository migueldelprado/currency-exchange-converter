package com.currency.converter.api;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Miguel del Prado Aranda
 * @email m.delpradoaranda@gmail.com
 */

public class CurrencyRatesAPI {
	public static final String ACCESS_KEY = "2a25839a25762cfbd2dc559c50f4fa16";
	public static final String BASE_URL = "http://apilayer.net/api/";
	public static final String ENDPOINT = "live";
	public static final String ORIGIN_AVAILABLE_CURRENCIES = "USD";
	public static final String TARGET_AVAILABLE_CURRENCIES = "USD,EUR,GBP,NZD,AUD,HUF";

	static CloseableHttpClient httpClient = HttpClients.createDefault();

	public static CurrencyExchangeRateAPI sendRequest( String currencyOrigin ) {

		CurrencyExchangeRateAPI currencyExchangeRate = new CurrencyExchangeRateAPI();

		HttpGet get = new HttpGet( BASE_URL + ENDPOINT + "?access_key=" + ACCESS_KEY + "&source=" + currencyOrigin + "&currencies="
				+ TARGET_AVAILABLE_CURRENCIES );

		try {
			CloseableHttpResponse response = httpClient.execute( get );
			HttpEntity entity = response.getEntity();

			JSONObject exchangeRates = new JSONObject( EntityUtils.toString( entity ) );

			ObjectMapper mapper = new ObjectMapper();
			mapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );

			currencyExchangeRate = mapper.readValue( exchangeRates.toString(), CurrencyExchangeRateAPI.class );

			response.close();

		} catch ( ClientProtocolException e ) {
			e.printStackTrace();
		} catch ( IOException e ) {
			e.printStackTrace();
		} catch ( ParseException e ) {
			e.printStackTrace();
		} catch ( JSONException e ) {
			e.printStackTrace();
		}

		return currencyExchangeRate;
	}
}
