package com.berrakanil.travelmoney;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class YahooRateRetrieverTest {
	
	private final String responseTemplate = 
			"{\"query\":"
		 	+   "{\"count\":1,"
			+    "\"created\":\"2015-09-03T21:51:02Z\","
			+    "\"lang\":\"en-US\","
			+    "\"results\":"
			+        "{\"rate\":"
			+          "{\"id\":\"%s\","
			+           "\"Name\":\"%s\","
			+           "\"Rate\":\"%s\","
			+           "\"Date\":\"9/3/2015\","
			+           "\"Time\":\"10:50pm\","
			+           "\"Ask\":\"133.5400\","
			+           "\"Bid\":\"133.5000\""
			+           "}"
			+        "}"
			+    "}"
			+ "}";
	
	private final float DELTA = 0.0001f;

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testParseResponse_validLine() {
		String line = String.format(responseTemplate, "USDGBP","USD/GBP","0.5");
		YahooRateRetriever retriever = new YahooRateRetriever();
		assertEquals(0.5, retriever.parseResponse(line), DELTA);	
	}
	
	@Test(expected=Exception.class)
	public void testParseResponse_invalidStringRate() throws Exception {
		String line = String.format(responseTemplate, "USDGBP","USD/GBP","f");
		YahooRateRetriever retriever = new YahooRateRetriever();
		float rate = retriever.parseResponse(line);
	}
	
	@Test(expected=Exception.class)
	public void testParseResponse_EmptyResponse() throws Exception {
		String line = "";
		YahooRateRetriever retriever = new YahooRateRetriever();
		float rate = retriever.parseResponse(line);
	}
	
	@Test(expected=Exception.class)
	public void testParseResponse_invalidLine() throws Exception {
		String line = "invalid line";
		YahooRateRetriever retriever = new YahooRateRetriever();
		float rate = retriever.parseResponse(line);
	}

	@Test(expected=Exception.class)
	public void testGetRate_invalidNegativeRate() throws Exception {
		YahooRateRetriever retriever = new YahooRateRetriever() {
			protected float parseResponse(String line) {
				return -1.23f;
			}
		};
		float rate = retriever.getRate("EURUSD");
	}
	
	@Test(expected=Exception.class)
	public void testGetRate_invalidZeroRate() throws Exception {
		YahooRateRetriever retriever = new YahooRateRetriever() {
			protected float parseResponse(String line) {
				return 0.0f;
			}
		};
		float rate = retriever.getRate("EURUSD");
	}
	
	@Test
	public void testGetRate_validRateWith2Precision() throws Exception {
		YahooRateRetriever retriever = new YahooRateRetriever() {
			protected float parseResponse(String line) {
				return 1.12f;
			}
		};
		float rate = retriever.getRate("EURUSD");
		assertEquals(1.12f, rate, DELTA);
	}
	
	@Test
	public void testGetRate_validRateWith4Precision() throws Exception {
		YahooRateRetriever retriever = new YahooRateRetriever() {
			protected float parseResponse(String line) {
				return 2.3415f;
			}
		};
		float rate = retriever.getRate("GBPTRY");
		assertEquals(2.3415f, rate, DELTA);
	}
}
