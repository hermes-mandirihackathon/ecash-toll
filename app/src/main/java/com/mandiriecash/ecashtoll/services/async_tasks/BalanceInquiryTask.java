package com.mandiriecash.ecashtoll.services.async_tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.mandiriecash.ecashtoll.services.ETollSyncRESTClient;
import com.mandiriecash.ecashtoll.services.exceptions.ETollIOException;
import com.mandiriecash.ecashtoll.services.requests.BalanceInquiryRequest;
import com.mandiriecash.ecashtoll.services.responses.BalanceInquiryResponse;

public class BalanceInquiryTask extends AsyncTask<Void,Void,Boolean> {
    protected Context mContext;
    protected BalanceInquiryRequest mRequest;
    protected ETollSyncRESTClient mClient;

    protected BalanceInquiryResponse mResponse;
    protected Exception mException;

    public BalanceInquiryTask(Context context, ETollSyncRESTClient client, String msisdn, String token){
        mContext = context;
        mClient = client;
        mRequest = (new BalanceInquiryRequest.Builder())
            .msisdn(msisdn)
            .token(token)
            .build();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Boolean success = false;
        try {
            mResponse = mClient.balance(mRequest);
            if (mResponse.getStatus().equals("ok")){
                success = true;
            }
        } catch (ETollIOException e) {
            e.printStackTrace();
            mException = e;
        }
        return success;
    }

}