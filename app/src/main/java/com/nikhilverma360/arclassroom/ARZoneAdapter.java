package com.nikhilverma360.arclassroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ARZoneAdapter extends RecyclerView.Adapter<ARZoneAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<ARZoneModel> mArZoneModelArrayList;
    public ARZoneAdapter(Context context, ArrayList<ARZoneModel> arZoneModelArrayList) {
        mContext = context;
        mArZoneModelArrayList = arZoneModelArrayList;
    }
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.ar_model_cardview, parent, false);
        return new ExampleViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        ARZoneModel currentItem = mArZoneModelArrayList.get(position);
        String title = currentItem.getTitle();
        String description = currentItem.getDescription();
        String storageId = currentItem.getShortURL();
        holder.mTextViewCreator.setText(title);
        holder.mTextViewLikes.setText(description);
        //Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
    }
    @Override
    public int getItemCount() {
        return mArZoneModelArrayList.size();
    }
    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        // public ImageView mImageView;
        public TextView mTextViewCreator;
        public TextView mTextViewLikes;
        public ExampleViewHolder(View itemView) {
            super(itemView);
            // mImageView = itemView.findViewById(R.id.image_view);
            mTextViewCreator = itemView.findViewById(R.id.text_view_creator);
            mTextViewLikes = itemView.findViewById(R.id.text_view_downloads);
        }
    }
}