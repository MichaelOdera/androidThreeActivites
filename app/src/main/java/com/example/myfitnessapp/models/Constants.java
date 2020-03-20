package com.example.myfitnessapp.models;

import com.example.myfitnessapp.BuildConfig;

import org.parceler.Parcel;

@Parcel

public class Constants {
        public static final String YELP_BASE_URL = "https://api.yelp.com/v3/";
        public static final String YELP_API_KEY = BuildConfig.YELP_API_KEY;
        public static final String GOOGLE_API_KEY = BuildConfig.GOOGLE_API_KEY;

}
