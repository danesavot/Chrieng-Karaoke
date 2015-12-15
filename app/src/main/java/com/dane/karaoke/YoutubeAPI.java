package com.dane.karaoke;

import android.util.Log;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;

import java.io.IOException;

/**
 * Created by 984391 on 12/14/2015.
 */
public final class YoutubeAPI {

    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    public static final JsonFactory JSON_FACTORY = new JacksonFactory();

    private static YouTube youtube;

    private static final long NUMBER_OF_VIDEOS_RETURNED = 25;

    public static final String YOUTUBE_API_KEY = "AIzaSyBYH_wGyjeUY1riBNLlAqt9b9HfSM53MeU";

    private YoutubeAPI() {

        youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
            public void initialize(HttpRequest request) throws IOException {
            }
        }).setApplicationName("Chrieng Karaoke").build();
    }

    public static SearchListResponse search(String queryTerm) {

        try {
            YouTube.Search.List search = youtube.search().list("id,snippet")
                    .setKey(YOUTUBE_API_KEY)
                    .setQ(queryTerm)
                    .setType("video")
                    .setFields("items(id(videoId),snippet(title,description,thumbnails/default/url))")
                    .setMaxResults(Long.valueOf(NUMBER_OF_VIDEOS_RETURNED));
/*                    .setOrder(SearchSetting.getInstance().getOrder())
                    .setSafeSearch(SearchSetting.getInstance().getSafeSearch())
                    .setVideoDefinition(SearchSetting.getInstance().getVideoDefinition())
                    .setVideoDuration(SearchSetting.getInstance().getVideoDuration())
                    .setVideoType(SearchSetting.getInstance().getVideoType());*/
            return search.execute();
        } catch (GoogleJsonResponseException e) {
            Log.d("com.dane.karaoke","There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
        } catch (IOException e) {
            Log.d("com.dane.karaoke","There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            Log.d("com.dane.karaoke",t.getMessage());
        }

        return null;
    }
}
