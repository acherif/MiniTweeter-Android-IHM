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

import java.io.IOException;

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
        Log.i(LoginActivity.class.getName(), "heeeere Nooooow");
        handleText = (EditText) findViewById(R.id.handle);
        passwordText = (EditText) findViewById(R.id.password);
        Log.i(LoginActivity.class.getName(), "heeeere111");
        submitLogin = (Button) findViewById(R.id.submit_login);
        Log.i(LoginActivity.class.getName(), "Before Clicked");
        submitLogin.setOnClickListener(this);

        Log.i(LoginActivity.class.getName(), "After Clicked");
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

        Log.i(LoginActivity.class.getName(), "now now");

        new AsyncTask<String, Void, String>(){
            @Override
            protected String doInBackground(String... params) {
                try{
                    String handle = params[0];
                    String password = params[1];
                    return new ApiClient().login(handle, password);
                } catch(IOException e){
                    Log.e(LoginActivity.class.getName(), "Login failed", e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                if(s != null){
//                    Fragment target = getTargetFragment();
//                    if(target != null){
//                        target.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
//                    }
                    AccountManager.login(LoginActivity.this, s, handle);

                    Log.i(LoginActivity.class.getName(), "Post Login");

                    Toast.makeText(LoginActivity.this, R.string.login_success, Toast.LENGTH_SHORT).show();
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
