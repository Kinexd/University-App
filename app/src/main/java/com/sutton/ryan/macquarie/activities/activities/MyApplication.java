package com.sutton.ryan.macquarie.activities.activities;

import android.app.Application;
import android.content.Context;

/**
 * Created by ryank on 29/06/2016.
 */
public class MyApplication extends Application{
     private static MyApplication sInstance;

    public void onCreate(){
        super.onCreate();
        sInstance=this;
    }

    public static MyApplication getsInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }
}
