package com.example.hamid.minitweeter;




import android.net.Uri;
import android.util.Log;

import com.example.hamid.minitweeter.model.Tweet;
import com.example.hamid.minitweeter.model.User;
import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;



/**
 * Created by hamid on 28/12/2014.
 */
public class ApiClient {
    private static final String API_BASE = "http://hackndo.com:5667/mongo/";

    public String login(String handle, String password) throws IOException {
        String url = Uri.parse(API_BASE + "session/").buildUpon()
                .appendQueryParameter("handle", handle)
                .appendQueryParameter("password", password)
                .build().toString();
        Log.i(ApiClient.class.getName(), "Login: " + url);
        InputStream stream = new URL(url).openStream();
        return IOUtils.toString(stream);
    }

    public List<User> getUsers() throws IOException {
        InputStream stream = new URL(API_BASE + "users/").openStream();
        String response = IOUtils.toString(stream);
        return Arrays.asList(new Gson().fromJson(response, User[].class));
    }

    public List<Tweet> getUserTweets(String handle) throws IOException {
        InputStream stream = new URL(API_BASE + handle + "/tweets/").openStream();
        String response = IOUtils.toString(stream);
        return Arrays.asList(new Gson().fromJson(response, Tweet[].class));
    }

    public List<User> getFollowers(String handle) throws IOException {
        InputStream stream = new URL(API_BASE + handle + "/followers/").openStream();
        String response = IOUtils.toString(stream);
        Log.i(ApiClient.class.getName(), "response followers : " + response);
        return Arrays.asList(new Gson().fromJson(response, User[].class));
    }

    public List<User> getFollowings(String handle) throws IOException {
        InputStream stream = new URL(API_BASE + handle + "/followings/").openStream();
        String response = IOUtils.toString(stream);
        Log.i(ApiClient.class.getName(), "response followings : " + response);
        return Arrays.asList(new Gson().fromJson(response, User[].class));
    }

    public void follow(String handle, String token, String toFollowHandle) throws IOException {
        String url = Uri.parse(API_BASE + handle + "/followings/post/").buildUpon()
                .appendQueryParameter("handle", toFollowHandle)
                .build().toString();
        Log.i(ApiClient.class.getName(), "Add following: " + url);
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Authorization", "Bearer-" + token);
        connection.getInputStream();
    }

    public void unfollow(String handle, String token, String unfollowingHandle) throws IOException{
        String url = Uri.parse(API_BASE + handle + "/followings/delete/").buildUpon()
                .appendQueryParameter("handle", unfollowingHandle)
                .build().toString();
        Log.i(ApiClient.class.getName(), "Delete following: " + url);
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Authorization", "Bearer-" + token);
        connection.getInputStream();
    }
    public void postTweet(String handle, String token, String content) throws IOException {
        String url = Uri.parse(API_BASE + handle + "/tweets/post/").buildUpon()
                .appendQueryParameter("content", content)
                .build().toString();
        Log.i(ApiClient.class.getName(), "Add tweet: " + url);
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Authorization", "Bearer-" + token);
        connection.getInputStream();
    }

}