package com.berrakanil.travelmoney;

public class RateResponse {

	private String m_currencyPair;
	private float m_exchangeRate;

	public RateResponse (float exchangeRate, String currencyPair) {
		this.m_exchangeRate = exchangeRate;
		this.m_currencyPair = currencyPair;	
	}
	
	public String getCurrencyPair() {
		return m_currencyPair;
	}
	
	public float getExchangeRate() {
		return m_exchangeRate;
	}
}
