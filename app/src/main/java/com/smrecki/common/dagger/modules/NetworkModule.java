package com.smrecki.common.dagger.modules;

import com.smrecki.payconiqtest.BuildConfig;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by tomislav on 29/08/17.
 */

@Module(includes = ContextModule.class)
public class NetworkModule {

    @Provides
    @Singleton
    public Interceptor loggingInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Accept", "application/json")
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
    }

    @Provides
    @Singleton
    public OkHttpClient okHttpClient(Interceptor loggingInterceptor) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(interceptor);
        }

        client.addInterceptor(loggingInterceptor);

        return client.build();
    }

}
