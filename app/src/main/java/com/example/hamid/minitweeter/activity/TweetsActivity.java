package com.example.hamid.minitweeter.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hamid.minitweeter.AccountManager;
import com.example.hamid.minitweeter.ApiClient;
import com.example.hamid.minitweeter.R;
import com.example.hamid.minitweeter.fragment.FollowingsFragment;
import com.example.hamid.minitweeter.fragment.TweetsFragment;
import com.example.hamid.minitweeter.loaders.FollowingsLoader;
import com.example.hamid.minitweeter.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by hamid on 26/12/2014.
 */
public class TweetsActivity extends ActionBarActivity{

    private static final String ARG_USER= "user";

    private String userHandle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweets_activity);

        userHandle = ((User) getIntent().getExtras().getParcelable(ARG_USER)).getHandle();

        if(savedInstanceState == null){
            Fragment tweetsFragment = new TweetsFragment();
            tweetsFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content, tweetsFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tweets, menu);
        MenuItem followItem = menu.findItem(R.id.action_follow_unfollow);
        if (!AccountManager.isConnected(TweetsActivity.this)) {
            followItem.setVisible(false);
        } else if(!AccountManager.getUserHandle(this).equals(userHandle)) {
            if (isFollowed()) {
                followItem.setTitle("unfollow");
            } else {
                followItem.setTitle("follow");
            }
        } else if(AccountManager.getUserHandle(this).equals(userHandle)){
            followItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_followers) {
            Intent intent = new Intent(this, FollowersActivity.class);
            intent.putExtras(getIntent().getExtras());
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_followings) {
            Intent intent = new Intent(this, FollowingsActivity.class);
            intent.putExtras(getIntent().getExtras());
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_follow_unfollow) {

            new AsyncTask<String, Void, Void>(){

                @Override
                protected Void doInBackground(String... params) {
                    String toFollowHandle = params[0];
                    String handle = AccountManager.getUserHandle(TweetsActivity.this);
                    String token = AccountManager.getUserToken(TweetsActivity.this);
                        if (!isFollowed()) {
                            try {
                                new ApiClient().follow(handle, token, toFollowHandle);
                            } catch (IOException e) {
                                Log.e(TweetsActivity.class.getName(), "Failed to follow " + e);
                            }
                        } else {
                            try {
                                new ApiClient().unfollow(handle, token, toFollowHandle);
                            } catch (IOException e) {
                                Log.e(TweetsActivity.class.getName(), "Failed to unfollow " + e);
                            }
                        }

                    return null;
                }


            }.execute(userHandle);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isFollowed() {
        Set<String> followings = AccountManager.getUserFollowings(this);
        for (String handle : followings) {
            if (handle.equals(userHandle)) {
                return true;
            }
        }
            return false;


    }
}
