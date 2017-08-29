package com.smrecki.common.retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.smrecki.common.base.presenters.BaseContract;
import com.smrecki.payconiqtest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by tomislav on 09/01/2017.
 */

public class RetrofitException extends RuntimeException {
    private final String url;
    private final Response response;
    private final Kind kind;
    private final Retrofit retrofit;

    RetrofitException(String message, String url, Response response, Kind kind, Throwable exception, Retrofit retrofit) {
        super(message, exception);
        this.url = url;
        this.response = response;
        this.kind = kind;
        this.retrofit = retrofit;
    }

    public static RetrofitException httpError(String url, Response response, Retrofit retrofit) {
        String message = response.code() + " " + response.message();
        return new RetrofitException(message, url, response, Kind.HTTP, null, retrofit);
    }

    public static RetrofitException networkError(IOException exception) {
        return new RetrofitException(exception.getMessage(), null, null, Kind.NETWORK, exception, null);
    }

    public static RetrofitException unexpectedError(Throwable exception) {
        return new RetrofitException(exception.getMessage(), null, null, Kind.UNEXPECTED, exception, null);
    }

    /** The request URL which produced the error. */
    public String getUrl() {
        return url;
    }

    /** Response object containing status code, headers, body, etc. */
    public Response getResponse() {
        return response;
    }

    /** The event kind which triggered this error. */
    public Kind getKind() {
        return kind;
    }

    /** The Retrofit this request was executed on */
    public Retrofit getRetrofit() {
        return retrofit;
    }

    public boolean processNetworkError(BaseContract.View view) {
        if (kind == Kind.NETWORK) {
            view.showShortInfo(R.string.network_error);
            return true;
        }
        return false;
    }

    /**
     * HTTP response body converted to specified {@code type}. {@code null} if there is no
     * response or the class conversion failed.
     */
    public <T> T getErrorBodyAs(Class<T> type) throws IOException {
        if (response == null || response.errorBody() == null) {
            throw new IOException("Response error data is empty.");
        }
        try {
            String jsonObjectString = response.errorBody().string();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = new JSONObject(jsonObjectString);
            } catch (JSONException e) {
                e.printStackTrace();
                try {
                    Log.d("array c", jsonObjectString);
                    JSONArray jsonArray = new JSONArray(jsonObjectString);
                    jsonObject = jsonArray.getJSONObject(0);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    try {
                        String errorMess = jsonObjectString;
                        if (errorMess == null || errorMess.isEmpty()) errorMess = response.message();
                        jsonObject.put("message", errorMess);
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
            return new Gson().fromJson(jsonObject.toString(), type);
            /*
            Converter<ResponseBody, T> converter = retrofit.responseBodyConverter(type, new Annotation[0]);
            return converter.convert(response.errorBody());
           */
        } finally {
            response.errorBody().close();
        }
    }

    /** Identifies the event kind which triggered a {@link RetrofitException}. */
    public enum Kind {
        /** An {@link IOException} occurred while communicating to the server. */
        NETWORK,
        /** A non-200 HTTP status code was received from the server. */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }
}
