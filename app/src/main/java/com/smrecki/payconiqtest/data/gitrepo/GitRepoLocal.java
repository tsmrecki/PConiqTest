package com.smrecki.payconiqtest.data.gitrepo;

import com.smrecki.payconiqtest.model.GitRepo;

import java.util.List;

import rx.Observable;

/**
 * Created by tomislav on 29/08/2017.
 */

//TODO: implement local repo
public class GitRepoLocal implements GitRepoData {
    @Override
    public Observable<List<GitRepo>> getRepositories(String username, int page, int perPage) {
        return Observable.empty();
    }

    @Override
    public Observable<GitRepo> getRepository(long id) {
        return Observable.empty();
    }

    @Override
    public void saveRepositories(List<GitRepo> repoList) {

    }

    @Override
    public void saveRepository(GitRepo repo) {

    }
}
