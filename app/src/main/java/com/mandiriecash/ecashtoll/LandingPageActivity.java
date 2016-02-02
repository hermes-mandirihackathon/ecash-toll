package com.mandiriecash.ecashtoll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class LandingPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        findViewById(R.id.registerBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO impl to register activity
            }
        });
        findViewById(R.id.loginBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO impl to login activity
            }
        });
    }

}
