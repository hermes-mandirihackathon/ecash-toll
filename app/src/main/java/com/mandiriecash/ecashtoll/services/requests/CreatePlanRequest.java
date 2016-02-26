package com.mandiriecash.ecashtoll.services.requests;

/**
 * Created by yafi on 27-Feb-16.
 */
public class CreatePlanRequest {
    String msisdn;
    int source_id;
    int dest_toll_id;
    String source_name;
    String dest_name;
    int price;

    public static class Builder {
        CreatePlanRequest request = new CreatePlanRequest();

        public Builder msisdn(String msisdn){ request.setMsisdn(msisdn); return this; }

        public Builder source_id(int source_toll_id){ request.setSource_id(source_toll_id); return this;}

        public Builder dest_id(int dest_toll_id){ request.setDest_toll_id(dest_toll_id); return this;}

        public Builder source_name(String source_name){ request.setSource_name(source_name); return this;}

        public Builder dest_name(String dest_name){ request.setDest_name(dest_name); return this;}

        public Builder price(int price){ request.setPrice(price); return this; }

        public CreatePlanRequest build(){ return request; }
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    public String getDest_name() {
        return dest_name;
    }

    public void setDest_name(String dest_name) {
        this.dest_name = dest_name;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public int getSource_id() {
        return source_id;
    }

    public void setSource_id(int source_id) {
        this.source_id = source_id;
    }

    public int getDest_toll_id() {
        return dest_toll_id;
    }

    public void setDest_toll_id(int dest_toll_id) {
        this.dest_toll_id = dest_toll_id;
    }
}
