package com.smrecki.payconiqtest.data.gitrepo;

import com.raizlabs.android.dbflow.annotation.Collate;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.OrderBy;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.smrecki.payconiqtest.data.database.AppDatabase;
import com.smrecki.payconiqtest.model.GitRepo;
import com.smrecki.payconiqtest.model.GitRepo_Table;
import com.smrecki.payconiqtest.model.User;
import com.smrecki.payconiqtest.model.User_Table;

import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;

/**
 * Created by tomislav on 29/08/2017.
 */
public class GitRepoLocal implements GitRepoData {
    @Override
    public Observable<List<GitRepo>> getRepositories(final String username, final int page, final int perPage) {
        return Observable.fromCallable(new Callable<List<GitRepo>>() {
            @Override
            public List<GitRepo> call() throws Exception {
                return new Select()
                        .from(GitRepo.class)
                        .innerJoin(User.class)
                        .as("user")
                        .on(GitRepo_Table.owner_id.eq(User_Table.id.as("user.id")))
                        .where(User_Table.username.eq(username))
                        .orderBy(OrderBy.fromProperty(GitRepo_Table.fullName).collate(Collate.NOCASE).ascending())
                        .limit(perPage)
                        .offset(page * perPage)
                        .queryList();
            }
        });
    }

    @Override
    public Observable<GitRepo> getRepository(final long id) {
        return Observable.fromCallable(new Callable<GitRepo>() {
            @Override
            public GitRepo call() throws Exception {
                return new Select().from(GitRepo.class).where(GitRepo_Table.id.eq(id)).querySingle();
            }
        });
    }

    @Override
    public void saveRepositories(final List<GitRepo> repoList) {
        FlowManager.getDatabase(AppDatabase.class).beginTransactionAsync(
                new ITransaction() {
                    @Override
                    public void execute(DatabaseWrapper databaseWrapper) {
                        for (GitRepo repo : repoList) {
                            repo.save(databaseWrapper);
                        }
                    }
                }
        ).build().execute();
    }

    @Override
    public void saveRepository(GitRepo repo) {
        repo.save(FlowManager.getWritableDatabase(AppDatabase.class));
    }
}
