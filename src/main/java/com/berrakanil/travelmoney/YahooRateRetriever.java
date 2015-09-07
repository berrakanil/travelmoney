package com.berrakanil.travelmoney;

import java.io.*;
import java.net.*;
import org.json.*;

public class YahooRateRetriever implements IRateRetriever {
	
	private final String CHARSET = java.nio.charset.StandardCharsets.UTF_8.name();
	private final String URL = "https://query.yahooapis.com/v1/public/yql";
	private final String DATATABLES = "store://datatables.org/alltableswithkeys";
	private final String FORMAT = "json";
	private final String YQL = "select * from yahoo.finance.xchange where pair in ('%s')";
	private final float  DELTA = 0.0001f;

	protected String createUrlString(String pair) throws UnsupportedEncodingException {
		String currencyQuery = String.format(YQL, pair);
		String query = String.format(URL + "?q=%s&format=%s&env=%s", URLEncoder.encode(currencyQuery, CHARSET),
				URLEncoder.encode(FORMAT, CHARSET), URLEncoder.encode(DATATABLES, CHARSET));
		return query;
	}

	protected float parseResponse(String line) {
		JSONObject json = new JSONObject(line);
		JSONObject main = json.getJSONObject("query").getJSONObject("results").getJSONObject("rate");
		String rate = main.getString("Rate");

		return Float.parseFloat(rate);
	}

	protected String retrieveResponse(String url) throws Exception {
		URLConnection connection = new URL(url).openConnection();
		connection.setRequestProperty("Accept-Charset", CHARSET);
		InputStream response = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(response, CHARSET));
		String line = reader.readLine();
		assert(line != null);

		return line;
	}

	public float getRate(String pair) throws Exception {
		String url  = createUrlString(pair);
		String line = retrieveResponse(url);

		Float rate = parseResponse(line);
		if (rate < DELTA) {
			throw new NumberFormatException("getRate: invalid rate " + rate);
		}
		
		return rate;
	}
}
