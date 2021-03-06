package com.michael.myfitnessapp.models;

import com.michael.myfitnessapp.BuildConfig;

import org.parceler.Parcel;

@Parcel

public class Constants {
        public static final String YELP_BASE_URL = "https://api.yelp.com/v3/";
        public static final String YOUTUBE_API_URL= "https://www.googleapis.com/";
        public static final String YELP_API_KEY = BuildConfig.YELP_API_KEY;
        public static final String GOOGLE_API_KEY = BuildConfig.GOOGLE_API_KEY;
        public static final String FIREBASE_CHILD_SEARCHED_LOCATION = "searchedLocation";
        public static final String YELP_LOCATION_QUERY_PARAMETER = "location";
        public static final String PREFERENCES_LOCATION_KEY = "location";
        public static final String FIREBASE_CHILD_GYMNASIUMS = "gymnasiums";
}
