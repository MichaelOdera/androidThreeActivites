package com.example.myfitnessapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myfitnessapp.adapters.GymListAdapter;
import com.example.myfitnessapp.R;
import com.example.myfitnessapp.models.Business;
import com.example.myfitnessapp.models.Constants;
import com.example.myfitnessapp.network.YelpBusinessesSearchResponse;
import com.example.myfitnessapp.network.YelpApi;
import com.example.myfitnessapp.network.YelpClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GymnasiumListActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private DatabaseReference mSearchedLocationReference;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private GymListAdapter mGymListAdapter;
    public List<Business> gyms;

    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    public static final String TAG = GymnasiumListActivity.class.getSimpleName();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gymnasiums);
        ButterKnife.bind(this);

        Intent gymLocationIntent = getIntent();
        String location = gymLocationIntent.getStringExtra("gymLocation");
        //mLocationTextView.setText("Gymnasiums near "+ location);

        mSearchedLocationReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_LOCATION);

        mSearchedLocationReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("In the Onchange----------- :", "Got location");
                for(DataSnapshot locationSnapshot : dataSnapshot.getChildren()){
                    AtomicReference<String> location = new AtomicReference<>(Objects.requireNonNull(locationSnapshot.getValue(String.class)));
                    Log.d("Locations updated", "location: " + location);
                    System.out.println("Location: "+location);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        YelpApi client = YelpClient.getClient();
        Call<YelpBusinessesSearchResponse> call = client.getGymnasiums(location, "gyms");
        Log.d(TAG, "Made a call");

        call.enqueue(new Callback<YelpBusinessesSearchResponse>() {
            @Override
            public void onResponse(Call<YelpBusinessesSearchResponse> call, Response<YelpBusinessesSearchResponse> response) {
                hideProgressBar();
                if (response.isSuccessful()) {
                    Log.d("Success", "In the success");
                    gyms = response.body().getBusinesses();

                    mGymListAdapter = new GymListAdapter(GymnasiumListActivity.this, gyms);
                    mRecyclerView.setAdapter(mGymListAdapter);
                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(GymnasiumListActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);

                    showGymnasiums();

                }
                else{
                    showUnsuccessfulMessage();
                }
            }

            @Override
            public void onFailure(Call<YelpBusinessesSearchResponse> call, Throwable t) {
                Log.d(TAG,"Not Successful");
                Log.e(TAG, "onFailure: ",t );
                hideProgressBar();
                showFailureMessage();

            }

            private void showFailureMessage() {
                mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
                mErrorTextView.setVisibility(View.VISIBLE);
            }

            private void showUnsuccessfulMessage() {
                mErrorTextView.setText("Something went wrong. Please try again later");
                mErrorTextView.setVisibility(View.VISIBLE);
            }

            private void showGymnasiums() {
                mRecyclerView.setVisibility(View.VISIBLE);
            }

            private void hideProgressBar() {
                mProgressBar.setVisibility(View.GONE);
            }

        });
    }
}
