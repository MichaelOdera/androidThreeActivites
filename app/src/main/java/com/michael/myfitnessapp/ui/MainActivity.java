package com.michael.myfitnessapp.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jsramraj.flags.Flags;
import com.michael.myfitnessapp.R;
import com.michael.myfitnessapp.adapters.FirebaseGymnasiumsViewHolder;
import com.michael.myfitnessapp.models.Business;
import com.michael.myfitnessapp.models.Constants;
import com.michael.myfitnessapp.swipedelete.SwipeToDeleteCallback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
@SuppressLint("NonConstantResourceId")
public class MainActivity extends AppCompatActivity {
    private DatabaseReference mGymnasiumsReference;
    private FirebaseRecyclerAdapter<Business, FirebaseGymnasiumsViewHolder> mFirebaseAdapter;


    @BindView(R.id.mainRecyclerView) RecyclerView mRecyclerView;



    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        FirebaseUser dataBaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert dataBaseUser != null;
        String uid = dataBaseUser.getUid();

        mGymnasiumsReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_GYMNASIUMS).child(uid);
        setUpFirebaseAdapter();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome, " + user.getDisplayName() + "!");
            } else {

            }
            //display welcome message
        };

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
        mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mFirebaseAdapter);
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(mFirebaseAdapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }


    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_gyms:
                            startActivity(new Intent(MainActivity.this, SearchGymsActivity.class));

                            return true;
                        case R.id.navigation_activities:
                            FragmentManager fm = getSupportFragmentManager();
                            RegistrationDialogFragment registerDialogFragment = new RegistrationDialogFragment ();
                            registerDialogFragment.show(fm, "registration");

                            return true;

                        case R.id.navigation_tutorials:
                            startActivity(new Intent(MainActivity.this, YoutubeTutorialsActivity.class));
                            return true;
                    }
                    return false;
                }
            };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, FitnessLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        mFirebaseAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

        if(mFirebaseAdapter != null) {
            mFirebaseAdapter.stopListening();
        }
    }

}
