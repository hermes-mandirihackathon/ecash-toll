package com.mandiriecash.ecashtoll.services;

public class ETollURLFactory {
    //TODO change
    public static final String BASE_URL = "";

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
        return String.format("%s/%s?token=%s&msisdn=%s",BASE_URL,"activities",token,msisdn);
    }

    /**
     * @param token auth token
     * @param msisdn phone number
     * @return string
     */
    public String getVehicles(String token,String msisdn){
        return String.format("%s/%s?token=%s&msisdn=%s",BASE_URL,"vehicles",token,msisdn);
    }

    /**
     * @param token auth token
     * @param msisdn phone number
     * @param name vehicle name
     * @param plateNo vehicle plate number
     * @return string
     */
    public String createVehicle(String token,String msisdn,String name,String plateNo){
        return String.format("%s/%s?token=%s&msisdn=%s&name=%s&plateNo=%s",BASE_URL,"vehicles/create",token,msisdn,name,plateNo);
    }
}
