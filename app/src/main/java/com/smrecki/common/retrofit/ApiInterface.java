package com.smrecki.common.retrofit;

import com.google.gson.annotations.SerializedName;
import com.smrecki.payconiqtest.model.GitRepo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by tomislav on 29/08/2017.
 */

public interface ApiInterface {

    @GET("/users/{username}/repos")
    Observable<List<GitRepo>> getRepositories(@Path("username") String username, @Query("page") int page, @Query("per_page") int perPage);

    @GET("/repositories/{id}")
    Observable<GitRepo> getRepository(@Path("id") long id);

    class GeneralError {

        @SerializedName("message")
        String message;

        public String getMessage() {
            return message;
        }
    }
}
