package com.example.myfitnessapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.SearchView;
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
    @BindView(R.id.searchVideosHeader) TextView mSearchVideosHeader;
    @BindView(R.id.youtubeRecyclerView) RecyclerView mYoutubeRecyclerView;
    public List<Item> mItems;
    private YoutubeRecyclerAdapter youtubeRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_tutorials);
        ButterKnife.bind(this);



    }
    @SuppressLint("CommitPrefEdits")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getVideos(query);
                return false;
            }



            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;

    }

    private void getVideos(String query) {
        YoutubeApi client = YoutubeClient.getClient();
        Call<GoogleResponse> call = client.getVideos("snippet", query,"50", GOOGLE_API_KEY);

        call.enqueue(new Callback<GoogleResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<GoogleResponse> call, Response<GoogleResponse> response) {
                if (response.isSuccessful()) {
                    mSearchVideosHeader.setVisibility(View.GONE);
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


