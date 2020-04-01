package com.example.myfitnessapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfitnessapp.R;
import com.example.myfitnessapp.googlemodel.Item;
import com.example.myfitnessapp.googlemodel.Snippet;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.CheckedOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YoutubeRecyclerAdapter extends RecyclerView.Adapter<YoutubeRecyclerAdapter.YoutubeViewHolder> {
    private Context mContext;
    private List<Item> mItems;

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

    public class YoutubeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.videoTitleTextView)
        TextView mVideoTitleTextView;
        @BindView(R.id.descriptionTextView) TextView mDescriptionTextView;
        @BindView(R.id.youtubeThumbNail)
        ImageView mThumbNailImageView;
        public YoutubeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();

        }

        public void bindSnippet(Item snippet) {
            Picasso.get().load(snippet.getSnippet().getThumbnails().getDefault().getUrl()).into(mThumbNailImageView);
            mVideoTitleTextView.setText(snippet.getSnippet().getTitle());
            mDescriptionTextView.setText(snippet.getSnippet().getDescription());
        }
    }
}
