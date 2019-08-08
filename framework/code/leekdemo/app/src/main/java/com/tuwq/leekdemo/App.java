package com.tuwq.leekdemo;


import android.app.Activity;
import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    private List<Activity> activities=new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }

    public void addActivity(Activity activity){
        activities.add(activity);
    }

    public void exit(){
        for (Activity activity : activities) {
            activity.finish();
        }
        activities.clear();
    }
}

