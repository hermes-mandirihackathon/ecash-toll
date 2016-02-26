package com.mandiriecash.ecashtoll.services.models;

/**
 * Created by yafi on 26-Feb-16.
 */
public class Plan {
    private int source_id;
    private String source_name;
    private String dest_name;
    private int dest_id;
    String msisdn;
    private int price;

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

    public int getDest_id() {
        return dest_id;
    }

    public void setDest_id(int dest_id) {
        this.dest_id = dest_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
