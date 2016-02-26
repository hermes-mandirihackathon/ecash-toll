package com.mandiriecash.ecashtoll.services.requests;

/**
 * Created by yafi on 27-Feb-16.
 */
public class GetPlansRequest {
    private String msisdn;

    public static class Builder {
        GetPlansRequest request = new GetPlansRequest();

        public Builder msisdn(String msisdn){ request.setMsisdn(msisdn); return this; }

        public GetPlansRequest build(){ return request; }
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
}
