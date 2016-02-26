package com.mandiriecash.ecashtoll.services;

import android.util.Log;

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
import com.mandiriecash.ecashtoll.services.responses.GetTollsResponse;
import com.mandiriecash.ecashtoll.services.responses.GetVehiclesResponse;
import com.mandiriecash.ecashtoll.services.responses.LoginResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ETollSyncRESTClientImpl implements ETollSyncRESTClient {
    final static String HTTP_REQUEST_TAG = "xhr";
    ETollURLFactory urlFactory = new ETollURLFactory();
    OkHttpClient client = new OkHttpClient.Builder().readTimeout(20000, TimeUnit.MILLISECONDS).build();
    Gson gson = new Gson();

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws ETollIOException, ETollHttpException {
        String url = urlFactory.login(loginRequest.getUid(),loginRequest.getMsisdn(),loginRequest.getCredentials());
        Request request = new Request.Builder()
                .url(url)
                .build();
        Log.d(HTTP_REQUEST_TAG,url);
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful())
                return gson.fromJson(response.body().charStream(), LoginResponse.class);
            else throw new ETollHttpException(response.code(),response.body().string());
        } catch (IOException e) {
            throw new ETollIOException(e);
        }
    }

    @Override
    public BalanceInquiryResponse balance(BalanceInquiryRequest balanceRequest) throws ETollIOException, ETollHttpException {
        String url = urlFactory.balance(balanceRequest.getToken(),balanceRequest.getMsisdn());
        Request request = new Request.Builder()
                .url(url)
                .build();
        Log.d(HTTP_REQUEST_TAG,url);
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                return gson.fromJson(response.body().charStream(),BalanceInquiryResponse.class);
            } else {
                throw new ETollHttpException(response.code(),response.body().string());
            }
        } catch (IOException e) {
            throw new ETollIOException(e);
        }
    }

    @Override
    public GetActivitiesResponse getActivities(GetActivitiesRequest getActivitiesRequest) throws ETollIOException, ETollHttpException {
        String url = urlFactory.getActivities(getActivitiesRequest.getToken(),getActivitiesRequest.getMsisdn());
        Request request = new Request.Builder()
                .url(url)
                .build();
        Log.d(HTTP_REQUEST_TAG,url);
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                return gson.fromJson(response.body().charStream(),GetActivitiesResponse.class);
            } else {
                throw new ETollHttpException(response.code(),response.body().string());
            }
        } catch (IOException e){
            throw new ETollIOException(e);
        }
    }


    @Override
    public CreateActivityResponse createActivity(CreateActivityRequest request) throws ETollIOException, ETollHttpException {
        String url = urlFactory.createActivity(request.getMsisdn(),request.getToken(),request.getTimestamp(),request.getVehicle_id()
                ,request.getSource_toll_id(),request.getDest_toll_id(),request.getPrice());

        Request httpRequest = new Request.Builder()
                .url(url)
                .build();
        Log.d(HTTP_REQUEST_TAG,url);

        try {
            Response response = client.newCall(httpRequest).execute();
            if (response.isSuccessful()){
                return gson.fromJson(response.body().charStream(),CreateActivityResponse.class);
            } else {
                throw new ETollHttpException(response.code(),response.body().string());
            }
        } catch (IOException e) {
            throw new ETollIOException(e);
        }
    }

    @Override
    public GetVehiclesResponse getVehicles(GetVehiclesRequest request) throws ETollIOException, ETollHttpException {
        String url = urlFactory.getVehicles(request.getToken(),request.getMsisdn());
        Request httpRequest = new Request.Builder()
                .url(url)
                .build();
        Log.d(HTTP_REQUEST_TAG,url);

        try {
            Response response = client.newCall(httpRequest).execute();
            if (response.isSuccessful()){
                return gson.fromJson(response.body().charStream(),GetVehiclesResponse.class);
            } else {
                throw new ETollHttpException(response.code(),response.body().string());
            }
        } catch (IOException e) {
            throw new ETollIOException(e);
        }
    }

    @Override
    public CreateVehicleResponse createVehicle(CreateVehicleRequest request) throws ETollIOException,ETollHttpException {
        String url = urlFactory.createVehicle(request.getToken(),request.getMsisdn(),request.getName(),request.getPlate_no());
        Request httpRequest = new Request.Builder()
                .url(url)
                .build();
        Log.d(HTTP_REQUEST_TAG,url);

        try {
            Response response = client.newCall(httpRequest).execute();
            if (response.isSuccessful()){
                return gson.fromJson(response.body().charStream(),CreateVehicleResponse.class);
            } else throw new ETollHttpException(response.code(),response.body().string());
        } catch (IOException e) {
            throw new ETollIOException(e);
        }
    }

    @Override
    public GetTollsResponse getTolls() throws ETollHttpException, ETollIOException {
        String url = urlFactory.getTolls();
        Request httpRequest = new Request.Builder()
                .url(url)
                .build();
        Log.d(HTTP_REQUEST_TAG,url);

        try {
            Response response = client.newCall(httpRequest).execute();
            if (response.isSuccessful()){
                return gson.fromJson(response.body().charStream(),GetTollsResponse.class);
            } else throw new ETollHttpException(response.code(),response.body().string());
        } catch (IOException e) {
            throw new ETollIOException(e);
        }
    }


}
