package com.dane.karaoke.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.dane.karaoke.R;
import com.dane.karaoke.YoutubeAPI;
import com.dane.karaoke.YoutubeSearchAdapter;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, YoutubeSearchAdapter.OnItemClickListener {

    private RecyclerView recycleView;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    List<SearchResult> searchResults = new ArrayList<>();
    YoutubeSearchAdapter searchAdapter;

    boolean duplicate;
    String query;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchAdapter = new YoutubeSearchAdapter(this, searchResults);
        searchAdapter.setOnItemClickListener(this);

        recycleView = (RecyclerView) findViewById(R.id.videoRecyclerView);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recycleView.setHasFixedSize(true);
        recycleView.setLayoutManager(staggeredGridLayoutManager);

        recycleView.setAdapter(searchAdapter);

        performSearchTask("Karaoke");
    }

    private void performSearchTask(String karaoke) {
        new YoutubeSearchTask().execute(karaoke);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.search_manu, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));

        searchView.setOnQueryTextListener(this);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        if (!duplicate) {
            this.query = query;
            duplicate = true;
            setTitle(query);
            hideSoftKeyboard(searchView);
            searchView.clearFocus();

            performSearchTask(query + " karaoke");

            return true;

        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        duplicate = false;
        return true;
    }

    @Override
    public void onItemClick(View view, int position) {

        Intent intent = new Intent(this, YoutubePlayerActivity.class);
        intent.putExtra("videoId", searchResults.get(position).getId().getVideoId());
        startActivity(intent);

    }

    public class YoutubeSearchTask extends AsyncTask<String, Void, SearchListResponse> {
        @Override
        protected SearchListResponse doInBackground(String... params) {
            return YoutubeAPI.search(params[0]);
        }

        @Override
        protected void onPostExecute(SearchListResponse searchListResponse) {
            if (searchListResponse != null) {
                searchResults.clear();
                searchResults.addAll(searchListResponse.getItems());
                searchAdapter.notifyDataSetChanged();
            }
        }
    }
}
