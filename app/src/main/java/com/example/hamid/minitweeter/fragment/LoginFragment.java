package com.example.hamid.minitweeter.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hamid.minitweeter.AccountManager;
import com.example.hamid.minitweeter.ApiClient;
import com.example.hamid.minitweeter.R;
import com.example.hamid.minitweeter.activity.ProfileActivity;
import com.example.hamid.minitweeter.model.User;

import java.io.IOException;

/**
 * Created by hamid on 14/01/2015.
 */
public class LoginFragment extends Fragment {

    private EditText handleText;
    private EditText passwordText;
    private Button submitLogin;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.login_fragment, null);
        handleText = (EditText) view.findViewById(R.id.handle);
        passwordText = (EditText) view.findViewById(R.id.password);

        submitLogin = (Button) view.findViewById(R.id.submit_login);
        submitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private void login(){
        final String handle = handleText.getText().toString();
        String password = passwordText.getText().toString();

        if(handle.isEmpty()){
            handleText.setError(getString(R.string.required));
            handleText.requestFocus();
            return;
        }
        new AsyncTask<String, Void, String>(){
            @Override
            protected String doInBackground(String... params) {
                try{
                    String handle = params[0];
                    String password = params[1];
                    return new ApiClient().login(handle, password);
                } catch(IOException e){
                    Log.e(LoginFragment.class.getName(), "Login failed", e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                if(s != null){
                    Fragment target = getTargetFragment();
                    if(target != null){
                        target.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
                    }
                    AccountManager.login(getActivity(), s, handle);
                    Toast.makeText(getActivity(), R.string.login_success, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), ProfileActivity.class);
                    User user = new User();
                    user.setHandle(handle);
                    intent.putExtras(TweetsFragment.newArguments(user));
                    intent.putExtra("title", handle + " tweets");
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), R.string.login_error, Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(handle, password);
    }
}
