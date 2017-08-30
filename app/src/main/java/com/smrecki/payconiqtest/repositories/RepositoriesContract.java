package com.smrecki.payconiqtest.repositories;

import com.smrecki.common.base.presenters.BaseContract;
import com.smrecki.common.dagger.scopes.ActivityScope;
import com.smrecki.payconiqtest.model.GitRepo;

import java.util.List;

/**
 * Created by tomislav on 30/08/2017.
 */

public interface RepositoriesContract {

    interface View extends BaseContract.View {
        void setRepositories(List<GitRepo> gitRepoList);

        void addRepositories(List<GitRepo> gitRepoList);

        void showPaginationLoader(boolean showLoader);
    }

    @ActivityScope
    interface Presenter<V extends View> extends BaseContract.Presenter<V> {
        void setUsername(String username);

        void refresh();

        void loadMore();
    }

}
