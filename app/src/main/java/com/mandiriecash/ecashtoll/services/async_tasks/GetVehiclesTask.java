package com.mandiriecash.ecashtoll.services.async_tasks;

import android.os.AsyncTask;

import com.mandiriecash.ecashtoll.services.ETollHttpException;
import com.mandiriecash.ecashtoll.services.ETollSyncRESTClient;
import com.mandiriecash.ecashtoll.services.exceptions.ETollIOException;
import com.mandiriecash.ecashtoll.services.requests.GetVehiclesRequest;
import com.mandiriecash.ecashtoll.services.responses.GetVehiclesResponse;

/**
 * Created by Ichwan Haryo Sembodo on 23/02/2016.
 */
public class GetVehiclesTask extends AsyncTask<Void,Void,Boolean> {
    protected GetVehiclesRequest mRequest;
    protected ETollSyncRESTClient mClient;

    protected Exception mException;
    protected GetVehiclesResponse mResponse;

    public GetVehiclesTask(ETollSyncRESTClient client,String msisdn,String token) {
        mRequest = new GetVehiclesRequest.Builder()
                .msisdn(msisdn)
                .token(token)
                .build();
        mClient = client;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Boolean success = false;
        try {
            mResponse = mClient.getVehicles(mRequest);
            if (mResponse.getStatus().equals("ok")){
                success = true;
            }
        } catch (ETollIOException | ETollHttpException e) {
            mException = e;
            e.printStackTrace();
        }
        return success;
    }
}
