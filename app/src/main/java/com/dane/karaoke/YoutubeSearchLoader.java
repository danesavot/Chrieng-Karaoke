package com.dane.karaoke;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.google.api.services.youtube.model.SearchResult;

import java.util.List;

/**
 * Created by Dane Savot on 1/24/2016.
 */
public class YoutubeSearchLoader extends AsyncTaskLoader<List<SearchResult>> {

    String keyword;
    List<SearchResult> searchResults = null;

    public YoutubeSearchLoader(Context context, String keyword) {
        super(context);
        this.keyword = keyword;
    }

    @Override
    public List<SearchResult> loadInBackground() {
        Log.i("YoutubeSearchLoader","loadInBackground " + keyword);
        searchResults = YoutubeAPI.search(keyword).getItems();
        return searchResults;
    }

    @Override
    protected void onStartLoading() {

        Log.i("YoutubeSearchLoader","onStartLoading " + keyword);
        if (searchResults!=null) {
            Log.i("YoutubeSearchLoader", "onStartLoading " + keyword + " " + searchResults.size());
            deliverResult(searchResults);
        }
        else
            forceLoad();

    }
    @Override
    public void deliverResult(List<SearchResult> data) {
        if (isReset()) {
            // The Loader has been reset; ignore the result and invalidate the
            // data.
            if (data != null) {
                releaseResources(data);
            }

            return;
        }

        // Hold a reference to the old data so it doesn't get garbage collected.
        // We must protect it until the new data has been delivered.
        List<SearchResult> oldData = searchResults;
        searchResults = data;

        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(data);
        }

        // Invalidate the old data as we don't need it any more.
        if (oldData != null && oldData != data) {
            releaseResources(oldData);
        }
    }

    @Override
    protected void onStopLoading() {
        Log.i("YoutubeSearchLoader","onStopLoading " + keyword );
        // The Loader is in a stopped state, so we should attempt to cancel the
        // current load (if there is one).
        cancelLoad();

        // Note that we leave the observer as is. Loaders in a stopped state
        // should still monitor the data source for changes so that the Loader
        // will know to force a new load if it is ever started again.
    }

    @Override
    protected void onReset() {
        Log.i("YoutubeSearchLoader","onReset " + keyword );
        // Ensure the loader has been stopped.
        onStopLoading();

        // At this point we can release the resources associated with 'mData'.
        if (searchResults != null) {
            releaseResources(searchResults);
            searchResults = null;
        }

/*		// The Loader is being reset, so we should stop monitoring for changes.
		if (mObserver != null) {
			// TODO: unregister the observer
			mObserver = null;
		}*/
    }

    @Override
    public void onCanceled(List<SearchResult> data) {
        Log.i("YoutubeSearchLoader","onCanceled " + keyword );
        // Attempt to cancel the current asynchronous load.
        super.onCanceled(data);

        // The load has been canceled, so we should release the resources
        // associated with 'data'.
        releaseResources(data);
    }

    private void releaseResources(List<SearchResult> data) {
        // For a simple List, there is nothing to do. For something like a Cursor, we
        // would close it in this method. All resources associated with the Loader
        // should be released here.
        data = null;
    }


}