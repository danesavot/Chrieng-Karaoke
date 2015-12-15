package com.dane.karaoke;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by noname on 12/15/2015.
 */
public class YoutubeSearchAdapter extends RecyclerView.Adapter<YoutubeSearchAdapter.SearchViewHolder> {

    Context context;

    public YoutubeSearchAdapter(Context context) {
        this.context = context;
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
        return null;
    }

    @Override
    public void onBindViewHolder(YoutubeSearchAdapter.SearchViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
