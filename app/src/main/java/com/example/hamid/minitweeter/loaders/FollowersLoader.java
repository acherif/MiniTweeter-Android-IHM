package com.example.hamid.minitweeter.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.hamid.minitweeter.ApiClient;
import com.example.hamid.minitweeter.model.User;

import java.io.IOException;
import java.util.List;

/**
 * Created by hamid on 03/01/2015.
 */
public class FollowersLoader extends BasicLoader{


    public FollowersLoader(Context context, String userHandle){
        super(context, userHandle);
    }

    @Override
    public List<User> loadInBackground() {
        try{
            List<User> users = new ApiClient().getFollowers(userHandle);
            //Nettoie la liste des user nulles
            for (User user : users) {
                if(user == null){
                    users.remove(users.lastIndexOf(user));
                }
            }
            return users;
        } catch (IOException e){
            Log.e(FollowersLoader.class.getName(), "Failed to download Followers", e);
            return null;
        }
    }

}
