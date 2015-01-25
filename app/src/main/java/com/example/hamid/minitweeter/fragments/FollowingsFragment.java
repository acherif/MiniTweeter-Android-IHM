package com.example.hamid.minitweeter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;

import com.example.hamid.minitweeter.activity.FollowingsActivity;
import com.example.hamid.minitweeter.loaders.FollowingsLoader;
import com.example.hamid.minitweeter.model.User;

import java.util.List;

/**
 * Created by hamid on 03/01/2015.
 */
public class FollowingsFragment extends BasicUsersFragment {

    private static final String ARG_USER = "user";
    private User user;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getArguments().getParcelable(ARG_USER);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() instanceof FollowingsActivity) {
            getActivity().setTitle(user.getHandle() + " followings");
        }
    }


    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        return new FollowingsLoader(getActivity(), user.getHandle());
    }

    @Override
    public void onResume() {
        super.onResume();
        listAdapter.notifyDataSetChanged();

        getLoaderManager().restartLoader(0, null, FollowingsFragment.this);
    }
}

