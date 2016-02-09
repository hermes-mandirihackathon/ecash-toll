package com.mandiriecash.ecashtoll.services;

import com.google.gson.Gson;
import com.mandiriecash.ecashtoll.services.exceptions.ETollIOException;
import com.mandiriecash.ecashtoll.services.requests.BalanceInquiryRequest;
import com.mandiriecash.ecashtoll.services.requests.LoginRequest;
import com.mandiriecash.ecashtoll.services.responses.BalanceInquiryResponse;
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
}
