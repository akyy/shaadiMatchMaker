package com.shaadi.match.maker.application;

import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.shaadi.match.maker.di.component.ApplicationComponent;
import com.shaadi.match.maker.di.component.DaggerApplicationComponent;
import com.shaadi.match.maker.di.module.ApplicationModule;

/**
 * Created by ajay
 */
public class MatchMakerApplication extends MultiDexApplication {

    private static MatchMakerApplication applicationContext;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private ApplicationComponent mApplicationComponent;

    public static MatchMakerApplication app() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

}