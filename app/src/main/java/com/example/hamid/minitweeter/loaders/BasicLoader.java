package com.example.hamid.minitweeter.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.hamid.minitweeter.model.User;

import java.util.List;

/**
 * Created by Hamid on 25/01/2015.
 *
 * AsyncTaskLoader for the list of Users. Extended by FollowersLoader, FollowingsLoader and UsersLoader
 */
public abstract class BasicLoader extends AsyncTaskLoader<List<User>> {

    protected List<User> result;
    protected String userHandle;

    public BasicLoader(Context context, String userHandle){
        super(context);
        this.userHandle = userHandle;

    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(result != null){
            deliverResult(result);
        }
        if(takeContentChanged() || result == null){
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    @Override
    public void deliverResult(List<User> data) {
        result = data;
        super.deliverResult(data);
    }



}
