package com.mandiriecash.ecashtoll;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity {
    static final int CREATE_VEHICLE_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { startCreateVehicleActivity(); }
        });
    }

    public void startCreateVehicleActivity(){
        Intent intent = new Intent(this,CreateVehicleActivity.class);
        startActivityForResult(intent,CREATE_VEHICLE_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_VEHICLE_ACTIVITY){
            if (resultCode == RESULT_OK){
                Toast.makeText(this,"TODO tambah kendaraan",Toast.LENGTH_LONG);
            }
        }
    }
}
