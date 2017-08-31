package com.smrecki.payconiqtest.data.gitrepo;

import com.smrecki.common.dagger.qualifiers.LocalData;
import com.smrecki.common.dagger.qualifiers.RemoteData;
import com.smrecki.payconiqtest.model.GitRepo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by tomislav on 29/08/2017.
 */

public class GitRepository implements GitRepoData {

    private GitRepoData localData;
    private GitRepoData remoteData;

    @Inject
    public GitRepository(@LocalData GitRepoData localData, @RemoteData GitRepoData remoteData) {
        this.localData = localData;
        this.remoteData = remoteData;
    }

    @Override
    public Observable<List<GitRepo>> getRepositories(final String username, final int page, final int perPage) {
        return Observable.concatDelayError(
                loadRemoteRepos(username, page, perPage),
                localData.getRepositories(username, page, perPage))
                .filter(new Func1<List<GitRepo>, Boolean>() {
                    boolean delivered = false;
                    @Override
                    public Boolean call(List<GitRepo> gitRepos) {
                        if (!gitRepos.isEmpty() && !delivered) {
                            delivered = true;
                            return true;
                        }
                        return false;
                    }
                }).defaultIfEmpty(new ArrayList<GitRepo>());
    }

    private Observable<List<GitRepo>> loadRemoteRepos(String username, int page, int perPage) {
        return remoteData.getRepositories(username, page, perPage)
                .flatMap(new Func1<List<GitRepo>, Observable<List<GitRepo>>>() {
                    @Override
                    public Observable<List<GitRepo>> call(List<GitRepo> repoList) {
                        saveRepositories(repoList);
                        return Observable.just(repoList);
                    }
                });
    }

    @Override
    public Observable<GitRepo> getRepository(long id) {
        return Observable.concatDelayError(
                loadRemoteRepository(id),
                localData.getRepository(id)
        ).filter(new Func1<GitRepo, Boolean>() {
            @Override
            public Boolean call(GitRepo gitRepo) {
                return gitRepo != null;
            }
        }).first();
    }

    private Observable<GitRepo> loadRemoteRepository(long id) {
        return remoteData.getRepository(id).flatMap(new Func1<GitRepo, Observable<GitRepo>>() {
            @Override
            public Observable<GitRepo> call(GitRepo gitRepo) {
                saveRepository(gitRepo);
                return Observable.just(gitRepo);
            }
        });
    }

    @Override
    public void saveRepositories(List<GitRepo> repoList) {
        localData.saveRepositories(repoList);
    }

    @Override
    public void saveRepository(GitRepo repo) {
        localData.saveRepository(repo);
    }
}
