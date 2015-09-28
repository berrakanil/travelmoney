package com.berrakanil.travelmoney;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestHandler {
    
    private final float DELTA = 0.0001f;
    private IRateRetriever m_retriever;
    
    public RequestHandler() { 
        m_retriever = new YahooRateRetriever();
    }
    
    public RequestHandler(IRateRetriever retriever) {
        this.m_retriever =  retriever;
    }
    
    @RequestMapping("/rate")
    public RateResponse rateRequest(@RequestParam(value="pair",defaultValue="EURUSD") String pair) throws Exception {
        float rate = m_retriever.getRate(pair);     
        if (rate < DELTA) {
            throw new Exception("Invalid Exchange Rate");
        }
        return new RateResponse(rate, pair);    
    }

}
