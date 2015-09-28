package com.berrakanil.travelmoney;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RequestHandlerTest {
    
    private final float DELTA = 0.0001f;

    protected void setUp(){}

    protected void tearDown(){}
    
    @Test
    public void testRateRequests_ValidCurrencyPair() throws Exception {
        IRateRetriever mockRateRetriever = mock(IRateRetriever.class);
        RequestHandler requestHandler = new RequestHandler(mockRateRetriever);
        String pair = "EURUSD";
        when(mockRateRetriever.getRate(pair)).thenReturn(5.0f);
        
        assertEquals("EURUSD",requestHandler.rateRequest("EURUSD").getCurrencyPair());
    }
    
    @Test(expected=Exception.class)
    public void testRateRequests_InvalidCurrencyPair() throws Exception {
        IRateRetriever mockRateRetriever = mock(IRateRetriever.class);
        RequestHandler requestHandler = new RequestHandler(mockRateRetriever);
        String responsePair = requestHandler.rateRequest("INVALID").getCurrencyPair();
    }
    
    @Test
    public void testRateRequests_ValidExchangeRate() throws Exception {
        IRateRetriever mockRateRetriever = mock(IRateRetriever.class);
        RequestHandler requestHandler = new RequestHandler(mockRateRetriever);
        
        String pair = "EURUSD";
        when(mockRateRetriever.getRate(pair)).thenReturn(5.0f);
        assertEquals(5.0f, requestHandler.rateRequest("EURUSD").getExchangeRate(), DELTA);
        when(mockRateRetriever.getRate(pair)).thenReturn(1.2f);
        assertEquals(1.2f, requestHandler.rateRequest("EURUSD").getExchangeRate(), DELTA);
    }
    
    @Test (expected=Exception.class)
    public void testRateRequests_NegativeExchangeRate() throws Exception {
        IRateRetriever mockRateRetriever = mock(IRateRetriever.class);
        RequestHandler requestHandler = new RequestHandler(mockRateRetriever);
        String pair = "EURUSD";
        when(mockRateRetriever.getRate(pair)).thenReturn(-2.0f);
        float rate = requestHandler.rateRequest("EURUSD").getExchangeRate();
    }
    
}
