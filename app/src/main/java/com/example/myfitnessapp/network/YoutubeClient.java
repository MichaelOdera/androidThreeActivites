package com.example.myfitnessapp.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.myfitnessapp.BuildConfig.GOOGLE_API_KEY;
import static com.example.myfitnessapp.models.Constants.YOUTUBE_API_URL;

public class YoutubeClient {
    private static Retrofit retrofit = null;

    public static YoutubeApi getClient(){
        if (retrofit == null){
//            OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                    .addInterceptor(new Interceptor() {
//                        @Override
//                        public Response intercept(Chain chain) throws IOException {
//                            Request newRequest = chain.request().newBuilder()
//                                    .addHeader("Authorization", GOOGLE_API_KEY)
//                                    .build();
//                            return chain.proceed(newRequest);
//                        }
//                    })
//                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(YOUTUBE_API_URL)
                    //.client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(YoutubeApi.class);
    }
}
