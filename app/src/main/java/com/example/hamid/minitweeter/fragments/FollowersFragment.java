package com.example.hamid.minitweeter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;

import com.example.hamid.minitweeter.activity.FollowersActivity;
import com.example.hamid.minitweeter.loaders.FollowersLoader;
import com.example.hamid.minitweeter.model.User;

import java.util.List;

/**
 * Created by hamid on 29/12/2014.
 */
public class FollowersFragment extends BasicUsersFragment {

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
        if(getActivity() instanceof FollowersActivity) {
            getActivity().setTitle(user.getHandle() + " followers");
        }
    }

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
         return new FollowersLoader(getActivity(), user.getHandle());
    }

    @Override
    public void onResume() {
        super.onResume();
        listAdapter.notifyDataSetChanged();

        getLoaderManager().restartLoader(0, null, FollowersFragment.this);
    }
}
