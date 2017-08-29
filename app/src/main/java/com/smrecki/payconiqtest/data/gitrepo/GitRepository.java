package com.smrecki.payconiqtest.data.gitrepo;

import com.smrecki.common.dagger.qualifiers.LocalData;
import com.smrecki.common.dagger.qualifiers.RemoteData;

import javax.inject.Inject;

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
}
