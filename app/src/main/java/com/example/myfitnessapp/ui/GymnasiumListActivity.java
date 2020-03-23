package com.example.myfitnessapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
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
        onLoadingShowCorrespondingMessage();

        //Intent gymLocationIntent = getIntent();
        //String location = gymLocationIntent.getStringExtra("gymLocation");
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

    }

    private void onLoadingShowCorrespondingMessage() {
        mProgressBar.setVisibility(View.GONE);
        mErrorTextView.setText("There are currently no Gymnasiums to show, Please Search location to show gymnasiums");
        mErrorTextView.setTextColor(getResources().getColor(R.color.colorButton));
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                showProgressBar();
                addToSharedPreferences(query);
                getRestaurants(query);
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });
        return true;
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void addToSharedPreferences(String location) {
        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
    }

    private void getRestaurants(String location) {
        YelpApi client = YelpClient.getClient();

        Call<YelpBusinessesSearchResponse> call = client.getGymnasiums(location, "gyms");
        Log.d(TAG, "After Location");

        call.enqueue(new Callback<YelpBusinessesSearchResponse>(){

            @Override
            public void onResponse(Call<YelpBusinessesSearchResponse> call, Response<YelpBusinessesSearchResponse> response) {
                Log.d(TAG, "In the Override");
                if (response.isSuccessful()) {
                    hideProgressBar();
                    hideErrorTextView();
                    Log.d(TAG, "Response Successful");
                    gyms = response.body().getBusinesses();
                    mGymListAdapter = new GymListAdapter(GymnasiumListActivity.this, gyms);
                    mRecyclerView.setAdapter(mGymListAdapter);
                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(GymnasiumListActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }else{
                  onLoadingShowCorrespondingMessage();
                }

            }

            @Override
            public void onFailure(Call<YelpBusinessesSearchResponse> call, Throwable t) {
                Log.d(TAG, "Request not successful");
                Log.e(TAG, "onFailure: ",t );
                mProgressBar.setVisibility(View.GONE);
                mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
                mErrorTextView.setVisibility(View.VISIBLE);

            }
        });



    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void hideErrorTextView() {
        mErrorTextView.setVisibility(View.GONE);
    }

}
