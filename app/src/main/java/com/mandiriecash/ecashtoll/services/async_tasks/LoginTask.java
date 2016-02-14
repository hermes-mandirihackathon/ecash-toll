package com.mandiriecash.ecashtoll.services.async_tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.mandiriecash.ecashtoll.services.ETollSyncRESTClient;
import com.mandiriecash.ecashtoll.services.exceptions.ETollIOException;
import com.mandiriecash.ecashtoll.services.requests.LoginRequest;
import com.mandiriecash.ecashtoll.services.responses.LoginResponse;

public class LoginTask extends AsyncTask<Void, Void, Boolean> {
    protected ETollSyncRESTClient mClient;
    protected LoginRequest mRequest;

    protected Exception mException;
    protected LoginResponse mResponse;

    public LoginTask(ETollSyncRESTClient client, String uid, String msisdn, String credentials) {
        mClient = client;
        mRequest = (new LoginRequest.Builder()).uid(uid).msisdn(msisdn).credentials(credentials).build();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Boolean success = false;
        try {
            mResponse = mClient.login(mRequest);
            if (mResponse.getStatus().equals("ok")){
                success = true;
            }
        } catch (ETollIOException e) {
            mException = e;
        }
        return success;
    }

}
