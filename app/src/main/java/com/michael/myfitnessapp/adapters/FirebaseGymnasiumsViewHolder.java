package com.michael.myfitnessapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.michael.myfitnessapp.R;
import com.michael.myfitnessapp.models.Business;
import com.michael.myfitnessapp.models.Constants;
import com.michael.myfitnessapp.ui.GymnasiumDetailActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseGymnasiumsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private Context mContext;
    private View mView;
    public FirebaseGymnasiumsViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        mContext= itemView.getContext();
        itemView.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    public void bindGymnasium(Business gymnasium){
        ImageView restaurantImageView = mView.findViewById(R.id.gymnasiumImageView);
        TextView nameTextView = mView.findViewById(R.id.gymnasiumNameTextView);
        TextView categoryTextView = mView.findViewById(R.id.categoryTextView);
        TextView ratingTextView = mView.findViewById(R.id.ratingTextView);
        nameTextView.setText(gymnasium.getName());
        nameTextView.setVisibility(View.VISIBLE);
        categoryTextView.setText(gymnasium.getCategories().get(0).getTitle());
        categoryTextView.setVisibility(View.VISIBLE);
        ratingTextView.setText("Rating: " + gymnasium.getRating() + "/5");
        ratingTextView.setVisibility(View.VISIBLE);
        Picasso.get().load(gymnasium.getImageUrl()).into(restaurantImageView);
        restaurantImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        final ArrayList<Business> gymnasiums = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String uid = user.getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_GYMNASIUMS).child(uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    gymnasiums.add(snapshot.getValue(Business.class));
                }

                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, GymnasiumDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("gyms", Parcels.wrap(gymnasiums));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

