package com.example.myfitnessapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

public class MyGymsArrayAdapter extends ArrayAdapter {

    private Context mContext;
    private String[] mGyms;
    private String[] mCategories;



    public MyGymsArrayAdapter(Context mContext, int resource,  String[] gyms, String[] categories) {
        super(mContext, resource);
        this.mContext = mContext;
        this.mGyms = gyms;
        this.mCategories = categories;
    }

    @Override
    public int getCount() {
        return mGyms.length;
    }

    @Override
    public Object getItem(int position) {
        String gym = mGyms[position];
        String category = mCategories[position];
        return String.format("%s, Offers %s services", gym, category);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
