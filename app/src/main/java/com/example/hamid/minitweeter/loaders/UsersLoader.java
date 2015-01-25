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
public class UsersLoader extends BasicLoader {


    public UsersLoader(Context context){
        super(context, null);
    }

    @Override
    public List<User> loadInBackground() {
        try{
                List<User> users = new ApiClient().getUsers();
                return users;
        } catch (IOException e) {
            Log.e(UsersLoader.class.getName(), "Failed to download users", e);
            return null;
        }
    }

}
