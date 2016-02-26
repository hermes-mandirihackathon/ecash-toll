package com.mandiriecash.ecashtoll;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mandiriecash.ecashtoll.services.ETollSyncRESTClient;
import com.mandiriecash.ecashtoll.services.ETollSyncRESTClientImpl;
import com.mandiriecash.ecashtoll.services.async_tasks.BalanceInquiryTask;
import com.mandiriecash.ecashtoll.services.models.LogActivity;
import com.mandiriecash.ecashtoll.services.models.Plan;
import com.mandiriecash.ecashtoll.services.models.Vehicle;

public class MainMenuActivity extends AppCompatActivity
        implements VehicleFragment.OnListFragmentInteractionListener, LogActivityFragment.OnListFragmentInteractionListener,PlanFragment.OnListFragmentInteractionListener {
    public static final int CREATE_VEHICLE_ACTIVITY = 1;
    MainMenuPagerAdapter mMainMenuPagerAdapter;
    ViewPager mViewPager;
    ETollSyncRESTClient mETollSyncRESTClient;

    TextView mBalanceTextView;
    ProgressBar mProgressBar;

    private String mMsisdn;
    private String mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMainMenuPagerAdapter = new MainMenuPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mMainMenuPagerAdapter);

        mProgressBar = (ProgressBar) findViewById(R.id.bar);

        mBalanceTextView = (TextView) findViewById(R.id.balance);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Log"));
        tabLayout.addTab(tabLayout.newTab().setText("Kendaraan"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        if (savedInstanceState != null) {
            //Restore the fragment's instance
            mViewPager.setCurrentItem(savedInstanceState.getInt("fid"));
        }

        mETollSyncRESTClient = new ETollSyncRESTClientImpl();

        //get token and msisdn. if null, go to login page
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        mToken = sharedPref.getString("token",null);
        mMsisdn = sharedPref.getString("msisdn",null);
        //TODO check if expired token
        if (mToken == null){
            toLoginActivity();
        }


//TODO hapus diganti di fragment
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) { startCreateVehicleActivity(); }
//        });
    }

//    public void startCreateVehicleActivity(){
//        Intent intent = new Intent(this,CreateVehicleActivity.class);
//        startActivityForResult(intent,CREATE_VEHICLE_ACTIVITY);
//    }


    @Override
    protected void onResume() {
        super.onResume();
        mETollSyncRESTClient = new ETollSyncRESTClientImpl();
        MainMenuBalanceInquiryTask balanceInqTask = new MainMenuBalanceInquiryTask(
                this,getmETollSyncRESTClient(),mBalanceTextView,mMsisdn,mToken);
        balanceInqTask.execute();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("fid",mViewPager.getCurrentItem());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_VEHICLE_ACTIVITY){
            if (resultCode == RESULT_OK){
                Toast.makeText(this,"TODO tambah kendaraan",Toast.LENGTH_LONG);
            }
        }
    }

    @Override
    public void onListFragmentInteraction(Vehicle item) {
        //TODO impl
    }

    @Override
    public void onListFragmentInteraction(LogActivity item) {
        //TODO impl
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public void onListFragmentInteraction(Plan item) {
        //TODO impl
    }

    private class MainMenuBalanceInquiryTask extends BalanceInquiryTask {
        private Context mContext;
        private TextView mBalanceTextView;

        public MainMenuBalanceInquiryTask(Context context, ETollSyncRESTClient client,
                                          TextView balanceTextView, String msisdn, String token) {
            super(client, msisdn, token);
            mBalanceTextView = balanceTextView;
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
            mBalanceTextView.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mProgressBar.setVisibility(View.GONE);
            mBalanceTextView.setVisibility(View.VISIBLE);
            if (success){
                mBalanceTextView.setText(mResponse.getAccountBalance());
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

    private void toLoginActivity(){
        Intent intent = new Intent(this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public ETollSyncRESTClient getmETollSyncRESTClient() {
        return mETollSyncRESTClient;
    }

    public String getmMsisdn() {
        return mMsisdn;
    }

    public String getmToken() {
        return mToken;
    }
}
