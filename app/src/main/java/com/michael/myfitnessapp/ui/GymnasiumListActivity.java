package com.michael.myfitnessapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
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

import com.michael.myfitnessapp.adapters.GymListAdapter;
import com.michael.myfitnessapp.R;
import com.michael.myfitnessapp.models.Business;
import com.michael.myfitnessapp.models.Constants;
import com.michael.myfitnessapp.network.YelpBusinessesSearchResponse;
import com.michael.myfitnessapp.network.YelpApi;
import com.michael.myfitnessapp.network.YelpClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("NonConstantResourceId")
public class GymnasiumListActivity extends AppCompatActivity {
//
//    public GymnasiumListActivity(){
//
//    }
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private DatabaseReference mSearchedLocationReference;
    private OnClick onClick;


    @BindView(R.id.mainRecyclerView) RecyclerView mRecyclerView;
    private GymListAdapter mGymListAdapter;
    public List<Business> gyms;

    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;

    public static final String TAG = GymnasiumListActivity.class.getSimpleName();

    //public GymnasiumListActivity(OnClick onClick) {
//        this.onClick = onClick;
//    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gymnasiums);
        ButterKnife.bind(this);
        onLoadingShowCorrespondingMessage();


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

    @SuppressLint("SetTextI18n")
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

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(GymnasiumListActivity.this);
        mEditor = mSharedPreferences.edit();

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchView, R.drawable.cursor); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (Exception e) {
        }
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                showProgressBar();
                addToSharedPreferences(query);
                getGymnasiums(query);
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

    private void getGymnasiums(String location) {
        YelpApi client = YelpClient.getClient();

        Call<YelpBusinessesSearchResponse> call = client.getGymnasiums(location, "gyms");
        Log.d(TAG, "After Location");

        call.enqueue(new Callback<YelpBusinessesSearchResponse>(){

            @Override
            public void onResponse(@NotNull Call<YelpBusinessesSearchResponse> call, @NotNull Response<YelpBusinessesSearchResponse> response) {
                Log.d(TAG, "In the Override");
                if(response.code() == 500 ){
                    Intent errorIntent = new Intent(GymnasiumListActivity.this, ServerErrorActivity.class);
                    startActivity(errorIntent);
                }
                else if (response.isSuccessful()) {
                    hideProgressBar();
                    hideErrorTextView();
                    Log.d(TAG, "Response Successful");
                    assert response.body() != null;
                    gyms = response.body().getBusinesses();
                    mGymListAdapter = new GymListAdapter(GymnasiumListActivity.this, gyms, onClick);
                    mRecyclerView.setAdapter(mGymListAdapter);
                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(GymnasiumListActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.addItemDecoration(new DividerItemDecoration(GymnasiumListActivity.this, DividerItemDecoration.VERTICAL));
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }else{
                  onLoadingShowCorrespondingMessage();
                }

            }

            @SuppressLint("SetTextI18n")
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
