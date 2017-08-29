package com.smrecki.common.base.presenters;


import com.smrecki.common.retrofit.ApiInterface;
import com.smrecki.common.retrofit.RetrofitException;

import java.io.IOException;
import java.lang.ref.WeakReference;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by tomislav on 25/02/17.
 */
public class BasePresenter<TView extends BaseContract.View> implements BaseContract.Presenter<TView> {

    private WeakReference<TView> mView;

    private CompositeSubscription mCompositeSubscription;
    private ApiInterface mApiInterface;

    @Inject
    public BasePresenter(CompositeSubscription compositeSubscription, ApiInterface apiInterface) {
        this.mCompositeSubscription = compositeSubscription;
        mApiInterface = apiInterface;
    }

    protected ApiInterface getApiInterface() {
        return mApiInterface;
    }

    protected CompositeSubscription getCompositeSubscription() {
        return mCompositeSubscription;
    }

    @Override
    public void subscribe(TView view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public void unsubscribe() {
        mCompositeSubscription.clear();
        if (mView != null) {
            mView.clear();
            mView = null;
        }
    }

    @Override
    public void handleError(Throwable e) {
        e.printStackTrace();
        if (e instanceof RetrofitException) {
            RetrofitException re = (RetrofitException) e;
            if (!re.processNetworkError(getView())) {
                try {
                    ApiInterface.GeneralError generalError = re.getErrorBodyAs(ApiInterface.GeneralError.class);
                    getView().showError(generalError.getMessage());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (re.getResponse() != null && re.getResponse().code() == 401) {
                logout();
            }
        }
    }

    @Override
    public void logout() {
        getView().onLogout();
    }

    protected TView getView() {
        if (mView == null) throw new MvpViewNotAttachedException();
        return mView.get();
    }

    public void showProgressCircle(boolean show) {
        getView().showProgressCircle(show);
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.subscribe(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }

}
