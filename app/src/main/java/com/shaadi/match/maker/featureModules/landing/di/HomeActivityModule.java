package com.shaadi.match.maker.featureModules.landing.di;

import android.content.Context;

import com.shaadi.match.maker.db.MatchMakerDatabase;
import com.shaadi.match.maker.di.scopes.UserScope;
import com.shaadi.match.maker.featureModules.landing.repo.HomeActivityRepository;
import com.shaadi.match.maker.featureModules.landing.repo.HomeActivityRestApi;
import com.shaadi.match.maker.preferences.CommonPreferences;
import com.shaadi.match.maker.utils.Util;
import com.shaadi.match.maker.utils.WSConstants;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by ajay
 */
@Module
public class HomeActivityModule {

    @Provides
    @UserScope
    public HomeActivityRestApi providesHomeActivityRestApi(@Named(WSConstants.RETROFIT_WITHOUT_HEADERS) Retrofit retrofit) {
        return retrofit.create(HomeActivityRestApi.class);
    }


    @Provides
    @UserScope
    HomeActivityRepository provideHomeActivityRepository(HomeActivityRestApi homeActivityRestApi, Util util, Context context, CommonPreferences prefs,
                                                         MatchMakerDatabase db) {
        HomeActivityRepository homeActivityRepository = HomeActivityRepository.getInstance();
        homeActivityRepository.setVariables(homeActivityRestApi, util, context, prefs, db);
        return homeActivityRepository;
    }


}
