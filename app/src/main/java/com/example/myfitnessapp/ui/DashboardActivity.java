package com.example.myfitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity {

    private ArrayAdapter mAdapter;

    @BindView(R.id.userNameTextView)
    TextView mUserNameTextView;
    @BindView(R.id.listView)
    ListView mListView;
    private EditText inputSearch;

    private String[] activities = new String[] {"Running", "Press Ups",
            "Pull Ups", "Push Ups", "Swimming", "Jogging",
            "Squats", "Banana Jumps", "Weight Lifting", "Hiking",
            "Frog jumps", "Star Jumps", "Skiing",
            "Commando crawls", "Back Stretching", "abs", "Nature walk"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String gottenUserName = intent.getStringExtra("userName");
        mUserNameTextView.setText("Welcome "+ gottenUserName+". \nClick on activity to choose it as your Week's Favorite");

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, activities){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

                //YOUR CHOICE OF COLOR
                textView.setTextColor(Color.rgb(255,79,0));
                textView.setBackgroundColor(Color.WHITE);

                return view;
            }
        };
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setBackgroundColor(getColor(R.color.lightThemeDarkPrimaryColourV3 )) ;
                String exercise = ((TextView)view).getText().toString();
                Intent intent = new Intent(DashboardActivity.this, FavoritesActivity.class);
                intent.putExtra("exercise",exercise);
                startActivity(intent);
            }
        });


        inputSearch = (EditText) findViewById(R.id.inputSearch);


        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                inputSearch.setTextColor(Color.rgb(255, 79, 0));
                adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                inputSearch.setTextColor(Color.rgb(220, 12,1));
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
    }


}
