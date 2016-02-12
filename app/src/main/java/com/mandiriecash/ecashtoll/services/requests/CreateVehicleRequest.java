package com.mandiriecash.ecashtoll.services.requests;

public class CreateVehicleRequest {
    String msisdn;
    String token;
    String name;
    String plate_no;

    public static class Builder {
        CreateVehicleRequest request = new CreateVehicleRequest();

        public Builder msisdn(String msisdn){ request.setMsisdn(msisdn); return this; }

        public Builder token(String token){ request.setToken(token); return this; }

        public Builder name(String name){ request.setName(name); return this; }

        public Builder plate_no(String plate_no){ request.setPlate_no(plate_no); return this;}

        public CreateVehicleRequest build(){ return request; }
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlate_no() {
        return plate_no;
    }

    public void setPlate_no(String plate_no) {
        this.plate_no = plate_no;
    }
}
