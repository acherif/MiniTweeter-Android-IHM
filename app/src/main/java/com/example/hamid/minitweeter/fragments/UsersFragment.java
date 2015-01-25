package com.example.hamid.minitweeter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hamid.minitweeter.AccountManager;
import com.example.hamid.minitweeter.R;
import com.example.hamid.minitweeter.activity.PostActivity;
import com.example.hamid.minitweeter.loaders.UsersLoader;
import com.example.hamid.minitweeter.model.User;

import java.util.List;


/**
 * Created by hamid on 26/12/2014.
 */
public class UsersFragment extends BasicUsersFragment{


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.users_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.post).setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            post();
        }
    });
}

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        return new UsersLoader(getActivity());
    }


    private void post(){
        startActivity(new Intent(getActivity(), PostActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        View postView = getView().findViewById(R.id.post);

        if(AccountManager.isConnected(getActivity())){
            postView.setVisibility(View.VISIBLE);
        } else {
            postView.setVisibility(View.INVISIBLE);
        }
    }
}

