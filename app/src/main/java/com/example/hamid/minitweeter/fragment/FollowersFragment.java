package com.example.hamid.minitweeter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;

import com.example.hamid.minitweeter.activity.FollowersActivity;
import com.example.hamid.minitweeter.adapter.UsersAdapter;
import com.example.hamid.minitweeter.loaders.FollowersLoader;
import com.example.hamid.minitweeter.model.User;

import java.util.List;

/**
 * Created by hamid on 29/12/2014.
 */
public class FollowersFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<User>> {

    private static final String ARG_USER = "user";
    private static final int LOADER_USERS = 1000;
    private static final int TYPE = 1;

    private User user;

    private UsersAdapter listAdapter;
    private boolean isMasterDetailsMode;

    public static Bundle newArguments(User user){
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        return args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getArguments().getParcelable(ARG_USER);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listAdapter = new UsersAdapter();
        setListAdapter(listAdapter);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() instanceof FollowersActivity) {
            getActivity().setTitle(user.getHandle() + " followers");
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(LOADER_USERS, null, this);
    }

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
         return new FollowersLoader(getActivity(), user.getHandle());
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> users) {
        listAdapter.setUsers(users);
        setListAdapter(listAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) { }


}
