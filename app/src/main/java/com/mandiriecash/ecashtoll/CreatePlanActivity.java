package com.mandiriecash.ecashtoll;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mandiriecash.ecashtoll.services.ETollHttpException;
import com.mandiriecash.ecashtoll.services.ETollSyncRESTClient;
import com.mandiriecash.ecashtoll.services.ETollSyncRESTClientImpl;
import com.mandiriecash.ecashtoll.services.exceptions.ETollIOException;
import com.mandiriecash.ecashtoll.services.models.Toll;
import com.mandiriecash.ecashtoll.services.requests.CreatePlanRequest;
import com.mandiriecash.ecashtoll.services.responses.CreatePlanResponse;
import com.mandiriecash.ecashtoll.services.responses.GetTollsResponse;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreatePlanActivity extends AppCompatActivity {

    Spinner mSourceTollSpinner;
    Spinner mDestTollSpinner;

    ETollSyncRESTClient mClient = new ETollSyncRESTClientImpl();

    List<String> mSourceTolls = new ArrayList<>();
    List<String> mDestTolls = new ArrayList<>();

    Map<String,Integer> tollIds = new HashMap<>();

    String mMsisdn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);

        mSourceTollSpinner = (Spinner) findViewById(R.id.sourceToll);
        mDestTollSpinner = (Spinner) findViewById(R.id.destToll);

        //set spinner data
        //button event listener
        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            createPlan();
//                Toast.makeText(getBaseContext(),""+getTollId((String) mSourceTollSpinner.getSelectedItem()),Toast.LENGTH_LONG).show();
            }
        });

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        mMsisdn = sharedPref.getString("msisdn",null);

        GetTollsTask task = new GetTollsTask(this,mClient);
        task.execute();
    }

    void createPlan(){
        String source_name = (String) mSourceTollSpinner.getSelectedItem();
        String dest_name = (String) mDestTollSpinner.getSelectedItem();
        int source_id = getTollId(source_name);
        int dest_id = getTollId(dest_name);
        //TODO HARDCODE
        CreatePlanTask task = new CreatePlanTask(this,mClient,mMsisdn,source_id,source_name,dest_id,dest_name,1000);
        task.execute();
    }


    class CreatePlanTask extends AsyncTask<Void,Void,Boolean>
    {
        Context mContext;
        ETollSyncRESTClient mClient;
        Exception mException;
        CreatePlanResponse mResponse;
        CreatePlanRequest mRequest;

        public CreatePlanTask(Context context,ETollSyncRESTClient client,String msisdn,
                              int source_id,String source_name,int dest_id,String dest_name,int price){
            this.mContext = context;
            this.mClient = client;
            mRequest = (new CreatePlanRequest.Builder())
                    .msisdn(msisdn)
                    .source_id(source_id)
                    .dest_id(dest_id)
                    .source_name(source_name)
                    .dest_name(dest_name)
                    .price(price)
                    .build();
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean success = false;
            try {
                mResponse = mClient.createPlan(mRequest);
                if (mResponse.getStatus().equalsIgnoreCase("ok")){
                    success = true;
                }
            } catch (ETollHttpException | ETollIOException e) {
                mException = e;
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success){
            } else {
                if (mException != null) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage(mException.getMessage());
                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                } else {
                    Toast.makeText(mContext,mResponse.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    class GetTollsTask extends AsyncTask<Void,Void,Boolean>
    {
        Context mContext;
        ETollSyncRESTClient mClient;
        Exception mException;
        GetTollsResponse mResponse;

        public GetTollsTask(Context context,ETollSyncRESTClient client){
            this.mContext = context;
            this.mClient = client;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean success = false;
            try {
                mResponse = mClient.getTolls();
                if (mResponse.getStatus().equalsIgnoreCase("ok")){
                    success = true;
                }
            } catch (ETollHttpException | ETollIOException e) {
                mException = e;
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success){
                List<Toll> tolls = mResponse.getTolls();
                for(Toll toll:tolls){
                    mSourceTolls.add(toll.getName());
                    mDestTolls.add(toll.getName());
                    tollIds.put(toll.getName(),toll.getId());
                }
                ArrayAdapter<String> sourceArrAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, mSourceTolls);
                ArrayAdapter<String> destArrAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, mDestTolls);

                mSourceTollSpinner.setAdapter(sourceArrAdapter);
                mDestTollSpinner.setAdapter(destArrAdapter);
            } else {
                if (mException != null) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage(mException.getMessage());
                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                } else {
                    Toast.makeText(mContext,mResponse.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    Integer getTollId(String name){
        Integer id = this.tollIds.get(name);
        return id;
    }

}
