package com.example.hamid.minitweeter.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hamid.minitweeter.AccountManager;
import com.example.hamid.minitweeter.ApiClient;
import com.example.hamid.minitweeter.R;
import com.example.hamid.minitweeter.fragment.TweetsFragment;
import com.example.hamid.minitweeter.model.User;
import com.example.hamid.minitweeter.util.Pair;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hamid on 14/01/2015.
 */
public class LoginActivity extends ActionBarActivity implements View.OnClickListener{

    private EditText handleText;
    private EditText passwordText;
    private Button submitLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fragment);
        handleText = (EditText) findViewById(R.id.handle);
        passwordText = (EditText) findViewById(R.id.password);
        submitLogin = (Button) findViewById(R.id.submit_login);
        submitLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        final String handle = handleText.getText().toString();
        String password = passwordText.getText().toString();

        if(handle.isEmpty()){
            handleText.setError(getString(R.string.required));
            handleText.requestFocus();
            return;
        }

        new AsyncTask<String, Void, Pair<String, Set<String>>>(){
            @Override
            protected Pair<String, Set<String>> doInBackground(String... params) {
                try{
                    String handle = params[0];
                    String password = params[1];
                    String token = new ApiClient().login(handle, password);
                    List<User> followings = null;
                    try {
                        followings = new ApiClient().getFollowings(handle);
                    } catch (IOException e) {
                        Log.i(LoginActivity.class.getName(), "Failed to load followings");
                    }
                    Set<String> followingsHandlers = new HashSet<>();
                    for(User user : followings){
                        followingsHandlers.add(user.getHandle());
                    }

                    return new Pair<>(token, followingsHandlers);
                } catch(IOException e){
                    Log.e(LoginActivity.class.getName(), "Login failed", e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Pair<String, Set<String>> s) {
                if(s.getFirst() != null){
                    AccountManager.login(LoginActivity.this, s.getFirst(), handle);

                    Toast.makeText(LoginActivity.this, R.string.login_success, Toast.LENGTH_SHORT).show();

                    AccountManager.putFollowings(LoginActivity.this, s.getSecond());
                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                    User user = new User();
                    user.setHandle(handle);
                    intent.putExtras(TweetsFragment.newArguments(user));
                    intent.putExtra("title", handle + " tweets");
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, R.string.login_error, Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(handle, password);
    }

}
