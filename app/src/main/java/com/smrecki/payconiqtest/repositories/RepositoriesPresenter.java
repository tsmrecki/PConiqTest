package com.smrecki.payconiqtest.repositories;

import com.smrecki.common.base.presenters.BasePresenter;
import com.smrecki.common.retrofit.ApiInterface;
import com.smrecki.payconiqtest.data.gitrepo.GitRepository;
import com.smrecki.payconiqtest.model.GitRepo;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by tomislav on 30/08/2017.
 */

public class RepositoriesPresenter extends BasePresenter<RepositoriesContract.View> implements
        RepositoriesContract.Presenter<RepositoriesContract.View> {

    private static final int PER_PAGE = 15;
    private int page = 0;

    private GitRepository mGitRepository;

    private List<GitRepo> mGitRepoList;
    private String mUsername;

    @Inject
    RepositoriesPresenter(CompositeSubscription compositeSubscription,
            ApiInterface apiInterface, GitRepository gitRepository) {
        super(compositeSubscription, apiInterface);
        mGitRepository = gitRepository;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    @Override
    public void subscribe(RepositoriesContract.View view) {
        super.subscribe(view);

        if (mGitRepoList == null) {
            getRepos(page, PER_PAGE);
        } else {
            getView().setRepositories(mGitRepoList);
        }
    }

    private void getRepos(final int page, int perPage) {
        getView().showProgressCircle(true);
        getCompositeSubscription().add(
                mGitRepository.getRepositories(
                        mUsername,
                        page,
                        perPage
                ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread(), true)
                        .subscribe(new Subscriber<List<GitRepo>>() {
                            @Override
                            public void onCompleted() {
                                getView().showProgressCircle(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                handleError(e);
                                getView().showPaginationLoader(false);
                            }

                            @Override
                            public void onNext(List<GitRepo> gitRepos) {
                                if (page == 0) {
                                    mGitRepoList = gitRepos;
                                    getView().setRepositories(mGitRepoList);
                                } else {
                                    mGitRepoList.addAll(gitRepos);
                                    getView().addRepositories(gitRepos);
                                }

                                if (gitRepos.isEmpty() || gitRepos.size() < PER_PAGE) {
                                    getView().showPaginationLoader(false);
                                }
                            }
                        }));
    }

    @Override
    public void refresh() {
        page = 0;
        getRepos(page, PER_PAGE);
    }

    @Override
    public void loadMore() {
        page++;
        getRepos(page, PER_PAGE);
    }
}
