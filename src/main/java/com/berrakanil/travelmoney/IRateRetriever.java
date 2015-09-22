package com.berrakanil.travelmoney;

public interface IRateRetriever {
	public float getRate(String pair) throws Exception;
}
