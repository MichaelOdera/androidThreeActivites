package com.example.myfitnessapp.swipedelete;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfitnessapp.R;
import com.example.myfitnessapp.adapters.FirebaseGymnasiumsViewHolder;
import com.example.myfitnessapp.models.Business;
import com.example.myfitnessapp.ui.MainActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import static android.widget.Toast.LENGTH_SHORT;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    /**
     * Creates a Callback for the given drag and swipe allowance. These values serve as
     * defaults
     * and if you want to customize behavior per ViewHolder, you can override
     * {@link #getSwipeDirs(RecyclerView, RecyclerView.ViewHolder)}
     * and / or {@link #getDragDirs(RecyclerView, RecyclerView.ViewHolder)}.
     *
     * @param dragDirs  Binary OR of direction flags in which the Views can be dragged. Must be
     *                  composed of , , , ,
     *                   and .
     * @param swipeDirs Binary OR of direction flags in which the Views can be swiped. Must be
     *                  composed of , , , ,
     *                   and .
     */

    private FirebaseRecyclerAdapter<Business, FirebaseGymnasiumsViewHolder> mFirebaseAdapter;
    private MainActivity mainActivity;


    public SwipeToDeleteCallback(FirebaseRecyclerAdapter<Business, FirebaseGymnasiumsViewHolder> adapter) {
        super(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mFirebaseAdapter = adapter;


    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        mFirebaseAdapter.getRef(position).removeValue();
    }


}
