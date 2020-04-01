package com.example.myfitnessapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.myfitnessapp.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.myfitnessapp.models.Constants.GOOGLE_API_KEY;

public class YoutubePlayerActivity extends YouTubeBaseActivity {
    private static final String TAG = YoutubePlayerActivity.class.getSimpleName();
    @BindView(R.id.youtubePlayerView)
    YouTubePlayerView mYoutubePlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        String videoId = intent.getStringExtra("videoId");
        initializeYoutubePlayer(videoId);

    }

    private void initializeYoutubePlayer(String videoId) {
        mYoutubePlayerView.initialize(GOOGLE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if(!b){
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    youTubePlayer.loadVideo(videoId);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("PLAYER_FAIL", "Failed to play");

            }
        });
    }
}
