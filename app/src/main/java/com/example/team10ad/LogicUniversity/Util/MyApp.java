package com.example.team10ad.LogicUniversity.Util;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {

    public static MyApp myApp ;
    public static MyPreferenceManager myPreferenceManager;

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
    }

    /*For creating the context of the Whole app.*/
    public static MyApp getInstance() {
        return myApp ;
    }

    /*This is for getting the instance of the MyPreferenceManager class using the context of MyApp App.*/
    public static MyPreferenceManager getPreferenceManager() {
        if (myPreferenceManager == null) {
            myPreferenceManager = new MyPreferenceManager(getInstance());
        }
        return myPreferenceManager;
    }
}