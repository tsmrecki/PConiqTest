package com.smrecki.payconiqtest.repositories;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.smrecki.common.base.views.BaseActivity;
import com.smrecki.payconiqtest.R;
import com.smrecki.payconiqtest.model.GitRepo;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class RepositoriesActivity extends BaseActivity implements RepositoriesContract.View {

    private static final String ARG_USERNAME = "username";
    private static final String DEFAULT_USERNAME = "JakeWharton";
    @Inject
    RepositoriesContract.Presenter<RepositoriesContract.View> presenter;
    @BindView(R.id.repositories_rv)
    RecyclerView repositoriesRV;
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
            if (mUsername == null) mUsername = DEFAULT_USERNAME;
        }

        getSupportActionBar().setTitle(mUsername);

        presenter.setUsername(mUsername);

        mGitRepoAdapter = new GitRepoAdapter();
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
}
