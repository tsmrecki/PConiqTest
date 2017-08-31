package com.smrecki.common.base.presenters;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

/**
 * Created by tomislav on 17/02/2017.
 */

public class BaseContract {

    public interface Presenter<TView extends View> {

        void subscribe(TView view);

        void unsubscribe();

        void handleError(Throwable e);

        void logout();

    }

    public interface View {

        void showProgressCircle(boolean show);

        void showError(String errorMessage);

        void showError(int stringResourceId);

        void showSnackbar(int stringResId);

        void showLoader(boolean show);

        void showLoader(boolean show, boolean cancelable);

        void showShortInfo(String info);

        void showShortInfo(int stringResourceId);

        void showInfoDialog(String title, String description, @Nullable String buttonText);

        void showInfoDialog(@StringRes int titleResourceId, @StringRes int descriptionResourceId, @StringRes int buttonText);

        void hideKeyboard();

        void onLogout();

    }
}
