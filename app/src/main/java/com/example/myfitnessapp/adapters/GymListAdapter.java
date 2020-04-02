package com.example.myfitnessapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfitnessapp.R;
import com.example.myfitnessapp.models.Business;
import com.example.myfitnessapp.ui.GymnasiumDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GymListAdapter extends RecyclerView.Adapter<GymListAdapter.GymnasiumViewHolder>{
    private List<Business> mGymnasiums;
    private Context mContext;

    public GymListAdapter(Context context, List<Business> gymnasiums){
        mContext = context;
        mGymnasiums = gymnasiums;
    }

    @NonNull
    @Override
    public GymListAdapter.GymnasiumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gymnasium_list_item, parent, false);
        GymnasiumViewHolder viewHolder = new GymnasiumViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GymListAdapter.GymnasiumViewHolder holder, int position) {
        holder.bindGymnasium(mGymnasiums.get(position));
    }

    @Override
    public int getItemCount() {
        return mGymnasiums.size();
    }


    public class GymnasiumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.gymnasiumsImageView) ImageView mGymnasiumImageView;
        @BindView(R.id.gymnasiumNameTextView) TextView mGymnasiumNameTextView;
        @BindView(R.id.categoryTextView) TextView mCategoryTextView;
        @BindView(R.id.ratingTextView) TextView mRatingTextView;
        //@BindView(R.id.gymnasiumPrice) TextView mGymnasiumPriceTextView;


        public GymnasiumViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @SuppressLint("SetTextI18n")
        public void bindGymnasium(Business gymnasium){
            Picasso.get().load(gymnasium.getImageUrl()).into(mGymnasiumImageView);
            //mGymnasiumPriceTextView.setText(gymnasium.getDistance().toString());
            mGymnasiumNameTextView.setText(gymnasium.getName());
            mCategoryTextView.setText(gymnasium.getCategories().get(0).getTitle());
            mRatingTextView.setText("Rating: "+gymnasium.getRating()+"/5");
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, GymnasiumDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("gyms", Parcels.wrap(mGymnasiums));
            mContext.startActivity(intent);
        }
    }

}
