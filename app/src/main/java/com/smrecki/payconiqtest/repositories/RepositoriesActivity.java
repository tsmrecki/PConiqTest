package com.smrecki.payconiqtest.repositories;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.smrecki.common.base.views.BaseActivity;
import com.smrecki.common.utils.CustomTabUtil;
import com.smrecki.payconiqtest.R;
import com.smrecki.payconiqtest.model.GitRepo;
import com.smrecki.payconiqtest.search.SearchActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class RepositoriesActivity extends BaseActivity implements RepositoriesContract.View, GitRepoAdapter.OnGitRepoInteractionListener {

    private static final String ARG_USERNAME = "username";
    private static final String DEFAULT_USERNAME = "JakeWharton";
    @Inject
    RepositoriesContract.Presenter<RepositoriesContract.View> presenter;
    @BindView(R.id.repositories_rv)
    RecyclerView repositoriesRV;
    @BindView(R.id.search_username)
    FloatingActionButton searchFAB;

    private String mUsername;
    private GitRepoAdapter mGitRepoAdapter;
    private boolean loading = true;

    public static Intent buildIntent(Context callingContext, String username) {
        Intent intent = new Intent(callingContext, RepositoriesActivity.class);
        intent.putExtra(ARG_USERNAME, username);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);
        getActivityComponent().inject(this);

        if (getIntent() != null) {
            mUsername = getIntent().getStringExtra(ARG_USERNAME);
            if (mUsername == null) {
                mUsername = DEFAULT_USERNAME;
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                searchFAB.setVisibility(View.GONE);
            }
        }

        getSupportActionBar().setTitle(mUsername);

        presenter.setUsername(mUsername);

        mGitRepoAdapter = new GitRepoAdapter();
        mGitRepoAdapter.setOnGitRepoInteractionListener(this);

        repositoriesRV.setLayoutManager(new LinearLayoutManager(this));
        repositoriesRV.setItemAnimator(new DefaultItemAnimator());
        repositoriesRV.setAdapter(mGitRepoAdapter);

        repositoriesRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && !loading) {
                    if (((LinearLayoutManager) repositoriesRV.getLayoutManager()).findLastCompletelyVisibleItemPosition()
                            > mGitRepoAdapter.getItemCount() - 3) {
                        loading = true;
                        presenter.loadMore();
                    }
                }
            }
        });

    }

    @OnClick(R.id.search_username)
    void searchByUsername() {
        startActivity(new Intent(this, SearchActivity.class));
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void setRepositories(List<GitRepo> gitRepoList) {
        mGitRepoAdapter.setGitRepoList(gitRepoList);
        loading = false;
    }

    @Override
    public void addRepositories(List<GitRepo> gitRepoList) {
        mGitRepoAdapter.addGitRepoList(gitRepoList);
        loading = false;
    }

    @Override
    public void showPaginationLoader(boolean showLoader) {
        mGitRepoAdapter.showLoader(showLoader);
    }

    @Override
    public void onGitRepoClicked(GitRepo gitRepo, View view) {
        CustomTabUtil.launchUrl(this, gitRepo.getHomepageUrl());
    }
}
