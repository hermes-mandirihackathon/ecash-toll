package com.mandiriecash.ecashtoll.services;

public class ETollURLFactory {
    public static final String BASE_URL = "http://etoll-api.mybluemix.net";
//    public static final String BASE_URL = "http://192.168.56.1:8080";
    /**
     * @param uid device id
     * @param msisdn phone number
     * @param credentials password
     * @return string
     */
    public String login(String uid,String msisdn,String credentials){
        return String.format("%s/%s?uid=%s&msisdn=%s&credentials=%s",BASE_URL,"login",uid,msisdn,credentials);
    }

    /**
     * @param token auth token
     * @param msisdn phone number
     * @return string
     */
    public String balance(String token,String msisdn){
        return String.format("%s/%s?token=%s&msisdn=%s",BASE_URL,"balance",token,msisdn);
    }

    /**
     * @param token auth token
     * @param msisdn phone number
     * @return string
     */
    public String getActivities(String token,String msisdn){
        return String.format("%s/%s?token=%s&msisdn=%s",BASE_URL,"activities/",token,msisdn);
    }

    public String createActivity(String msisdn,String token,long timestamp,int vehicle_id,int source_toll_id,int dest_toll_id,int price){
        return String.format("%s/%s?msisdn=%s&token=%s&timestamp=%s&vehicle_id=%d&source_toll_id=%d&dest_toll_id=%d&price=%d",
                BASE_URL,"activities/create",msisdn,token,timestamp,vehicle_id,source_toll_id,dest_toll_id,price);
    }

    /**
     * @param token auth token
     * @param msisdn phone number
     * @return string
     */
    public String getVehicles(String token,String msisdn){
        return String.format("%s/%s?token=%s&msisdn=%s",BASE_URL,"vehicles/",token,msisdn);
    }

    /**
     * @param token auth token
     * @param msisdn phone number
     * @param name vehicle name
     * @param plateNo vehicle plate number
     * @return string
     */
    public String createVehicle(String token,String msisdn,String name,String plateNo){
        return String.format("%s/%s?token=%s&msisdn=%s&name=%s&plate_no=%s",BASE_URL,"vehicles/create",token,msisdn,name,plateNo);
    }
}
