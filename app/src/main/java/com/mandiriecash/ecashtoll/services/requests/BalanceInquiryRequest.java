package com.mandiriecash.ecashtoll.services.requests;

public class BalanceInquiryRequest {
    public String msisdn;
    public String token;

    public static class Builder {
        BalanceInquiryRequest request = new BalanceInquiryRequest();

        public Builder msisdn(String msisdn){ request.setMsisdn(msisdn); return this; }

        public Builder token(String token){ request.setToken(token); return this; }

        public BalanceInquiryRequest build(){ return request; }
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
