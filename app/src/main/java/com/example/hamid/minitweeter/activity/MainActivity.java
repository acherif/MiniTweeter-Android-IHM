package com.example.hamid.minitweeter.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hamid.minitweeter.AccountManager;
import com.example.hamid.minitweeter.R;
import com.example.hamid.minitweeter.fragment.FollowersFragment;
import com.example.hamid.minitweeter.fragment.FollowingsFragment;
import com.example.hamid.minitweeter.fragment.LoginFragment;
import com.example.hamid.minitweeter.model.User;


public class MainActivity extends ActionBarActivity {

    private static final int REQUEST_LOGIN_FOR_POST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (!AccountManager.isConnected(MainActivity.this)) {
            MenuItem item = menu.findItem(R.id.action_followers);
            item.setVisible(false);
            item = menu.findItem(R.id.action_followings);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        if (id == R.id.action_followers) {
                Intent intent = new Intent(this, FollowersActivity.class);
                User user = new User();
                user.setHandle(AccountManager.getUserHandle(MainActivity.this));
                intent.putExtras(FollowersFragment.newArguments(user));
                startActivity(intent);
                return true;
        }

        if (id == R.id.action_followings) {
                Intent intent = new Intent(this, FollowingsActivity.class);
                User user = new User();
                user.setHandle(AccountManager.getUserHandle(MainActivity.this));
                intent.putExtras(FollowingsFragment.newArguments(user));
                startActivity(intent);
                return true;
        }

        if (id == R.id.action_login) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
