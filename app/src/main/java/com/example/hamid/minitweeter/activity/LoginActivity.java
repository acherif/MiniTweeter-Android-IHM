package com.example.hamid.minitweeter.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

import com.example.hamid.minitweeter.R;
import com.example.hamid.minitweeter.fragment.LoginFragment;

/**
 * Created by hamid on 14/01/2015.
 */
public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fragment);

        if(savedInstanceState == null){
            Fragment loginFragment = new LoginFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .commit();
        }
    }
}
