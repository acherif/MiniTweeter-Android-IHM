package com.example.hamid.minitweeter.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hamid.minitweeter.AccountManager;
import com.example.hamid.minitweeter.R;
import com.example.hamid.minitweeter.fragment.TweetsFragment;
import com.example.hamid.minitweeter.model.User;

/**
 * Main activity. Displays List of users
 * If not logged in, a button to login is displayed.
 * If already logged in, a button to the profile activity is displayed and one to post a tweet.
 */
public class MainActivity extends ActionBarActivity {

    //
    private static boolean isConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isConnected = AccountManager.isConnected(MainActivity.this);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //Displays connect button if not logged in, profile button otherwise
        if (!AccountManager.isConnected(MainActivity.this)) {
            MenuItem profileItem = menu.findItem(R.id.profile);
            profileItem.setVisible(false);
        }else{
            MenuItem loginItem = menu.findItem(R.id.action_login);
            loginItem.setVisible(false);
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

        if (id == R.id.profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            User user = new User();
            user.setHandle(AccountManager.getUserHandle(MainActivity.this));
            intent.putExtra("title", user.getHandle() + " tweets");
            intent.putExtras(TweetsFragment.newArguments(user));
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

    public static boolean getIsConnected() {
        return isConnected;
    }

    public static void setIsConnected(boolean isConnected) {
        MainActivity.isConnected = isConnected;
    }

}
