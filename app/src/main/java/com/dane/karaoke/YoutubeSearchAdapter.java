package com.dane.karaoke;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.api.services.youtube.model.SearchResult;

import java.util.List;

/**
 * Created by noname on 12/15/2015.
 */
public class YoutubeSearchAdapter extends RecyclerView.Adapter<YoutubeSearchAdapter.SearchViewHolder> {

    Context context;
    List<SearchResult> searchResults;

    public YoutubeSearchAdapter(Context context, List<SearchResult> searchResults) {

        this.context = context;
        this.searchResults = searchResults;

    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView durationTextView;
        TextView titleTextView;
        TextView nameTextView;


        public SearchViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            durationTextView = (TextView) itemView.findViewById(R.id.durationTextView);
            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);

        }
    }

    @Override
    public YoutubeSearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.video_item,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(YoutubeSearchAdapter.SearchViewHolder holder, int position) {

        SearchResult searchResult = searchResults.get(position);

        holder.titleTextView.setText(searchResult.getSnippet().getTitle());
        holder.nameTextView.setText(searchResult.getSnippet().getDescription());

    }

    @Override
    public int getItemCount() {
        return searchResults.size();

    }
}
