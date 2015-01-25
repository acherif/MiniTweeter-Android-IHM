package com.example.hamid.minitweeter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.example.hamid.minitweeter.AccountManager;
import com.example.hamid.minitweeter.activity.MainActivity;
import com.example.hamid.minitweeter.activity.TweetsActivity;
import com.example.hamid.minitweeter.adapter.UsersAdapter;
import com.example.hamid.minitweeter.loaders.UsersLoader;
import com.example.hamid.minitweeter.model.User;

import java.util.List;

/**
 * Created by Hamid on 25/01/2015.
 *
 * Contains the list of users. Extended by FollowersFragment, FollowingsFragment and UsersFragment
 */

public class BasicUsersFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<User>> {

    protected static final int LOADER_USERS = 1000;

    protected UsersAdapter listAdapter;
    private boolean isMasterDetailsMode;




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listAdapter = new UsersAdapter();
        setListAdapter(listAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        isMasterDetailsMode = getActivity().findViewById(R.id.tweets_content) != null;
//        if(isMasterDetailsMode){
//            getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
//        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(LOADER_USERS, null, this);
    }

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        return new UsersLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> users) {
        listAdapter.setUsers(users);
    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) { }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        User user = listAdapter.getItem(position);
        Intent intent = new Intent(getActivity(), TweetsActivity.class);
        intent.putExtras(TweetsFragment.newArguments(user));
        intent.putExtra("title", user.getHandle() + " tweets");
        startActivity(intent);
//        if(isMasterDetailsMode) {
//            Log.i(UsersFragment.class.getName(), " onListItemClick !!");
//            Fragment tweetsFragment = new TweetsFragment();
//            tweetsFragment.setArguments(TweetsFragment.newArguments(user));
//            getFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.tweets_content, tweetsFragment)
//                    .commit();
//        } else {
//            Intent intent = new Intent(getActivity(), TweetsActivity.class);
//            intent.putExtras(TweetsFragment.newArguments(user));
//            startActivity(intent);
//        }
}


    @Override
    public void onResume() {
        super.onResume();
        if(MainActivity.getIsConnected() != AccountManager.isConnected(getActivity())){
            ActivityCompat.invalidateOptionsMenu(getActivity());
            MainActivity.setIsConnected(AccountManager.isConnected(getActivity()));
        }


    }
}

