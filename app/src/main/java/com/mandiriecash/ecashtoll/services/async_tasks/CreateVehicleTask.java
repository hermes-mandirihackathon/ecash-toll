package com.mandiriecash.ecashtoll.services.async_tasks;

import android.os.AsyncTask;

import com.mandiriecash.ecashtoll.services.ETollHttpException;
import com.mandiriecash.ecashtoll.services.ETollSyncRESTClient;
import com.mandiriecash.ecashtoll.services.exceptions.ETollIOException;
import com.mandiriecash.ecashtoll.services.requests.CreateVehicleRequest;
import com.mandiriecash.ecashtoll.services.responses.CreateVehicleResponse;

public class CreateVehicleTask extends AsyncTask<Void,Void,Boolean>{
    protected ETollSyncRESTClient mClient;
    protected CreateVehicleRequest mRequest;

    protected CreateVehicleResponse mResponse;
    protected Exception mException;

    public CreateVehicleTask(ETollSyncRESTClient client,String msisdn,String token,String name,
                             String plate_no){
        mRequest = new CreateVehicleRequest.Builder()
            .msisdn(msisdn)
            .token(token)
            .name(name)
            .plate_no(plate_no).build();
        mClient = client;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Boolean success = false;
        try {
            mResponse = mClient.createVehicle(mRequest);
            if (mResponse.getStatus().equals("ok")){
                success = true;
            }
        } catch (ETollIOException | ETollHttpException e) {
            mException = e;
        }
        return success;
    }
}
