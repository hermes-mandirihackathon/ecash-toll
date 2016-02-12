package com.mandiriecash.ecashtoll.services.models;

import java.sql.Timestamp;

public class Activity {
    private int id;
    private Timestamp time;
    private int vehicle_id;
    private int source_toll_id;
    private int dest_toll_id;
    private int price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
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
