package com.example.hamid.minitweeter.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hamid.minitweeter.AccountManager;
import com.example.hamid.minitweeter.ApiClient;
import com.example.hamid.minitweeter.R;

import java.io.IOException;

/**
 * Created by hamid on 20/01/2015.
 */
public class LoginDialogFragment extends DialogFragment implements DialogInterface.OnShowListener {

    private EditText handleText;
    private EditText passwordText;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.login_fragment, null);
        handleText = (EditText) view.findViewById(R.id.handle);
        passwordText = (EditText) view.findViewById(R.id.password);

        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.login)
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();

        dialog.setOnShowListener(this);
        return dialog;
    }

    @Override
    public void onShow(DialogInterface dialog) {
        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
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
                    Log.e(LoginDialogFragment.class.getName(), "Login failed", e);
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
                    dismiss();
                    Toast.makeText(getActivity(), R.string.login_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), R.string.login_error, Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(handle, password);
    }
}