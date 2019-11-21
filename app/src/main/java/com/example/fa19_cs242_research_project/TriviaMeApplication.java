package com.example.fa19_cs242_research_project;

import android.app.Application;

import com.frogermcs.androiddevmetrics.AndroidDevMetrics;

public class TriviaMeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Use it only in debug builds
        if (BuildConfig.DEBUG) {
            AndroidDevMetrics.initWith(this);
        }
    }
}

