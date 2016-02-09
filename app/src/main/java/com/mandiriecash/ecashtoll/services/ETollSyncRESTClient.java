package com.mandiriecash.ecashtoll.services;

import com.mandiriecash.ecashtoll.services.exceptions.ETollIOException;
import com.mandiriecash.ecashtoll.services.requests.BalanceInquiryRequest;
import com.mandiriecash.ecashtoll.services.requests.LoginRequest;
import com.mandiriecash.ecashtoll.services.responses.BalanceInquiryResponse;
import com.mandiriecash.ecashtoll.services.responses.LoginResponse;

import java.io.IOException;

public interface ETollSyncRESTClient {
    public LoginResponse login(LoginRequest request) throws ETollIOException;

    public BalanceInquiryResponse balance(BalanceInquiryRequest request) throws ETollIOException;
}
