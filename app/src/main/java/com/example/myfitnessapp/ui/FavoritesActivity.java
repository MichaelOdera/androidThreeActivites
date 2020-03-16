package com.example.myfitnessapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myfitnessapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.favoriteExerciseTextView)
    TextView mFavoriteExerciseTextView;
//    @BindView(R.id.listView)
//    ListView mFavoriteListView;

    @BindView(R.id.startButton)
    Button mStartTimerButton;
    @BindView(R.id.pauseButton) Button mPauseTimerButton;
    @BindView(R.id.timerValue) TextView mTimerReadings;

    private long startingTime = 0L;
    private Runnable updateTimerThread;

    private Handler TimeCustomHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuffer = 0L;
    long updatedTime = 0L;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String favoriteExercise = intent.getStringExtra("exercise");
        Log.d("This is a tag", favoriteExercise);
        mFavoriteExerciseTextView.setText(favoriteExercise + " has been added as your favorite Week's Exercise");


        mStartTimerButton.setOnClickListener(this);
        mPauseTimerButton.setOnClickListener(this);


        updateTimerThread = new Runnable() {

            @SuppressLint("DefaultLocale")
            public void run() {

                timeInMilliseconds = SystemClock.uptimeMillis() - startingTime;

                updatedTime = timeSwapBuffer + timeInMilliseconds;

                int secs = (int) (updatedTime / 1000);
                int mins = secs / 60;
                secs = secs % 60;
                int milliseconds = (int) (updatedTime % 1000);
                mTimerReadings.setText("" + mins + ":"
                        + String.format("%02d", secs) + ":"
                        + String.format("%02d", milliseconds));
                TimeCustomHandler.postDelayed(this, 0);
            }

        };

    }

    @Override
    public void onClick(View v){
        if(v == mStartTimerButton){
            Log.d("got it", "Got Listened to");
            startingTime = SystemClock.uptimeMillis();
            TimeCustomHandler.postDelayed(updateTimerThread, 0);
        }

        if(v == mPauseTimerButton){
            timeSwapBuffer += timeInMilliseconds;
            TimeCustomHandler.removeCallbacks(updateTimerThread);
        }
    }



}
