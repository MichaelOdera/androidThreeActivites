package com.example.myfitnessapp.network;

import com.example.myfitnessapp.googlemodel.GoogleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeApi {
    @GET("youtube/v3/search")
    Call<GoogleResponse> getVideos(
            @Query("part") String part,
            @Query("q") String q,
            @Query("key") String key
    );
}
