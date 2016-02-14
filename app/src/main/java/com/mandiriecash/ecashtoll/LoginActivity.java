package com.mandiriecash.ecashtoll;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mandiriecash.ecashtoll.services.ETollSyncRESTClient;
import com.mandiriecash.ecashtoll.services.ETollSyncRESTClientImpl;
import com.mandiriecash.ecashtoll.services.exceptions.ETollIOException;
import com.mandiriecash.ecashtoll.services.requests.LoginRequest;
import com.mandiriecash.ecashtoll.services.responses.LoginResponse;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);

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
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        mAuthTask = new UserLoginTask(email, password);
        mAuthTask.execute();
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, LoginResponse> {
        private final String mMsisdn;
        private final String mCredentials;
        private Exception mException;

        UserLoginTask(String msisdn, String credentials) {
            mMsisdn = msisdn;
            mCredentials = credentials;
        }

        @Override
        protected LoginResponse doInBackground(Void... params) {
            LoginResponse response = null;
            try {
                //TODO change uid
                ETollSyncRESTClient client = new ETollSyncRESTClientImpl();
                response = client.login((new LoginRequest.Builder().
                        msisdn(mMsisdn).
                        credentials(mCredentials)).
                        build());
            } catch (ETollIOException e) {
                mException = e;
            }
            return response;
        }

        @Override
        protected void onPostExecute(final LoginResponse response) {
            Context context = getBaseContext();
            if (response != null) {
                String toastMessage;
                if (response.getStatus().equals("ok")) {
                    SharedPreferences sharedPref = context.getSharedPreferences(
                            getString(R.string.preference_file_key),Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("token",response.getToken());
                    editor.putString("msisdn",response.getMsisdn());
                    editor.apply();
                    Intent intent = new Intent(context,MainMenuActivity.class);
                    //TODO set as first intent with setflags?
                    startActivity(intent);
                } else {
                    toastMessage = response.getMessage();
                    Toast.makeText(getBaseContext(),toastMessage,Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getBaseContext(),mException.getMessage(),Toast.LENGTH_LONG).show();
            }
        }

    }
}

