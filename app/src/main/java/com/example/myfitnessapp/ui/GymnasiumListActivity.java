package com.example.myfitnessapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myfitnessapp.adapters.GymListAdapter;
import com.example.myfitnessapp.R;
import com.example.myfitnessapp.models.Business;
import com.example.myfitnessapp.network.YelpBusinessesSearchResponse;
import com.example.myfitnessapp.network.YelpApi;
import com.example.myfitnessapp.network.YelpClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GymnasiumListActivity extends AppCompatActivity {

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
