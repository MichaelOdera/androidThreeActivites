package com.example.myfitnessapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfitnessapp.R;
import com.example.myfitnessapp.googlemodel.Item;
import com.example.myfitnessapp.googlemodel.Snippet;
import com.example.myfitnessapp.ui.YoutubePlayerActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.CheckedOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class  YoutubeRecyclerAdapter extends RecyclerView.Adapter<YoutubeRecyclerAdapter.YoutubeViewHolder> {
    private Context mContext;
    private List<Item> mItems;
    String videoId;

    public YoutubeRecyclerAdapter(Context context, List<Item> items){
        mContext = context;
        mItems = items;
    }


    @NonNull
    @Override
    public YoutubeRecyclerAdapter.YoutubeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_video_item, parent, false);
        return new YoutubeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YoutubeRecyclerAdapter.YoutubeViewHolder holder, int position) {
        holder.bindSnippet(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
    @SuppressLint("NonConstantResourceId")
    public class YoutubeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        @BindView(R.id.videoTitleTextView) TextView mVideoTitleTextView;
        @BindView(R.id.descriptionTextView) TextView mDescriptionTextView;
        @BindView(R.id.youtubeThumbNail) ImageView mThumbNailImageView;
        @BindView(R.id.playVideoButton) ImageButton mPlayerVideoButton;
        public YoutubeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mPlayerVideoButton.setOnClickListener(this);
            mContext = itemView.getContext();

        }

        public void bindSnippet(Item snippet) {
            if(!snippet.getSnippet().getThumbnails().getDefault().getUrl().isEmpty()){
                Picasso.get().load(snippet.getSnippet().getThumbnails().getDefault().getUrl()).into(mThumbNailImageView);
                mVideoTitleTextView.setText(snippet.getSnippet().getTitle());
                mDescriptionTextView.setText(snippet.getSnippet().getChannelTitle());
            }
            else {
                mThumbNailImageView.setImageResource(R.drawable.fitness);
            }

        }

        @Override
        public void onClick(View v) {
                int position = getLayoutPosition();
                Intent intent = new Intent(mContext, YoutubePlayerActivity.class);
                String videoId = mItems.get(position).getId().getVideoId();
                intent.putExtra("position", position);
                intent.putExtra("videoId", videoId);
                mContext.startActivity(intent);
                Toast.makeText(mContext, "PLAYING VIDEO NOW", Toast.LENGTH_SHORT).show();
                Log.d("Clicked a VIDEO _______", "wants to load Youtube player");
        }
    }
}
