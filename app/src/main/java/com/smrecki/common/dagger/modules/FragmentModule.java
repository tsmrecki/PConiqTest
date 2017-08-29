package com.smrecki.common.dagger.modules;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.smrecki.common.dagger.qualifiers.ActivityContext;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by tomislav on 29/08/2017.
 */
@Module
public class FragmentModule {

    private Fragment mActivity;

    public FragmentModule(Fragment activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity.getActivity();
    }

    @Provides
    Activity provideActivity() {
        return mActivity.getActivity();
    }

    @Provides
    CompositeSubscription provideCompositeSubscription() {
        return new CompositeSubscription();
    }

}
