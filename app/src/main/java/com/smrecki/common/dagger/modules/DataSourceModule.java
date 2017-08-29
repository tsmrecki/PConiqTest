package com.smrecki.common.dagger.modules;

import com.smrecki.common.dagger.qualifiers.LocalData;
import com.smrecki.common.dagger.qualifiers.RemoteData;
import com.smrecki.common.retrofit.ApiInterface;
import com.smrecki.payconiqtest.data.gitrepo.GitRepoData;
import com.smrecki.payconiqtest.data.gitrepo.GitRepoLocal;
import com.smrecki.payconiqtest.data.gitrepo.GitRepoRemote;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tomislav on 29/08/2017.
 */
@Module
public class DataSourceModule {

    @Provides
    @Singleton
    @LocalData
    GitRepoData localUsersData() {
        return new GitRepoLocal();
    }

    @Provides
    @Singleton
    @RemoteData
    GitRepoData remoteUsersData(ApiInterface apiInterface) {
        return new GitRepoRemote(apiInterface);
    }


}
