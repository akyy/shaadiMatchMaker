package com.shaadi.match.maker.featureModules.landing.di;

import com.shaadi.match.maker.di.component.ApplicationComponent;
import com.shaadi.match.maker.di.scopes.UserScope;
import com.shaadi.match.maker.featureModules.landing.adapters.HomeActivityRecyclerAdapter;
import com.shaadi.match.maker.featureModules.landing.views.HomeActivity;
import com.shaadi.match.maker.featureModules.landing.views.HomeActivityViewModel;

import dagger.Component;

/**
 * Created by ajay
 */
@UserScope
@Component(dependencies = ApplicationComponent.class, modules = HomeActivityModule.class)
public interface HomeActivityComponent {

    void inject(HomeActivity homeActivity);

    void inject(HomeActivityViewModel homeActivityViewModel);

    void inject(HomeActivityRecyclerAdapter homeActivityRecyclerAdapter);

}
