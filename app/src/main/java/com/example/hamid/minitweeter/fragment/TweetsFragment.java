package com.example.hamid.minitweeter.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;

import com.example.hamid.minitweeter.activity.TweetsActivity;
import com.example.hamid.minitweeter.adapter.TweetsAdapter;
import com.example.hamid.minitweeter.loaders.TweetsLoader;
import com.example.hamid.minitweeter.model.Tweet;
import com.example.hamid.minitweeter.model.User;

import java.util.List;

/**
 * Created by hamid on 26/12/2014.
 */
public class TweetsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<Tweet>> {

    private static final int LOADER_TWEETS = 1000;
    private static final String ARG_USER= "user";

    private User user;
    private TweetsAdapter listAdapter;

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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listAdapter = new TweetsAdapter();
        getListView().setDividerHeight(0);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() instanceof TweetsActivity) {
            getActivity().setTitle((String) getArguments().get("title"));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(LOADER_TWEETS, null, this);
    }

    @Override
    public Loader<List<Tweet>> onCreateLoader(int i, Bundle bundle) {
        return new TweetsLoader(getActivity(), user.getHandle());
    }

    @Override
    public void onLoadFinished(Loader<List<Tweet>> listLoader, List<Tweet> tweets) {
        listAdapter.setTweets(tweets);
        setListAdapter(listAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<Tweet>> listLoader) {

    }
}
