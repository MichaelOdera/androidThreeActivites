package com.example.myfitnessapp;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesActivity extends AppCompatActivity {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String favoriteExercise = intent.getStringExtra("exercise");
        Log.d("This is a tag", favoriteExercise);
        mFavoriteExerciseTextView.setText(favoriteExercise + " has been added as your favorite Week's Exercise");

//        ArrayList<String> favoriteExerciseList =  new ArrayList<>();
//        favoriteExerciseList.add(favoriteExercise);
//
//        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, favoriteExerciseList){
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View view =super.getView(position, convertView, parent);
//
//                TextView textView=(TextView) view.findViewById(android.R.id.text1);
//
//                /*YOUR CHOICE OF COLOR*/
//                textView.setTextColor(Color.WHITE);
//                return view;
//            }
//        };
//        mFavoriteListView.setAdapter(adapter);

        mStartTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("got it", "Got Listened to");
                startingTime = SystemClock.uptimeMillis();
                TimeCustomHandler.postDelayed(updateTimerThread, 0);
            }
        });

        mPauseTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSwapBuffer += timeInMilliseconds;
                TimeCustomHandler.removeCallbacks(updateTimerThread);
            }
        });


        updateTimerThread = new Runnable() {

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


}
