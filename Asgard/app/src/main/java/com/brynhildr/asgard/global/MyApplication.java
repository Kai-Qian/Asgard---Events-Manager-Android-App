package com.brynhildr.asgard.global;

import android.app.Application;
import android.content.Context;

/**
 * Created by lqshan on 11/24/15.
 */
public class MyApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}