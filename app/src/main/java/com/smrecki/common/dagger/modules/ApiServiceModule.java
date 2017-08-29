package com.smrecki.common.dagger.modules;

import com.google.gson.Gson;
import com.smrecki.common.retrofit.ApiInterface;
import com.smrecki.common.retrofit.RxErrorHandlingCallAdapterFactory;
import com.smrecki.payconiqtest.BuildConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by tomislav on 29/08/17.
 */

@Module(includes = NetworkModule.class)
public class ApiServiceModule {

    @Provides
    @Singleton
    public ApiInterface apiService(Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);
    }

    @Provides
    @Singleton
    public Retrofit retrofit(OkHttpClient client) {

        Gson gson = new Gson();

        return new Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.BASE_API_URL)
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }
}
