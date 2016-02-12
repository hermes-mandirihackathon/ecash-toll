package com.mandiriecash.ecashtoll.services;

import com.google.gson.Gson;
import com.mandiriecash.ecashtoll.services.exceptions.ETollIOException;
import com.mandiriecash.ecashtoll.services.requests.BalanceInquiryRequest;
import com.mandiriecash.ecashtoll.services.requests.CreateActivityRequest;
import com.mandiriecash.ecashtoll.services.requests.CreateVehicleRequest;
import com.mandiriecash.ecashtoll.services.requests.GetActivitiesRequest;
import com.mandiriecash.ecashtoll.services.requests.GetVehiclesRequest;
import com.mandiriecash.ecashtoll.services.requests.LoginRequest;
import com.mandiriecash.ecashtoll.services.responses.BalanceInquiryResponse;
import com.mandiriecash.ecashtoll.services.responses.CreateActivityResponse;
import com.mandiriecash.ecashtoll.services.responses.CreateVehicleResponse;
import com.mandiriecash.ecashtoll.services.responses.GetActivitiesResponse;
import com.mandiriecash.ecashtoll.services.responses.GetVehiclesResponse;
import com.mandiriecash.ecashtoll.services.responses.LoginResponse;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ETollSyncRESTClientImpl implements ETollSyncRESTClient {
    ETollURLFactory urlFactory = new ETollURLFactory();
    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws ETollIOException {
        Request request = new Request.Builder()
                .url(urlFactory.login(loginRequest.getUid(),loginRequest.getMsisdn(),loginRequest.getCredentials()))
                .build();
        try {
            Response response = client.newCall(request).execute();
            return gson.fromJson(response.body().charStream(), LoginResponse.class);
        } catch (IOException e) {
            throw new ETollIOException(e);
        }
    }

    @Override
    public BalanceInquiryResponse balance(BalanceInquiryRequest balanceRequest) throws ETollIOException {
        Request request = new Request.Builder()
                .url(urlFactory.balance(balanceRequest.getToken(),balanceRequest.getMsisdn()))
                .build();
        try {
            Response response = client.newCall(request).execute();
            return gson.fromJson(response.body().charStream(),BalanceInquiryResponse.class);
        } catch (IOException e) {
            throw new ETollIOException(e);
        }
    }

    @Override
    public GetActivitiesResponse getActivities(GetActivitiesRequest getActivitiesRequest) throws ETollIOException {
        Request request = new Request.Builder()
                .url(urlFactory.getActivities(getActivitiesRequest.getToken(),getActivitiesRequest.getMsisdn()))
                .build();
        try {
            Response response = client.newCall(request).execute();
            return gson.fromJson(response.body().charStream(),GetActivitiesResponse.class);
        } catch (IOException e){
            throw new ETollIOException(e);
        }
    }


    @Override
    public CreateActivityResponse createActivity(CreateActivityRequest request) throws ETollIOException {
        Request httpRequest = new Request.Builder()
                .url(urlFactory.createActivity(request.getMsisdn(),request.getToken(),request.getTimestamp(),request.getVehicle_id()
                    ,request.getSource_toll_id(),request.getDest_toll_id(),request.getPrice()))
                .build();
        try {
            Response response = client.newCall(httpRequest).execute();
            return gson.fromJson(response.body().charStream(),CreateActivityResponse.class);
        } catch (IOException e) {
            throw new ETollIOException(e);
        }
    }

    @Override
    public GetVehiclesResponse getVehicles(GetVehiclesRequest request) throws ETollIOException {
        Request httpRequest = new Request.Builder()
                .url(urlFactory.getVehicles(request.getToken(),request.getMsisdn()))
                .build();
        try {
            Response response = client.newCall(httpRequest).execute();
            return gson.fromJson(response.body().charStream(),GetVehiclesResponse.class);
        } catch (IOException e) {
            throw new ETollIOException(e);
        }
    }

    @Override
    public CreateVehicleResponse createVehicle(CreateVehicleRequest request) throws ETollIOException {
        Request httpRequest = new Request.Builder()
                .url(urlFactory.createVehicle(request.getToken(),request.getMsisdn(),request.getName(),request.getPlate_no()))
                .build();
        try {
            Response response = client.newCall(httpRequest).execute();
            return gson.fromJson(response.body().charStream(),CreateVehicleResponse.class);
        } catch (IOException e) {
            throw new ETollIOException(e);
        }
    }
}
