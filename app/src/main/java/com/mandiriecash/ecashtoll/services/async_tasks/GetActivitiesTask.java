package com.mandiriecash.ecashtoll.services.async_tasks;

import android.os.AsyncTask;

import com.mandiriecash.ecashtoll.LogActivityViewAdapter;
import com.mandiriecash.ecashtoll.services.ETollSyncRESTClient;
import com.mandiriecash.ecashtoll.services.exceptions.ETollIOException;
import com.mandiriecash.ecashtoll.services.requests.GetActivitiesRequest;
import com.mandiriecash.ecashtoll.services.responses.GetActivitiesResponse;

public class GetActivitiesTask extends AsyncTask<Void,Void,Boolean>{
    protected GetActivitiesRequest mRequest;
    protected ETollSyncRESTClient mClient;

    protected Exception mException;
    protected GetActivitiesResponse mResponse;


    public GetActivitiesTask(ETollSyncRESTClient client,String msisdn,String token) {
        mRequest = new GetActivitiesRequest.Builder()
            .msisdn(msisdn)
            .token(token)
            .build();
        mClient = client;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Boolean success = false;
        try {
            mResponse = mClient.getActivities(mRequest);
            if (mResponse.getStatus().equals("ok")){
                success = true;
            }
        } catch (ETollIOException e) {
            mException = e;
        }
        return success;
    }
}
