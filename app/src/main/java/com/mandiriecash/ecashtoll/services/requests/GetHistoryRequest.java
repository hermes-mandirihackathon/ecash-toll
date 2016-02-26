package com.mandiriecash.ecashtoll.services.requests;

public class GetHistoryRequest {
    private String msisdn;

    public static class Builder {
        GetHistoryRequest request = new GetHistoryRequest();

        public Builder msisdn(String msisdn){ request.setMsisdn(msisdn); return this; }

        public GetHistoryRequest build(){ return request; }
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
}
