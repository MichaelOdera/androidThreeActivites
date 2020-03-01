package com.example.myfitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesActivity extends AppCompatActivity {
    @BindView(R.id.favoriteExerciseTextView)
    TextView mFavoriteExerciseTextView;
    @BindView(R.id.listView)
    ListView mFavoriteListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String favoriteExercise = intent.getStringExtra("exercise");
        mFavoriteExerciseTextView.setText(favoriteExercise + " has been added to your favorite exercises");
        ArrayList<String> favoriteExerciseList =  new ArrayList<>();
        favoriteExerciseList.add(favoriteExercise);

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, favoriteExerciseList);
        mFavoriteListView.setAdapter(adapter);

    }
}
