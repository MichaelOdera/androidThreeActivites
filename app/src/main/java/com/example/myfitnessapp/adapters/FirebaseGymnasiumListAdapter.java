package com.example.myfitnessapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfitnessapp.R;
import com.example.myfitnessapp.models.Business;
import com.example.myfitnessapp.util.ItemTouchHelperAdapter;
import com.example.myfitnessapp.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

public class FirebaseGymnasiumListAdapter extends FirebaseRecyclerAdapter<Business, FirebaseGymnasiumsViewHolder> implements ItemTouchHelperAdapter {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;
    public FirebaseGymnasiumListAdapter(@NonNull FirebaseRecyclerOptions<Business> options,
                                        DatabaseReference ref,
                                        OnStartDragListener onStartDragListener,
                                        Context context) {
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onBindViewHolder(@NonNull FirebaseGymnasiumsViewHolder firebaseGymnasiumsViewHolder, int i, @NonNull Business gymnasium) {
        firebaseGymnasiumsViewHolder.bindGymnasium(gymnasium);
        firebaseGymnasiumsViewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getActionMasked() == MotionEvent.ACTION_DOWN){
                    mOnStartDragListener.onStartDrag(firebaseGymnasiumsViewHolder);
                }
                return false;
            }
        });
    }

    @NonNull
    @Override
    public FirebaseGymnasiumsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gymnasium_list_drag_item, parent, false);
        return new FirebaseGymnasiumsViewHolder(view);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }
}
