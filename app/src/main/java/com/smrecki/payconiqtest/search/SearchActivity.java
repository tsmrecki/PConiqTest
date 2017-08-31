package com.smrecki.payconiqtest.search;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.smrecki.common.base.views.BaseActivity;
import com.smrecki.payconiqtest.R;
import com.smrecki.payconiqtest.repositories.RepositoriesActivity;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.username_et)
    EditText usernameET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            doEnterAnim();
        }
    }

    @OnEditorAction(R.id.username_et)
    boolean onEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            search();
            return true;
        }
        return false;
    }

    @OnClick(R.id.search)
    void search() {
        String username = usernameET.getText().toString().trim();

        if (username.isEmpty()) {
            showError(getString(R.string.error_field_required));
            return;
        }
        startActivity(RepositoriesActivity.buildIntent(this, username));
    }

    @OnClick(R.id.close)
    public void dismiss() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            doExitAnim();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }

    @Override
    public void onBackPressed() {
        dismiss();
    }

    /**
     * On Lollipop+ perform a circular reveal animation (an expanding circular mask) when showing
     * the search panel.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void doEnterAnim() {
        View scrim = findViewById(R.id.scrim);
        scrim.animate()
                .alpha(1f)
                .setDuration(500L)
                .setInterpolator(
                        AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in))
                .start();


        final View searchPanel = findViewById(R.id.search_panel);
        if (searchPanel != null) {
            searchPanel.getViewTreeObserver().addOnPreDrawListener(
                    new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            searchPanel.getViewTreeObserver().removeOnPreDrawListener(this);

                            int revealRadius = ((ViewGroup) searchPanel.getParent()).getHeight();
                            // Center the animation on the top right of the panel i.e. near to the
                            // search button which launched this screen.
                            Animator show = ViewAnimationUtils.createCircularReveal(searchPanel,
                                    searchPanel.getRight() - (int) (24 * getResources().getDisplayMetrics().density),
                                    searchPanel.getTop() + (int) (25 * getResources().getDisplayMetrics().density), 0f, revealRadius);
                            show.setDuration(400L);
                            show.setInterpolator(AnimationUtils.loadInterpolator(SearchActivity.this,
                                    android.R.interpolator.fast_out_linear_in));
                            show.start();
                            return false;
                        }
                    });
        }
    }

    /**
     * On Lollipop+ perform a circular animation (a contracting circular mask) when hiding the
     * search panel.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void doExitAnim() {
        final View searchPanel = findViewById(R.id.search_panel);
        int revealRadius = (int) Math.sqrt(Math.pow(searchPanel.getWidth(), 2)
                + Math.pow(searchPanel.getHeight(), 2));
        // Animating the radius to 0 produces the contracting effect
        Animator shrink = ViewAnimationUtils.createCircularReveal(searchPanel,
                searchPanel.getRight() - (int) (32 * getResources().getDisplayMetrics().density) + (int) (24
                        * getResources().getDisplayMetrics().density), searchPanel.getTop(), revealRadius, 0f);
        shrink.setDuration(200L);
        shrink.setInterpolator(AnimationUtils.loadInterpolator(SearchActivity.this,
                android.R.interpolator.fast_out_slow_in));
        shrink.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                searchPanel.setVisibility(View.INVISIBLE);
                ActivityCompat.finishAfterTransition(SearchActivity.this);
                overridePendingTransition(0, 0);
            }
        });
        shrink.start();

        findViewById(R.id.scrim).animate()
                .alpha(0f)
                .setDuration(200L)
                .setInterpolator(
                        AnimationUtils.loadInterpolator(SearchActivity.this,
                                android.R.interpolator.fast_out_slow_in))
                .start();
    }
}
