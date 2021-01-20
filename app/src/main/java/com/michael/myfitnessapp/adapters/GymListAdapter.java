package com.michael.myfitnessapp.adapters;

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

import com.michael.myfitnessapp.R;
import com.michael.myfitnessapp.models.Business;
import com.michael.myfitnessapp.ui.GymnasiumDetailActivity;
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

    @SuppressLint("NonConstantResourceId")
    public class GymnasiumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.gymnasiumImageView) ImageView mGymnasiumImageView;
        @BindView(R.id.gymnasiumNameTextView) TextView mGymnasiumNameTextView;
        @BindView(R.id.categoryTextView) TextView mCategoryTextView;
        @BindView(R.id.ratingTextView) TextView mRatingTextView;


        public GymnasiumViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @SuppressLint("SetTextI18n")
        public void bindGymnasium(Business gymnasium){
            if(gymnasium.getImageUrl() != "" && !gymnasium.getImageUrl().isEmpty()){
                Picasso.get().load(gymnasium.getImageUrl()).into(mGymnasiumImageView);
                mGymnasiumNameTextView.setText(gymnasium.getName());
                mCategoryTextView.setText(gymnasium.getCategories().get(0).getTitle());
                mRatingTextView.setText("Rating: "+gymnasium.getRating()+"/10");
            }else{
                mGymnasiumImageView.setImageResource(R.drawable.bulls);
            }




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
