package com.michael.myfitnessapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.michael.myfitnessapp.R;
import com.michael.myfitnessapp.adapters.FirebaseGymnasiumsViewHolder;
import com.michael.myfitnessapp.models.Business;
import com.michael.myfitnessapp.models.Constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
@SuppressLint("NonConstantResourceId")
public class SavedGymnasiumsListActivity extends AppCompatActivity {
    private DatabaseReference mGymnasiumsReference;
    private FirebaseRecyclerAdapter<Business, FirebaseGymnasiumsViewHolder> mFirebaseAdapter;


    @BindView(R.id.mainRecyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gymnasiums);
        ButterKnife.bind(this);

        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(SavedGymnasiumsListActivity.this, DividerItemDecoration.VERTICAL));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String uid = user.getUid();


        mGymnasiumsReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_GYMNASIUMS).child(uid);
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        FirebaseRecyclerOptions<Business> options =
                new FirebaseRecyclerOptions.Builder<Business>()
                        .setQuery(mGymnasiumsReference, Business.class)
                        .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Business, FirebaseGymnasiumsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseGymnasiumsViewHolder firebaseGymnasiumsViewHolder, int position, @NonNull Business gymnasium) {
                firebaseGymnasiumsViewHolder.bindGymnasium(gymnasium);
            }

            @NonNull
            @Override
            public FirebaseGymnasiumsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gymnasium_list_item, parent, false);
                return new FirebaseGymnasiumsViewHolder(view);
            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onStart(){
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(mFirebaseAdapter != null){
            mFirebaseAdapter.stopListening();
        }
    }
}
