package com.smrecki.common.dagger.components;

import android.content.Context;

import com.smrecki.common.dagger.modules.ApiServiceModule;
import com.smrecki.common.dagger.modules.DataSourceModule;
import com.smrecki.common.retrofit.ApiInterface;
import com.smrecki.payconiqtest.data.gitrepo.GitRepository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ianic on 08/12/16.
 */

@Singleton
@Component(modules = {ApiServiceModule.class, DataSourceModule.class})
public interface ApplicationComponent {

    Context getContext();

    ApiInterface getApiInterface();

    GitRepository gitRepository();

}