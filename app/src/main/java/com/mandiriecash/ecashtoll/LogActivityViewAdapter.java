package com.mandiriecash.ecashtoll;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mandiriecash.ecashtoll.LogActivityFragment.OnListFragmentInteractionListener;
import com.mandiriecash.ecashtoll.services.models.LogActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link LogActivity} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class LogActivityViewAdapter extends RecyclerView.Adapter<LogActivityViewAdapter.ViewHolder> {

    private List<LogActivity> mValues;
    private final OnListFragmentInteractionListener mListener;

    public LogActivityViewAdapter(List<LogActivity> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public void setmValues(List<LogActivity> mValues) {
        this.mValues = mValues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mVehicleName.setText(mValues.get(position).getVehicle_name());
//        String sourceDest = mValues.get(position).getSource_toll_id() + " - " +mValues.get(position).getDest_toll_id();
        String sourceDest = mValues.get(position).getSource_toll_name() + " - " +mValues.get(position).getDest_toll_name();
        holder.mSourceDest.setText(sourceDest);
        holder.mPrice.setText(String.valueOf(mValues.get(position).getPrice()));
//        holder.mTime.setText(String.valueOf(mValues.get(position).getTimestamp()));
        Calendar calendar = Calendar.getInstance(); calendar.setTimeInMillis(mValues.get(position).getTimestamp());
        holder.mTime.setText(String.format("%d-%d-%d %d:%d",
                calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR),
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE)));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mSourceDest;
        public final TextView mVehicleName;
        public final TextView mTime;
        public final TextView mPrice;

        public LogActivity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mSourceDest = (TextView) view.findViewById(R.id.sourceDest);
            mVehicleName = (TextView) view.findViewById(R.id.vehicleName);
            mTime = (TextView) view.findViewById(R.id.time);
            mPrice = (TextView) view.findViewById(R.id.price);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mPrice.getText() + "'";
        }
    }
}
