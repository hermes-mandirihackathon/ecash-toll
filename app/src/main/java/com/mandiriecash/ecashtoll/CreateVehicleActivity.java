package com.mandiriecash.ecashtoll;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mandiriecash.ecashtoll.services.ETollSyncRESTClient;
import com.mandiriecash.ecashtoll.services.ETollSyncRESTClientImpl;
import com.mandiriecash.ecashtoll.services.async_tasks.CreateVehicleTask;

/**
 * A login screen that offers login via email/password.
 */
public class CreateVehicleActivity extends AppCompatActivity {
    static final int SELECT_PICTURE = 1;

    Button selectPictureBtn;
    ImageView imageView;
    EditText mNameView;
    EditText mPlateView;
    TextView mErrorView;

    String selectedImagePath = null;
    String mMsisdn;
    String mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vehicle);

        selectPictureBtn = (Button) findViewById(R.id.selectPictureBtn);
        imageView = (ImageView) findViewById(R.id.imageView);
        mNameView = (EditText) findViewById(R.id.name);
        mPlateView = (EditText) findViewById(R.id.plateNo);
        mErrorView = (TextView) findViewById(R.id.error);

        selectPictureBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Pilih gambar kendaraan"), SELECT_PICTURE);

            }
        });

        findViewById(R.id.addVehicleBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mErrorView.setVisibility(View.GONE);
                attemptCreateVehicle();
            }
        });

        //add backbutton
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //get token and msisdn. if null, go to login page
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        mToken = sharedPref.getString("token",null);
        mMsisdn = sharedPref.getString("msisdn",null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == SELECT_PICTURE){
                Uri selectedImageUri = data.getData();
                selectedImagePath = getImagePath(selectedImageUri);
                imageView.setImageURI(selectedImageUri);
            }
        }
    }

    /**
     * helper to retrieve the path of an image URI
     */
    public String getImagePath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }

    private void attemptCreateVehicle(){
        //get string from text
        String name = mNameView.getText().toString();
        String plateNo = mPlateView.getText().toString();

        //perform http request
        ActivityCreateVehicleTask activityCreateVehicleTask = new ActivityCreateVehicleTask(
            this,new ETollSyncRESTClientImpl(),mMsisdn,mToken,name,plateNo
        );
        activityCreateVehicleTask.execute();
    }

    private class ActivityCreateVehicleTask extends CreateVehicleTask {
        private Context mContext;
        private ProgressDialog mProgressDialog;

        public ActivityCreateVehicleTask(Context context,ETollSyncRESTClient client, String msisdn, String token, String name, String plate_no) {
            super(client,msisdn,token,name,plate_no);
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage("Adding vehicle...");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mProgressDialog.dismiss();
            if (success){
                Toast.makeText(mContext,"Vehicle added",Toast.LENGTH_LONG).show();
            } else {
                if (mException != null){
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
                    mErrorView.setText(mResponse.getMessage());
                    mErrorView.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}

