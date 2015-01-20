package com.example.hamid.minitweeter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Set;


/**
 * Created by hamid on 28/12/2014.
 */
public class AccountManager {

    private static final String PREF_API_TOKEN = "apiToken";
    private static final String PREF_API_HANDLE = "apiHandle";
    private static final String PREF_API_FOLLOWINGS = "apiFollowings";

    public static final String CONNECTED_STATE = "connected";
    public static final String DISCONNECTED_STATE = "disconnected";
    private String previousState = DISCONNECTED_STATE;
    public static boolean isConnected(Context context){
        return getUserToken(context) != null;
    }

    public static String getUserToken(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(PREF_API_TOKEN, null);
    }

    public static String getUserHandle(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(PREF_API_HANDLE, null);
    }

    public static Set<String> getUserFollowings(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getStringSet(PREF_API_FOLLOWINGS, null);
    }

    public static void login(Context context, String token, String handle){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref.edit()
                .putString(PREF_API_TOKEN, token)
                .putString(PREF_API_HANDLE, handle)
                .apply();
    }

    public static void logout(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref.edit()
                .remove(PREF_API_TOKEN)
                .remove(PREF_API_HANDLE)
                .remove(PREF_API_FOLLOWINGS)
                .apply();
    }

    public static void putFollowings(Context context, Set<String> followings){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        Log.i(AccountManager.class.getName(), "here = " + followings.size());
        pref.edit()
                .putStringSet(PREF_API_FOLLOWINGS, followings)
                .apply();
    }

    public static void putFollowing(Context context, String following){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> followings = pref.getStringSet(PREF_API_FOLLOWINGS, null);
        followings.add(following);
        pref.edit()
                .remove(PREF_API_FOLLOWINGS)
                .apply();
        pref.edit()
                .putStringSet(PREF_API_FOLLOWINGS, followings)
                .apply();
    }

    public static void removeFollowing(Context context, String following){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> followings = pref.getStringSet(PREF_API_FOLLOWINGS, null);
        followings.add(following);
        pref.edit()
                .remove(PREF_API_FOLLOWINGS)
                .apply();
        followings.remove(following);
        pref.edit()
                .putStringSet(PREF_API_FOLLOWINGS, followings)
                .apply();
    }
}

