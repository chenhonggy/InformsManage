package com.launcher.informsmanage.Application;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by chen on 14-7-26.
 */
public class MainApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
