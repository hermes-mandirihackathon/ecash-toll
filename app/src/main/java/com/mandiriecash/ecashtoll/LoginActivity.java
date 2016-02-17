package com.mandiriecash.ecashtoll;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mandiriecash.ecashtoll.services.ETollSyncRESTClient;
import com.mandiriecash.ecashtoll.services.ETollSyncRESTClientImpl;
import com.mandiriecash.ecashtoll.services.async_tasks.LoginTask;



/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private TextView mErrorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mErrorTextView = (TextView) findViewById(R.id.error);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        //TODO uid
        UserLoginTask mAuthTask = new UserLoginTask(this, new ETollSyncRESTClientImpl(), "1", email, password);
        mAuthTask.execute();
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends LoginTask {
        Context mContext;
        ProgressDialog mProgressDialog;

        public UserLoginTask(Context context, ETollSyncRESTClient client, String uid, String msisdn, String credentials) {
            super(client, uid, msisdn, credentials);
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage("Logging in...");
            mProgressDialog.show();
            mErrorTextView.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mProgressDialog.dismiss();
            if (success) {
                SharedPreferences sharedPref = mContext.getSharedPreferences(
                        getString(R.string.preference_file_key),Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("token",mResponse.getToken());
                editor.putString("msisdn", mResponse.getMsisdn());
                editor.apply();
                Intent intent = new Intent(mContext,MainMenuActivity.class);
//                Intent intent = new Intent(mContext,CreateVehicleActivity.class);
                //TODO set as first intent with setflags?
                startActivity(intent);
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
                    mErrorTextView.setText(mResponse.getMessage());
                    mErrorTextView.setVisibility(View.VISIBLE);
                }
            }
        }

    }
}

