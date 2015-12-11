package com.brynhildr.asgard.global;

import android.app.Application;
import android.content.Context;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lqshan on 11/24/15.
 */
public class MyApplication extends Application {

    private static Context context;
    private static Lock databaseLock;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
        MyApplication.databaseLock = new ReentrantLock();
    }

    public static void startUsingDatabase() {
        databaseLock.lock();
    }

    public static void completeUsingDatabase() {
        databaseLock.unlock();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

}