package com.example.myfitnessapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myfitnessapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchGymsActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.gymSearchEditText)
    EditText mGymSearchEditText;
    @BindView(R.id.submitGymLocationButton)
    Button mGymSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_gyms);

        ButterKnife.bind(this);
        mGymSearchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v == mGymSearchButton ){
            String searchGymLocation = mGymSearchEditText.getText().toString();
            Intent gymIntent = new Intent(SearchGymsActivity.this, GymnasiumListActivity.class);
            gymIntent.putExtra("gymLocation", searchGymLocation);
            startActivity(gymIntent);
        }
    }
}
