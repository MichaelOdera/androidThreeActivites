package com.example.myfitnessapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import static com.example.myfitnessapp.BuildConfig.GOOGLE_API_KEY;

import com.example.myfitnessapp.R;
import com.example.myfitnessapp.adapters.YoutubeRecyclerAdapter;
import com.example.myfitnessapp.googlemodel.GoogleResponse;
import com.example.myfitnessapp.googlemodel.Item;
import com.example.myfitnessapp.googlemodel.Snippet;
import com.example.myfitnessapp.network.YoutubeApi;
import com.example.myfitnessapp.network.YoutubeClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YoutubeTutorialsActivity extends AppCompatActivity {
    @BindView(R.id.youtubeRecyclerView) RecyclerView mYoutubeRecyclerView;
    public List<Item> mItems;
    private YoutubeRecyclerAdapter youtubeRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_tutorials);
        ButterKnife.bind(this);

        YoutubeApi client = YoutubeClient.getClient();
        Call<GoogleResponse> call = client.getVideos("snippet", "football", GOOGLE_API_KEY);

        call.enqueue(new Callback<GoogleResponse>() {
            @Override
            public void onResponse(Call<GoogleResponse> call, Response<GoogleResponse> response) {
                if (response.isSuccessful()){
                    mItems = response.body().getItems();
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(YoutubeTutorialsActivity.this);
                    youtubeRecyclerAdapter = new YoutubeRecyclerAdapter(YoutubeTutorialsActivity.this, mItems);
                    mYoutubeRecyclerView.setLayoutManager(layoutManager);
                    mYoutubeRecyclerView.setAdapter(youtubeRecyclerAdapter);
                    mYoutubeRecyclerView.setHasFixedSize(true);
                    Log.d("Successful------", "SUCCESS________________");

                }

            }

            @Override
            public void onFailure(Call<GoogleResponse> call, Throwable t) {
                Log.d("FAILED", "failed to load__________success_on_Youtube");

            }
        });

    }
}
