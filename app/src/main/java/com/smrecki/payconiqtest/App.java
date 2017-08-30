package com.smrecki.payconiqtest;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.smrecki.common.dagger.components.ApplicationComponent;
import com.smrecki.common.dagger.components.DaggerApplicationComponent;
import com.smrecki.common.dagger.modules.ContextModule;

/**
 * Created by tomislav on 29/08/2017.
 */

public class App extends Application {

    private ApplicationComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        FlowManager.init(new FlowConfig.Builder(this).build());

        ApplicationComponent mComponent = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        this.mComponent = mComponent;
    }

    public ApplicationComponent getComponent() {
        return mComponent;
    }
}
