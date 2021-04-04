package com.michael.myfitnessapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.michael.myfitnessapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServerErrorActivity extends AppCompatActivity implements View.OnClickListener{

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tryAgainButton) Button mTryAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_error);
        ButterKnife.bind(this);
        mTryAgainButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mTryAgainButton) {
            Intent backToSearchGymnasium = new Intent(ServerErrorActivity.this, GymnasiumListActivity.class);
            startActivity(backToSearchGymnasium);
        }

    }
}
