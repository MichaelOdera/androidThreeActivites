package com.michael.myfitnessapp.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.michael.myfitnessapp.R;
import com.michael.myfitnessapp.adapters.YoutubeRecyclerAdapter;
import com.michael.myfitnessapp.googlemodel.GoogleResponse;
import com.michael.myfitnessapp.googlemodel.Item;
import com.michael.myfitnessapp.network.YoutubeApi;
import com.michael.myfitnessapp.network.YoutubeClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.michael.myfitnessapp.BuildConfig.GOOGLE_API_KEY;

@SuppressLint("NonConstantResourceId")
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
                    mYoutubeRecyclerView.addItemDecoration(new DividerItemDecoration(YoutubeTutorialsActivity.this, DividerItemDecoration.VERTICAL));
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


