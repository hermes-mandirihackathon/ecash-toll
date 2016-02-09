package com.mandiriecash.ecashtoll.services.requests;

public class LoginRequest {
    String msisdn;
    String uid;
    String credentials;

    public static class Builder {
        LoginRequest request = new LoginRequest();

        public Builder msisdn(String msisdn){ request.setMsisdn(msisdn); return this; }

        public Builder uid(String uid){ request.setUid(uid); return this; }

        public Builder credentials(String credentials){ request.setCredentials(credentials); return this; }

        public LoginRequest build(){ return request; }
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }
}
