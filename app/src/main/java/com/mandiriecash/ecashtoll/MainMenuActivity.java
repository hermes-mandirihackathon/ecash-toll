package com.mandiriecash.ecashtoll;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.mandiriecash.ecashtoll.dummy.ListVehicleContent;
import com.mandiriecash.ecashtoll.services.ETollSyncRESTClient;
import com.mandiriecash.ecashtoll.services.ETollSyncRESTClientImpl;
import com.mandiriecash.ecashtoll.services.async_tasks.BalanceInquiryTask;
import com.mandiriecash.ecashtoll.services.models.LogActivity;

public class MainMenuActivity extends AppCompatActivity implements VehicleFragment.OnListFragmentInteractionListener, LogActivityFragment.OnListFragmentInteractionListener{
    public static final int CREATE_VEHICLE_ACTIVITY = 1;
    MainMenuPagerAdapter mMainMenuPagerAdapter;
    ViewPager mViewPager;

    TextView mBalanceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMainMenuPagerAdapter = new MainMenuPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mMainMenuPagerAdapter);

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
            System.out.println(savedInstanceState.getInt("fid"));
            mViewPager.setCurrentItem(savedInstanceState.getInt("fid"));
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
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        String token = sharedPref.getString("token",null);
        String msisdn = sharedPref.getString("msisdn",null);
        if (token == null){
            toLoginActivity();
        }
        MainMenuBalanceInquiryTask balanceInqTask = new MainMenuBalanceInquiryTask(
                this,new ETollSyncRESTClientImpl(),mBalanceTextView,msisdn,token);
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
    public void onListFragmentInteraction(ListVehicleContent.Vehicle item) {
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
        protected void onPostExecute(Boolean success) {
            if (success){
                mBalanceTextView.setText(mResponse.getAccountBalance());
            } else {
                String toastMessage;
                if (mException != null) {
                    toastMessage = mException.getMessage();
                } else {
                    toastMessage = mResponse.getMessage();
                }
                Toast.makeText(mContext,toastMessage,Toast.LENGTH_LONG).show();
            }
        }
    }

    private void toLoginActivity(){
        Intent intent = new Intent(this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
