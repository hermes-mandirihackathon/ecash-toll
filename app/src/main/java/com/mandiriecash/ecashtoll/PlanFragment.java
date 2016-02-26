package com.mandiriecash.ecashtoll;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mandiriecash.ecashtoll.services.ETollHttpException;
import com.mandiriecash.ecashtoll.services.ETollSyncRESTClient;
import com.mandiriecash.ecashtoll.services.ETollSyncRESTClientImpl;
import com.mandiriecash.ecashtoll.services.exceptions.ETollIOException;
import com.mandiriecash.ecashtoll.services.models.Plan;
import com.mandiriecash.ecashtoll.services.requests.GetPlansRequest;
import com.mandiriecash.ecashtoll.services.responses.GetPlansResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PlanFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private PlanRecyclerViewAdapter mPlanRecyclerViewAdapter;
    private LinearLayout mProgressBarLayout;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PlanFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PlanFragment newInstance(int columnCount) {
        PlanFragment fragment = new PlanFragment();
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
        View view = inflater.inflate(R.layout.fragment_plan_list, container, false);

        // Set the adapter
        if (view instanceof CoordinatorLayout) {
            final Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
            mProgressBarLayout = (LinearLayout) view.findViewById(R.id.bar);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mPlanRecyclerViewAdapter = new PlanRecyclerViewAdapter(new ArrayList<Plan>(), mListener);
            recyclerView.setAdapter(mPlanRecyclerViewAdapter);

            //set button listener
            FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,CreatePlanActivity.class);
                    startActivity(intent);
                }
            });
        }
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
    public void onResume(){
        super.onResume();
        fetchData();
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
        void onListFragmentInteraction(Plan item);
    }

    void fetchData(){
        String msisdn = ((MainMenuActivity) getActivity()).getmMsisdn();
        GetPlansTask task = new GetPlansTask(getActivity(),new ETollSyncRESTClientImpl(),msisdn);
        task.execute();
//        List<Plan> plans = new ArrayList<>();
//        Plan plan = new Plan();
//        plan.setDest_id(1);
//        plan.setSource_id(1);
//        plan.setDest_name("woi");
//        plan.setSource_name("woi");
//        plan.setPrice(12345);
//        for(int i = 0; i < 5; i++){
//            plans.add(plan);
//        }
//        mPlanRecyclerViewAdapter.setmValues(plans);
//        mPlanRecyclerViewAdapter.notifyDataSetChanged();
    }

    class GetPlansTask extends AsyncTask<Void,Void,Boolean>
    {
        Context mContext;
        ETollSyncRESTClient mClient;
        Exception mException;
        GetPlansResponse mResponse;
        GetPlansRequest mRequest;

        public GetPlansTask(Context context,ETollSyncRESTClient client,String msisdn){
            this.mContext = context;
            this.mClient = client;
            mRequest = (new GetPlansRequest.Builder()).msisdn(msisdn).build();
        }

        @Override
        protected void onPreExecute() {
            mProgressBarLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean success = false;
            try {
                mResponse = mClient.getPlans(mRequest);
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
            mProgressBarLayout.setVisibility(View.GONE);
            if (success){
                mPlanRecyclerViewAdapter.setmValues(mResponse.getPlans());
                mPlanRecyclerViewAdapter.notifyDataSetChanged();
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
                    Toast.makeText(mContext, mResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }

    }
}
