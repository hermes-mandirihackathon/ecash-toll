package com.mandiriecash.ecashtoll;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mandiriecash.ecashtoll.services.ETollHttpException;
import com.mandiriecash.ecashtoll.services.ETollSyncRESTClient;
import com.mandiriecash.ecashtoll.services.ETollSyncRESTClientImpl;
import com.mandiriecash.ecashtoll.services.exceptions.ETollIOException;
import com.mandiriecash.ecashtoll.services.models.History;
import com.mandiriecash.ecashtoll.services.models.Plan;
import com.mandiriecash.ecashtoll.services.requests.GetHistoryRequest;
import com.mandiriecash.ecashtoll.services.requests.GetPlansRequest;
import com.mandiriecash.ecashtoll.services.responses.GetHistoryResponse;
import com.mandiriecash.ecashtoll.services.responses.GetPlansResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class HistoryFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private HistoryRecyclerViewAdapter mHistoryRecyclerViewAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HistoryFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HistoryFragment newInstance(int columnCount) {
        HistoryFragment fragment = new HistoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_history_list, container, false);

        // Set the adapter
        if (view instanceof CoordinatorLayout) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mHistoryRecyclerViewAdapter = new HistoryRecyclerViewAdapter(new ArrayList<History>(),mListener);
            recyclerView.setAdapter(mHistoryRecyclerViewAdapter);
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
    public void onResume() {
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
        void onListFragmentInteraction(History item);
    }

    void fetchData(){
        String msisdn = ((MainMenuActivity) getActivity()).getmMsisdn();
        GetHistoryTask task = new GetHistoryTask(getActivity(),new ETollSyncRESTClientImpl(),msisdn);
        task.execute();

//        List<History> histories = new ArrayList<>();
//        History history = new History();
//        history.setDest_id(1);
//        history.setSource_id(1);
//        history.setDest_name("woi");
//        history.setSource_name("woi");
//        history.setPrice(12345);
//        for(int i = 0; i < 5; i++){
//            histories.add(history);
//        }
//        mHistoryRecyclerViewAdapter.setmValues(histories);
//        mHistoryRecyclerViewAdapter.notifyDataSetChanged();
    }

    class GetHistoryTask extends AsyncTask<Void,Void,Boolean>
    {
        Context mContext;
        ETollSyncRESTClient mClient;
        Exception mException;
        GetHistoryResponse mResponse;
        GetHistoryRequest mRequest;

        public GetHistoryTask(Context context,ETollSyncRESTClient client,String msisdn){
            this.mContext = context;
            this.mClient = client;
            mRequest = (new GetHistoryRequest.Builder()).msisdn(msisdn).build();
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean success = false;
            try {
                mResponse = mClient.getHistory(mRequest);
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
                mHistoryRecyclerViewAdapter.setmValues(mResponse.getHistories());
                mHistoryRecyclerViewAdapter.notifyDataSetChanged();
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
