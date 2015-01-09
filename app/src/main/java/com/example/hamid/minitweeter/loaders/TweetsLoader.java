package com.example.hamid.minitweeter.loaders;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.hamid.minitweeter.ApiClient;
import com.example.hamid.minitweeter.model.Tweet;
import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hamid on 26/12/2014.
 */
public class TweetsLoader extends AsyncTaskLoader<List<Tweet>> {

    private String handle;
    private List<Tweet> result;

    public TweetsLoader(Context context, String handle) {
        super(context);
        this.handle = handle;
    }

    @Override
    public List<Tweet> loadInBackground() {
        try{
            return new ApiClient().getUserTweets(handle);
        } catch (Exception e) {
            Log.e(TweetsLoader.class.getName(), "Failed to download tweets", e);
            return null;
        }
    }

    /**
     * Lorsqu'on éfface la classe (après rotation de l'écran ou
     * après kill du process parce que ya pas assez de mémoire,
     * cette méthode est
     * appelée
     */
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(result != null){
            deliverResult(result);
        }
        if(takeContentChanged() || result == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    /**
     * Appelée sur le thread principal après loadInBackground au moment où le loader s'aprète à notifier les callbacks
     * @param data
     */
    @Override
    public void deliverResult(List<Tweet> data) {
        Log.i(TweetsLoader.class.getName(), "Returned data : " + data);
        result = data;
        super.deliverResult(data);
    }
}
