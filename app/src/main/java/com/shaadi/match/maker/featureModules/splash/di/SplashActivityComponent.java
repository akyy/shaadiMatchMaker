package com.shaadi.match.maker.featureModules.splash.di;

import com.shaadi.match.maker.di.component.ApplicationComponent;
import com.shaadi.match.maker.di.scopes.UserScope;
import com.shaadi.match.maker.featureModules.splash.views.SplashActivity;

import dagger.Component;

/**
 * Created by ajay
 */
@UserScope
@Component(dependencies = ApplicationComponent.class, modules = SplashActivityModule.class)
public interface SplashActivityComponent {
    void inject(SplashActivity splashActivity);
}
