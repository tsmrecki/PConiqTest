package com.smrecki.payconiqtest.data.gitrepo;

import com.smrecki.common.retrofit.ApiInterface;
import com.smrecki.payconiqtest.model.GitRepo;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by tomislav on 29/08/2017.
 */

public class GitRepoRemote implements GitRepoData {

    ApiInterface mApiInterface;

    @Inject
    public GitRepoRemote(ApiInterface apiInterface) {
        this.mApiInterface = apiInterface;
    }

    @Override
    public Observable<List<GitRepo>> getRepositories(String username, int page, int perPage) {
        return mApiInterface.getRepositories(username, page + 1, perPage).flatMap(
                new Func1<List<GitRepo>, Observable<GitRepo>>() {
                    @Override
                    public Observable<GitRepo> call(List<GitRepo> gitRepos) {
                        return Observable.from(gitRepos);
                    }
                }
        ).toSortedList(new Func2<GitRepo, GitRepo, Integer>() {
            @Override
            public Integer call(GitRepo gitRepo, GitRepo gitRepo2) {
                return gitRepo.getFullName().toLowerCase().compareTo(gitRepo2.getFullName().toLowerCase());
            }
        });
    }

    @Override
    public Observable<GitRepo> getRepository(long id) {
        return mApiInterface.getRepository(id);
    }

    @Override
    public void saveRepositories(List<GitRepo> repoList) {

    }

    @Override
    public void saveRepository(GitRepo repo) {

    }
}
