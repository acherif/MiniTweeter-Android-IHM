package com.example.hamid.minitweeter.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

import com.example.hamid.minitweeter.R;
import com.example.hamid.minitweeter.fragment.FollowersFragment;
import com.example.hamid.minitweeter.fragment.FollowingsFragment;

/**
 * Created by hamid on 03/01/2015.
 */
public class FollowingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.followings_activity);

        if(savedInstanceState == null){
            Fragment followersFragment = new FollowingsFragment();
            followersFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.followings_list, followersFragment)
                    .commit();
        }
    }


}
