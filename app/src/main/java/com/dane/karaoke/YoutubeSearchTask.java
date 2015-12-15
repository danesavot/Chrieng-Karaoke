package com.dane.karaoke;

import android.os.AsyncTask;

import com.google.api.services.youtube.model.SearchListResponse;

/**
 * Created by 984391 on 12/15/2015.
 */
public class YoutubeSearchTask extends AsyncTask<String,Void,SearchListResponse> {
    @Override
    protected SearchListResponse doInBackground(String... params) {
        return null;
    }

    @Override
    protected void onPostExecute(SearchListResponse searchListResponse) {



    }
}
