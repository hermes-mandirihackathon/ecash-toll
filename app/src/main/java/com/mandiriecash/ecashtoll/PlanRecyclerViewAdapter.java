package com.mandiriecash.ecashtoll;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mandiriecash.ecashtoll.PlanFragment.OnListFragmentInteractionListener;
import com.mandiriecash.ecashtoll.services.models.Plan;

import java.util.List;
public class PlanRecyclerViewAdapter extends RecyclerView.Adapter<PlanRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<Plan> mValues;
    private final OnListFragmentInteractionListener mListener;

    public PlanRecyclerViewAdapter(Context context,List<Plan> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_plan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mPriceView.setText(String.valueOf(mValues.get(position).getPrice()));
        holder.mDestView.setText(mValues.get(position).getDest_name());
        holder.mSourceView.setText(mValues.get(position).getSource_name());

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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final View mView;
        public final TextView mSourceView;
        public final TextView mDestView;
        public final TextView mPriceView;
        public final ImageButton mImageButton;
        public Plan mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mSourceView = (TextView) view.findViewById(R.id.source);
            mDestView = (TextView) view.findViewById(R.id.dest);
            mPriceView = (TextView) view.findViewById(R.id.price);
            mImageButton = (ImageButton) view.findViewById(R.id.imageButton);
            mImageButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            startBarcodeActivity(String.valueOf(mItem.getId()));
        }
    }

    public List<Plan> getmValues() {
        return mValues;
    }

    public void setmValues(List<Plan> mValues) {
        this.mValues = mValues;
    }

    public void startBarcodeActivity(String code){
        Intent intent = new Intent(mContext,BarcodeActivity.class);
        intent.putExtra("code",code);
        mContext.startActivity(intent);
    }
}
