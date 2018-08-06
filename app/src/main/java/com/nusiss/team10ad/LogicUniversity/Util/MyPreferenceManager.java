package com.nusiss.team10ad.LogicUniversity.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MyPreferenceManager {

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public MyPreferenceManager(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    //Method to clear the access token of the application.
    public void clearLoginData() {
        editor.remove(Constants.KEY_ACCESS_TOKEN);
        editor.remove(Constants.USER_GSON);
        editor.apply();
    }
}
