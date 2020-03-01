package com.example.myfitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity {

    @BindView(R.id.userNameTextView)
    TextView mUserNameTextView;
    @BindView(R.id.listView)
    ListView mListView;
    private EditText inputSearch;

    private String[] activities = new String[] {"Running", "Press Ups",
            "Pull Ups", "Push Ups", "Swimming", "Jogging",
            "Squats", "Banana Jumps", "Weight Lifting", "Hiking",
            "Frog jumps", "Star Jumps", "Skiing",
            "Commando crawls", "Back Stretching"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String gottenUserName = intent.getStringExtra("userName");
        mUserNameTextView.setText("Welcome "+ gottenUserName);

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, activities);
        mListView.setAdapter(adapter);

        inputSearch = (EditText) findViewById(R.id.inputSearch);


        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
    }


}
