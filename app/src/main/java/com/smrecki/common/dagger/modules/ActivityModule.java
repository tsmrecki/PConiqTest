package com.smrecki.common.dagger.modules;

import android.app.Activity;
import android.content.Context;

import com.smrecki.common.dagger.qualifiers.ActivityContext;
import com.smrecki.payconiqtest.repositories.RepositoriesContract;
import com.smrecki.payconiqtest.repositories.RepositoriesPresenter;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by tomislav on 29/08/2017.
 */
@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeSubscription provideCompositeSubscription() {
        return new CompositeSubscription();
    }

    @Provides
    RepositoriesContract.Presenter<RepositoriesContract.View> repoPresenter(RepositoriesPresenter presenter) {
        return presenter;
    }
}
