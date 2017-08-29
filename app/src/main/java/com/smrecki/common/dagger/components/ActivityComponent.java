package com.smrecki.common.dagger.components;

import com.smrecki.common.base.views.BaseActivity;
import com.smrecki.common.dagger.modules.ActivityModule;
import com.smrecki.common.dagger.scopes.ActivityScope;

import dagger.Component;

/**
 * Created by ianic on 26/01/17.
 */

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    //activities
    void inject(BaseActivity baseActivity);

}
