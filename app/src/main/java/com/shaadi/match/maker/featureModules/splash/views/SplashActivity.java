package com.shaadi.match.maker.featureModules.splash.views;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.shaadi.match.maker.R;
import com.shaadi.match.maker.application.MatchMakerApplication;
import com.shaadi.match.maker.databinding.ActivitySplashBinding;
import com.shaadi.match.maker.featureModules.landing.views.HomeActivity;
import com.shaadi.match.maker.featureModules.login.LoginActivity;
import com.shaadi.match.maker.featureModules.splash.di.DaggerSplashActivityComponent;
import com.shaadi.match.maker.featureModules.splash.di.SplashActivityComponent;
import com.shaadi.match.maker.featureModules.splash.di.SplashActivityModule;
import com.shaadi.match.maker.preferences.CommonPreferences;

import javax.inject.Inject;

/**
 * Created by ajay
 */
public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Inject
    CommonPreferences prefs;

    private ActivitySplashBinding binding;
    private SplashActivityComponent splashActivityComponent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        splashActivityComponent = DaggerSplashActivityComponent.builder()
                .applicationComponent(((MatchMakerApplication) getApplication()).getApplicationComponent())
                .splashActivityModule(new SplashActivityModule())
                .build();

        splashActivityComponent.inject(this);

        scheduleSplashScreen();

    }

    private void scheduleSplashScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLoginStatus();
            }
        }, SPLASH_TIME_OUT);
    }

    private void checkLoginStatus() {

        if (prefs.isFirstTimeLogin()) {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            startActivity(intent);
            finish();
        }

    }


}
