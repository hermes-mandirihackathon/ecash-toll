package com.mandiriecash.ecashtoll;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mandiriecash.ecashtoll.services.models.Vehicle;
import com.mandiriecash.ecashtoll.services.ETollSyncRESTClient;
import com.mandiriecash.ecashtoll.services.async_tasks.GetVehiclesTask;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class VehicleFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    private VehicleRecyclerViewAdapter mVehicleRecyclerViewAdapter;

    LinearLayout mProgressBarLinearLayout;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public VehicleFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static VehicleFragment newInstance(int columnCount) {
        VehicleFragment fragment = new VehicleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehicle_list, container, false);

        // Set the adapter
        if (view instanceof CoordinatorLayout) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.vehicleListRecyclerView);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mVehicleRecyclerViewAdapter = new VehicleRecyclerViewAdapter(new ArrayList<Vehicle>(), mListener, getContext());
            recyclerView.setAdapter(mVehicleRecyclerViewAdapter);
        }

        mProgressBarLinearLayout = (LinearLayout) view.findViewById(R.id.bar);
        mProgressBarLinearLayout.setVisibility(View.GONE);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCreateVehicleActivity();
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        //do some shit
        MainMenuActivity activity = (MainMenuActivity) getActivity();
        ActivityGetVehiclesTask activityGetVehiclesTask = new ActivityGetVehiclesTask(
                getContext(),mVehicleRecyclerViewAdapter,
                activity.getmETollSyncRESTClient(),activity.getmMsisdn(),activity.getmToken());
        activityGetVehiclesTask.execute();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Vehicle item);
    }

    public void startCreateVehicleActivity(){
        Intent intent = new Intent(getActivity(),CreateVehicleActivity.class);
        startActivityForResult(intent,MainMenuActivity.CREATE_VEHICLE_ACTIVITY);
    }

    public class ActivityGetVehiclesTask extends GetVehiclesTask {
        VehicleRecyclerViewAdapter mViewAdapter;
        Context mContext;

        public ActivityGetVehiclesTask(Context context,
                                       VehicleRecyclerViewAdapter vehicleRecyclerViewAdapter,
                                       ETollSyncRESTClient client,
                                       String msisdn,String token) {
            super(client,msisdn,token);
            mViewAdapter = vehicleRecyclerViewAdapter;
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            mProgressBarLinearLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mProgressBarLinearLayout.setVisibility(View.GONE);
            if (success) {
                mViewAdapter.setmValues(mResponse.getVehicles());
                mViewAdapter.notifyDataSetChanged();
            } else {
                String toastMessage;
                if (mException != null) {
                    toastMessage = mException.getMessage();
                } else {
                    toastMessage = mResponse.getMessage();
                }
                Toast.makeText(mContext, toastMessage, Toast.LENGTH_LONG).show();
            }
        }
    }
}
