package com.dane.karaoke;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.api.services.youtube.model.SearchResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by noname on 12/15/2015.
 */
public class YoutubeSearchAdapter extends RecyclerView.Adapter<YoutubeSearchAdapter.SearchViewHolder> {

    Context context;
    List<SearchResult> searchResults;

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public YoutubeSearchAdapter(Context context, List<SearchResult> searchResults) {

        this.context = context;
        this.searchResults = searchResults;

    }

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout cardPlaceholder;
        ImageView imageView;
        TextView durationTextView;
        TextView titleTextView;
        TextView nameTextView;

        public SearchViewHolder(View itemView) {
            super(itemView);

            cardPlaceholder = (LinearLayout) itemView.findViewById(R.id.cardPlaceholder);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            durationTextView = (TextView) itemView.findViewById(R.id.durationTextView);
            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);

            cardPlaceholder.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener!= null) {
                onItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }

    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }


    @Override
    public YoutubeSearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.video_item,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(YoutubeSearchAdapter.SearchViewHolder holder, int position) {

        SearchResult searchResult = searchResults.get(position);

        Picasso.with(context)
                .load(searchResult.getSnippet().getThumbnails().getHigh().getUrl())
                .placeholder(R.drawable.video_placeholder)
                .into(holder.imageView);

        holder.titleTextView.setText(searchResult.getSnippet().getTitle());
        holder.nameTextView.setText("By: " + searchResult.getSnippet().getChannelTitle());

    }

    @Override
    public int getItemCount() {
        return searchResults.size();

    }
}
