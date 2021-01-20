package com.michael.myfitnessapp.swipedelete;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.michael.myfitnessapp.adapters.FirebaseGymnasiumsViewHolder;
import com.michael.myfitnessapp.models.Business;
import com.michael.myfitnessapp.ui.MainActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

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
