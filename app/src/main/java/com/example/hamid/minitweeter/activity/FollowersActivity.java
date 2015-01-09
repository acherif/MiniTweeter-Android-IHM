package com.example.hamid.minitweeter.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

import com.example.hamid.minitweeter.R;
import com.example.hamid.minitweeter.fragment.FollowersFragment;
import com.example.hamid.minitweeter.fragment.TweetsFragment;

/**
 * Created by hamid on 29/12/2014.
 */
public class FollowersActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.followers_activity);

        if(savedInstanceState == null){
            Fragment followersFragment = new FollowersFragment();
            followersFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.followers_list, followersFragment)
                    .commit();
        }
    }


}