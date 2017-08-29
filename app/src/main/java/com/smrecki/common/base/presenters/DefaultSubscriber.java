package com.smrecki.common.base.presenters;

import android.util.Log;

import rx.Subscriber;

/**
 * Created by tomislav on 29/08/2017.
 */

public abstract class DefaultSubscriber<T> extends Subscriber<T> {

    private static final String TAG = "DefaultSubscriber";
    private BaseContract.Presenter presenter;

    public DefaultSubscriber(BaseContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        presenter.handleError(e);
    }

    @Override
    public void onNext(T t) {
        Log.d(TAG, "Success: " + t);
    }
}
