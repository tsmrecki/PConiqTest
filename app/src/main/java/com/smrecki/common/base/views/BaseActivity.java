package com.smrecki.common.base.views;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.greysonparrelli.permiso.PermisoActivity;
import com.smrecki.common.base.presenters.BaseContract;
import com.smrecki.common.dagger.components.ActivityComponent;
import com.smrecki.common.dagger.components.DaggerActivityComponent;
import com.smrecki.common.dagger.modules.ActivityModule;
import com.smrecki.common.retrofit.ApiInterface;
import com.smrecki.common.utils.DialogUtil;
import com.smrecki.payconiqtest.App;
import com.smrecki.payconiqtest.R;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tomislav on 25/02/17.
 */
public abstract class BaseActivity extends PermisoActivity implements BaseContract.View {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Inject
    protected ApiInterface apiInterface;
    protected Dialog pd;
    @Nullable
    @BindView(value = R.id.toolbarProgressCircle)
    ProgressBar progressCircle;
    private NotificationManager mNotificationManager;
    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Locale locale = new Locale("hr", "HR");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, null);
        super.onCreate(savedInstanceState);

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((App) getApplication()).getComponent())
                .build();
        getActivityComponent().inject(this);

        pd = new ProgressDialog(this);
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    protected NotificationManager getNotificationManager() {
        return mNotificationManager;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        ButterKnife.bind(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected boolean checkPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(this, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    /*
        BaseView method implementations
     */
    @Override
    public void showError(String errorMessage) {
        Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_LONG);
        toast.getView().setBackground(ContextCompat.getDrawable(this, R.drawable.toast_background));
        toast.show();
    }

    @Override
    public void showShortInfo(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showProgressCircle(boolean show) {
        if (progressCircle != null) {
            progressCircle.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        } else {
            showLoader(show);
        }
    }

    @Override
    public void showLoader(boolean show) {
        if (show) {
            pd.show();
        } else {
            pd.dismiss();
        }

        if (!show) {
            pd.setCancelable(true);
        }
    }

    @Override
    public void showLoader(boolean show, boolean cancelable) {
        pd.setCancelable(cancelable);
        showLoader(show);
    }

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onLogout() {
        //TODO: open login page if logged out
        /*
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        */
    }


    @Override
    public void showError(int stringResourceId) {
        showError(getString(stringResourceId));
    }

    @Override
    public void showShortInfo(int stringResourceId) {
        showShortInfo(getString(stringResourceId));
    }

    @Override
    public void showSnackbar(int stringResId) {
        Snackbar.make(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0), stringResId, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showInfoDialog(String title, String description, @Nullable String buttonText) {
        DialogUtil.buildInfoDialog(
                this,
                title,
                description,
                buttonText
        ).show();
    }

    @Override
    public void showInfoDialog(@StringRes int titleResourceId, @StringRes int descriptionResourceId, @StringRes int buttonText) {
        showInfoDialog(
                titleResourceId != 0 ? getString(titleResourceId) : null,
                descriptionResourceId != 0 ? getString(descriptionResourceId) : null,
                buttonText != 0 ? getString(buttonText) : null
        );
    }

}
