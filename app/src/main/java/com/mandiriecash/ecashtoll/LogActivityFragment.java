package com.mandiriecash.ecashtoll;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mandiriecash.ecashtoll.services.ETollSyncRESTClient;
import com.mandiriecash.ecashtoll.services.ETollSyncRESTClientImpl;
import com.mandiriecash.ecashtoll.services.async_tasks.GetActivitiesTask;
import com.mandiriecash.ecashtoll.services.exceptions.ETollIOException;
import com.mandiriecash.ecashtoll.services.models.LogActivity;
import com.mandiriecash.ecashtoll.services.requests.GetActivitiesRequest;
import com.mandiriecash.ecashtoll.services.responses.GetActivitiesResponse;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class LogActivityFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    private LogActivityViewAdapter mLogActivityViewAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LogActivityFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static LogActivityFragment newInstance(int columnCount) {
        LogActivityFragment fragment = new LogActivityFragment();
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
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mLogActivityViewAdapter = new LogActivityViewAdapter(new ArrayList<LogActivity>(), mListener);
            recyclerView.setAdapter(mLogActivityViewAdapter);
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
        MainMenuActivity activity = (MainMenuActivity) getActivity();
        LogActGetActivitiesTask logActGetActivitiesTask = new LogActGetActivitiesTask(
                getContext(),mLogActivityViewAdapter,
                activity.getmETollSyncRESTClient(),activity.getmMsisdn(),activity.getmToken());
        logActGetActivitiesTask.execute();
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
        void onListFragmentInteraction(LogActivity item);
    }

    public class LogActGetActivitiesTask extends GetActivitiesTask {
        LogActivityViewAdapter mViewAdapter;
        Context mContext;

        public LogActGetActivitiesTask(Context context,
                                       LogActivityViewAdapter logActivityViewAdapter,
                                       ETollSyncRESTClient client,
                                       String msisdn,String token) {
            super(client,msisdn,token);
            mViewAdapter = logActivityViewAdapter;
            mContext = context;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                mViewAdapter.setmValues(mResponse.getActivities());
                mViewAdapter.notifyDataSetChanged();
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
}
