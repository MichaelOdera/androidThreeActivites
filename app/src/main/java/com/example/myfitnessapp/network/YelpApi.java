package com.example.myfitnessapp.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YelpApi {
    @GET("businesses/search")
    Call<YelpBusinessesSearchResponse> getGymnasiums(
            @Query("location") String location,
            @Query("term") String term
    );
}
