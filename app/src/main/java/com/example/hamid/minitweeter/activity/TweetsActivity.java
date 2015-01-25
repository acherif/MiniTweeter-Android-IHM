package com.example.hamid.minitweeter.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hamid.minitweeter.AccountManager;
import com.example.hamid.minitweeter.ApiClient;
import com.example.hamid.minitweeter.R;
import com.example.hamid.minitweeter.fragments.TweetsFragment;
import com.example.hamid.minitweeter.model.User;

import java.io.IOException;
import java.util.Set;

/**
 * Created by hamid on 26/12/2014.
 *
 * Contains list of tweets.
 * Its menu contains button for followers, followings.
 * If a user is logged in and it's not its tweets, a follow/unfollow button is displayed
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
        MenuItem followItem = menu.findItem(R.id.action_follow);
        MenuItem unfollowItem = menu.findItem(R.id.action_unfollow);
        if (!AccountManager.isConnected(TweetsActivity.this)) {
            //If no user is logged in
            followItem.setVisible(false);
            unfollowItem.setVisible(false);
        } else if(!AccountManager.getUserHandle(this).equals(userHandle)) {
            //If a user is logged in and we're not on his tweets list
            if (isFollowed()) {
                //If user already follow, we show the unfollow button
                followItem.setVisible(false);
            } else {
                //If not followed, we show the follow button
                unfollowItem.setVisible(false);
            }
        } else if(AccountManager.getUserHandle(this).equals(userHandle)){
            //If a user is logged in and were on it's his tweets list, we don't show neither the follow button nor the unfollow one
            followItem.setVisible(false);
            unfollowItem.setVisible(false);
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

        //Follow button. We create the AsyncTask that calls the ApiClient (where an access to internet is done) method to follow a user
        if (id == R.id.action_follow) {


            new AsyncTask<String, Void, Void>(){

                @Override
                protected Void doInBackground(String... params) {
                    String toFollowHandle = params[0];
                    String handle = AccountManager.getUserHandle(TweetsActivity.this);
                    String token = AccountManager.getUserToken(TweetsActivity.this);
                    try {
                        new ApiClient().follow(handle, token, toFollowHandle);

                        //We also put the new following here
                        AccountManager.putFollowing(TweetsActivity.this, toFollowHandle);

                        //Needed to refresh the button follow becomes unfollow
                        ActivityCompat.invalidateOptionsMenu(TweetsActivity.this);
                    } catch (IOException e) {
                        Log.e(TweetsActivity.class.getName(), "Failed to follow " + e);
                    }


                    return null;
                }


            }.execute(userHandle);
            Toast.makeText(TweetsActivity.this, R.string.follow_success, Toast.LENGTH_SHORT).show();
            return true;
        }

        //Unfollow button. We create the AsyncTask that calls the ApiClient (where an access to internet is done) method to unfollow a user
        if(id == R.id.action_unfollow){
            new AsyncTask<String, Void, Void>(){

                @Override
                protected Void doInBackground(String... params) {
                    String toUnfollowHandle = params[0];
                    String handle = AccountManager.getUserHandle(TweetsActivity.this);
                    String token = AccountManager.getUserToken(TweetsActivity.this);
                    try {
                        new ApiClient().unfollow(handle, token, toUnfollowHandle);

                        //We also delete the following here
                        AccountManager.removeFollowing(TweetsActivity.this, toUnfollowHandle);

                        //Needed to refresh the button unfollow becomes follow
                        ActivityCompat.invalidateOptionsMenu(TweetsActivity.this);
                    } catch (IOException e) {
                        Log.e(TweetsActivity.class.getName(), String.valueOf(R.string.error) + e);
                    }


                    return null;
                }


            }.execute(userHandle);
            Toast.makeText(TweetsActivity.this, R.string.unfollow_success, Toast.LENGTH_SHORT).show();
            return true;
        }

        if(id == R.id.action_home){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Checks the followings of the logged in user
     * Its followings are stored as a Set<String> in SharedPreferences in @see com.example.hamid.minitweeter.AccountManager
     *
     * @return true if already followed, false otherwise
     */
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
