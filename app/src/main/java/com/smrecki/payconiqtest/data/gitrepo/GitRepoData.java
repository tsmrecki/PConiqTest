package com.smrecki.payconiqtest.data.gitrepo;

import com.smrecki.payconiqtest.model.GitRepo;

import java.util.List;

import rx.Observable;

/**
 * Created by tomislav on 29/08/2017.
 */

public interface GitRepoData {

    Observable<List<GitRepo>> getRepositories(String username, int page, int perPage);

    Observable<GitRepo> getRepository(long id);

    void saveRepositories(List<GitRepo> repoList);

    void saveRepository(GitRepo repo);
}
