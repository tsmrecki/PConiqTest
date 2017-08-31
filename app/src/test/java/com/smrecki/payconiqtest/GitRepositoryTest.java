package com.smrecki.payconiqtest;

import static org.mockito.Mockito.when;

import com.smrecki.common.retrofit.ApiInterface;
import com.smrecki.common.retrofit.RetrofitException;
import com.smrecki.payconiqtest.data.gitrepo.GitRepoLocal;
import com.smrecki.payconiqtest.data.gitrepo.GitRepoRemote;
import com.smrecki.payconiqtest.data.gitrepo.GitRepository;
import com.smrecki.payconiqtest.model.GitRepo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

/**
 * Created by tomislav on 31/08/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class GitRepositoryTest {

    private static final GitRepo[] FAKE_REPOS = new GitRepo[]{new GitRepo(1, "Repo1", "Jake/Repo1"), new GitRepo(2, "Repo2", "Jake/Repo2")};

    @Mock
    ApiInterface apiInterface;

    @InjectMocks
    GitRepoRemote sourceRemote;

    @Mock
    GitRepoLocal sourceLocal;


    @Test
    public void testEmptyLocalDataSource_Repository() {
        when(apiInterface.getRepositories("JakeWharton", 1, 2)).thenReturn(
                Observable.just(Arrays.asList(FAKE_REPOS))
        );

        when(sourceLocal.getRepositories("JakeWharton", 0, 2)).thenReturn(
                Observable.<List<GitRepo>>empty()
        );

        TestSubscriber<List<GitRepo>> testSubscriber = new TestSubscriber<>();

        GitRepository repository = new GitRepository(sourceLocal, sourceRemote);

        repository.getRepositories("JakeWharton", 0, 2)
                .subscribe(testSubscriber);

        testSubscriber.assertReceivedOnNext(new ArrayList<List<GitRepo>>(Arrays.asList(new ArrayList<>(Arrays.asList(FAKE_REPOS)))));
        testSubscriber.assertValueCount(1);
    }

    @Test
    public void testEmptyBothSources_Repository() {
        when(apiInterface.getRepositories("JakeWharton", 1, 2)).thenReturn(
                Observable.<List<GitRepo>>empty()
        );

        when(sourceLocal.getRepositories("JakeWharton", 0, 2)).thenReturn(
                Observable.<List<GitRepo>>empty()
        );

        GitRepository repository = new GitRepository(sourceLocal, sourceRemote);


        TestSubscriber<List<GitRepo>> testSubscriber = new TestSubscriber<>();

        repository.getRepositories("JakeWharton", 0, 2)
                .subscribe(testSubscriber);

        testSubscriber.assertReceivedOnNext(new ArrayList<List<GitRepo>>(Arrays.asList(new ArrayList<GitRepo>())));
        testSubscriber.assertValueCount(1);
    }

    @Test
    public void testEmptyRemoteSource_Repository() {
        when(apiInterface.getRepositories("JakeWharton", 1, 2)).thenReturn(Observable.<List<GitRepo>>empty());

        when(sourceLocal.getRepositories("JakeWharton", 0, 2)).thenReturn(
                Observable.just(Arrays.asList(FAKE_REPOS))
        );

        GitRepository repository = new GitRepository(sourceLocal, sourceRemote);


        TestSubscriber<List<GitRepo>> testSubscriber = new TestSubscriber<>();

        repository.getRepositories("JakeWharton", 0, 2)
                .subscribe(testSubscriber);

        testSubscriber.assertReceivedOnNext(new ArrayList<List<GitRepo>>(Arrays.asList(new ArrayList<GitRepo>(Arrays.asList(FAKE_REPOS)))));
        testSubscriber.assertValueCount(1);
    }

    @Test
    public void testNetworkError_Repository() {
        Exception FAKE_NETWORK_ERROR = RetrofitException.networkError(new IOException("FAKE_NETWORK_ERROR"));

        when(apiInterface.getRepositories("JakeWharton", 1, 2)).thenReturn(Observable.<List<GitRepo>>error(FAKE_NETWORK_ERROR));

        when(sourceLocal.getRepositories("JakeWharton", 0, 2)).thenReturn(
                Observable.just(Arrays.asList(FAKE_REPOS))
        );

        GitRepository repository = new GitRepository(sourceLocal, sourceRemote);


        TestSubscriber<List<GitRepo>> testSubscriber = new TestSubscriber<>();

        repository.getRepositories("JakeWharton", 0, 2)
                .subscribe(testSubscriber);

        testSubscriber.assertReceivedOnNext(new ArrayList<List<GitRepo>>(Arrays.asList(new ArrayList<GitRepo>(Arrays.asList(FAKE_REPOS)))));
        testSubscriber.assertValueCount(1);

        testSubscriber.assertError(FAKE_NETWORK_ERROR);
    }


    @Test
    public void testNetworkError_EmptyLocal_Repository() {
        Exception FAKE_NETWORK_ERROR = RetrofitException.networkError(new IOException("FAKE_NETWORK_ERROR"));

        when(apiInterface.getRepositories("JakeWharton", 1, 2)).thenReturn(Observable.<List<GitRepo>>error(FAKE_NETWORK_ERROR));

        when(sourceLocal.getRepositories("JakeWharton", 0, 2)).thenReturn(
                Observable.<List<GitRepo>>empty()
        );

        GitRepository repository = new GitRepository(sourceLocal, sourceRemote);


        TestSubscriber<List<GitRepo>> testSubscriber = new TestSubscriber<>();

        repository.getRepositories("JakeWharton", 0, 2)
                .subscribe(testSubscriber);

        testSubscriber.assertReceivedOnNext(new ArrayList<List<GitRepo>>());
        testSubscriber.assertValueCount(0);

        testSubscriber.assertError(FAKE_NETWORK_ERROR);
    }
}
