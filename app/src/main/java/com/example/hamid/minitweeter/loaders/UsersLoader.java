package com.example.hamid.minitweeter.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.hamid.minitweeter.ApiClient;
import com.example.hamid.minitweeter.model.User;
import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hamid on 26/12/2014.
 */
public class UsersLoader extends AsyncTaskLoader<List<User>> {

    private List<User> result;
    private int type = 0;
    private String handle = null;

    public UsersLoader(Context context){
        super(context);
    }

//    public UsersLoader(Context context, int type, String handle) {
//        super(context);
//        this.type = type;
//        this.handle = handle;
//    }

    @Override
    public List<User> loadInBackground() {
        try{
//            if(type == 0) {
                List<User> users = new ApiClient().getUsers();
                return users;
                //return result;
//            }else if(type == 1){
//                return new ApiClient().getFollowers(handle);
//                //return result;
//            } else {
//                Log.i(UsersLoader.class.getName(), "Nothing to show");
//                return null;
//            }
        } catch (IOException e) {
            Log.e(UsersLoader.class.getName(), "Failed to download users", e);
            return null;
        }
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
