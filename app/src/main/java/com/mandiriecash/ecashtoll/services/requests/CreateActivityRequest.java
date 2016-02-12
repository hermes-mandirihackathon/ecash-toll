package com.mandiriecash.ecashtoll.services.requests;

public class CreateActivityRequest {
    String msisdn;
    String token;
    long timestamp;
    int vehicle_id;
    int source_toll_id;
    int dest_toll_id;
    int price;

    public static class Builder {
        CreateActivityRequest request = new CreateActivityRequest();

        public Builder msisdn(String msisdn){ request.setMsisdn(msisdn); return this; }

        public Builder token(String token){ request.setToken(token); return this; }

        public Builder timestamp(long timestamp){ request.setTimestamp(timestamp); return this;}

        public Builder vehicle_id(int vehicle_id){ request.setVehicle_id(vehicle_id); return this; }

        public Builder source_toll_id(int source_toll_id){ request.setSource_toll_id(source_toll_id); return this; }

        public Builder dest_toll_id(int dest_toll_id){ request.setDest_toll_id(dest_toll_id); return this; }

        public Builder price(int price){ request.setPrice(price); return this; }

        public CreateActivityRequest build(){ return request; }
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public int getSource_toll_id() {
        return source_toll_id;
    }

    public void setSource_toll_id(int source_toll_id) {
        this.source_toll_id = source_toll_id;
    }

    public int getDest_toll_id() {
        return dest_toll_id;
    }

    public void setDest_toll_id(int dest_toll_id) {
        this.dest_toll_id = dest_toll_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
