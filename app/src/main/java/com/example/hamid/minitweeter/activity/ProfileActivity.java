package com.example.hamid.minitweeter.activity;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by hamid on 14/01/2015.
 * Profile activity : contains logged user's tweets, and the possibility to display its followers and followings.
 */
public class ProfileActivity extends TweetsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Avoid returning to the login activity once one is already logged in
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
