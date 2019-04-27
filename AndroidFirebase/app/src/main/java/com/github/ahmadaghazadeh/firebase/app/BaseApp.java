package com.github.ahmadaghazadeh.firebase.app;

import android.content.Context;
import android.util.Log;
//import android.support.multidex.MultiDex;


import com.github.ahmadaghazadeh.firebase.di.AppComponent;
import com.github.ahmadaghazadeh.firebase.di.DaggerAppComponent;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class BaseApp extends DaggerApplication{

    private Thread.UncaughtExceptionHandler defaultHandler;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        //MultiDex.install(this);
    }
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {

        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this::handleUncaughtException);
        AppComponent component = DaggerAppComponent.builder().getApp(this).build();
        component.inject(this);
        FirebaseApp.initializeApp(this, C.baseUserInfo, "base-userinfo");
        return component;
    }

    public void handleUncaughtException(Thread thread, Throwable e) {
        StackTraceElement[] arr = e.getStackTrace();
        final StringBuffer report = new StringBuffer(e.toString());
        final String lineSeperator = "-------------------------------\n\n";
        report.append("--------- Stack trace ---------\n\n");
        for (int i = 0; i < arr.length; i++) {
            report.append("    ");
            report.append(arr[i].toString());
            report.append(lineSeperator);
        }
        int x=0;
        defaultHandler.uncaughtException(thread, e);
        FirebaseCrash.logcat(Log.ERROR, "Error", "NPE caught");
        FirebaseCrash.report(e);
    }

}
