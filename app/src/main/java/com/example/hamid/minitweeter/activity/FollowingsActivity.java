package com.example.hamid.minitweeter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.hamid.minitweeter.R;
import com.example.hamid.minitweeter.fragments.FollowingsFragment;

/**
 * Created by hamid on 03/01/2015.
 * Followings activity : contains the followings
 */
public class FollowingsActivity extends BasicFollowActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.followings_activity);

        if(savedInstanceState == null){
            Fragment followingsFragment = new FollowingsFragment();
            followingsFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.followings_list, followingsFragment)
                    .commit();
        }
    }

}
