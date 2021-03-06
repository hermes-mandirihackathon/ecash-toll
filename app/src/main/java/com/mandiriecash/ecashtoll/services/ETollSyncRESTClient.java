package com.mandiriecash.ecashtoll.services;

import com.mandiriecash.ecashtoll.services.exceptions.ETollIOException;
import com.mandiriecash.ecashtoll.services.requests.BalanceInquiryRequest;
import com.mandiriecash.ecashtoll.services.requests.CreateActivityRequest;
import com.mandiriecash.ecashtoll.services.requests.CreatePlanRequest;
import com.mandiriecash.ecashtoll.services.requests.CreateVehicleRequest;
import com.mandiriecash.ecashtoll.services.requests.GetActivitiesRequest;
import com.mandiriecash.ecashtoll.services.requests.GetHistoryRequest;
import com.mandiriecash.ecashtoll.services.requests.GetPlansRequest;
import com.mandiriecash.ecashtoll.services.requests.GetVehiclesRequest;
import com.mandiriecash.ecashtoll.services.requests.LoginRequest;
import com.mandiriecash.ecashtoll.services.responses.BalanceInquiryResponse;
import com.mandiriecash.ecashtoll.services.responses.CreateActivityResponse;
import com.mandiriecash.ecashtoll.services.responses.CreatePlanResponse;
import com.mandiriecash.ecashtoll.services.responses.CreateVehicleResponse;
import com.mandiriecash.ecashtoll.services.responses.GetActivitiesResponse;
import com.mandiriecash.ecashtoll.services.responses.GetHistoryResponse;
import com.mandiriecash.ecashtoll.services.responses.GetPlansResponse;
import com.mandiriecash.ecashtoll.services.responses.GetTollsResponse;
import com.mandiriecash.ecashtoll.services.responses.GetVehiclesResponse;
import com.mandiriecash.ecashtoll.services.responses.LoginResponse;

import java.io.IOException;

public interface ETollSyncRESTClient {
    public LoginResponse login(LoginRequest request) throws ETollIOException,ETollHttpException;

    public BalanceInquiryResponse balance(BalanceInquiryRequest request) throws ETollIOException,ETollHttpException;

    public GetActivitiesResponse getActivities(GetActivitiesRequest request) throws ETollIOException,ETollHttpException;

    public CreateActivityResponse createActivity(CreateActivityRequest request) throws ETollIOException,ETollHttpException;

    public GetVehiclesResponse getVehicles(GetVehiclesRequest request) throws ETollIOException,ETollHttpException;

    public CreateVehicleResponse createVehicle(CreateVehicleRequest request) throws ETollIOException,ETollHttpException;

    public GetTollsResponse getTolls() throws ETollHttpException,ETollIOException;

    GetPlansResponse getPlans(GetPlansRequest request) throws ETollHttpException,ETollIOException;

    GetHistoryResponse getHistory(GetHistoryRequest request) throws ETollHttpException,ETollIOException;

    CreatePlanResponse createPlan(CreatePlanRequest request) throws ETollHttpException,ETollIOException;
}
