package com.smrecki.payconiqtest.data.gitrepo;

import com.smrecki.common.retrofit.ApiInterface;

import javax.inject.Inject;

/**
 * Created by tomislav on 29/08/2017.
 */

public class GitRepoRemote implements GitRepoData {

    ApiInterface mApiInterface;

    @Inject
    public GitRepoRemote(ApiInterface apiInterface) {
        this.mApiInterface = apiInterface;
    }

}
