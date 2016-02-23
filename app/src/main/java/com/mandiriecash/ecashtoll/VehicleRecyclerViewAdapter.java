package com.mandiriecash.ecashtoll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mandiriecash.ecashtoll.VehicleFragment.OnListFragmentInteractionListener;
import com.mandiriecash.ecashtoll.services.models.Vehicle;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Vehicle} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class VehicleRecyclerViewAdapter extends RecyclerView.Adapter<VehicleRecyclerViewAdapter.ViewHolder> {

    private List<Vehicle> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;

    public VehicleRecyclerViewAdapter(List<Vehicle> items, OnListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        mContext = context;
    }

    public List<Vehicle> getmValues() {
        return mValues;
    }

    public void setmValues(List<Vehicle> mValues) {
        this.mValues = mValues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_vehicle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mPlateNoView.setText(mValues.get(position).getPlateNo());
        Picasso.with(mContext).load(mValues.get(position).getPhotoUrl()).into(holder.mVehicleImageView);

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
        public final TextView mNameView;
        public final TextView mPlateNoView;
        public final ImageView mVehicleImageView;

        public Vehicle mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.name);
            mPlateNoView = (TextView) view.findViewById(R.id.plateNo);
            mVehicleImageView = (ImageView) view.findViewById(R.id.vehicleImage);
        }
    }
}
