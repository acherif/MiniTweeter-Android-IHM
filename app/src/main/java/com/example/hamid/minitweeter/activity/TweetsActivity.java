package com.example.hamid.minitweeter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hamid.minitweeter.AccountManager;
import com.example.hamid.minitweeter.R;
import com.example.hamid.minitweeter.fragment.TweetsFragment;

/**
 * Created by hamid on 26/12/2014.
 */
public class TweetsActivity extends ActionBarActivity{

    public static final String EXTRA_USER_ID = "userId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweets_activity);

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

        return super.onOptionsItemSelected(item);
    }
}
