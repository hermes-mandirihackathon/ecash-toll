package com.mandiriecash.ecashtoll.services.models;

public class LogActivity {
    int id;
    int source_toll_id;
    int dest_toll_id;
    int price;
    String vehicle_name;
    long timestamp;

    public LogActivity(Activity activity,String vehicleName){
        setId(activity.getId());
        setSource_toll_id(activity.getSource_toll_id());
        setDest_toll_id(activity.getDest_toll_id());
        setPrice(activity.getPrice());
        setVehicle_name(vehicleName);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getVehicle_name() {
        return vehicle_name;
    }

    public void setVehicle_name(String vehicle_name) {
        this.vehicle_name = vehicle_name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
