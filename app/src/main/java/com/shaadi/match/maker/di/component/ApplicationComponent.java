package com.shaadi.match.maker.di.component;

import android.content.Context;

import com.shaadi.match.maker.db.MatchMakerDatabase;
import com.shaadi.match.maker.di.module.ApplicationModule;
import com.shaadi.match.maker.preferences.CommonPreferences;
import com.shaadi.match.maker.utils.AnimationUtil;
import com.shaadi.match.maker.utils.permissionManager.PermissionUtils;
import com.shaadi.match.maker.utils.Util;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by ajay
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {


    @Named("WithoutHeaders")
    Retrofit provideRetrofitWithoutHeaders();

    Context providesContext();

    CommonPreferences providesSharedPreferences();

    Util provideUtility();

    AnimationUtil provideAnimation();
    
    PermissionUtils providePermissionUtils();

    MatchMakerDatabase provideAppDatabase();


}
