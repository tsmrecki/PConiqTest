package com.smrecki.common.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tomislav on 09/01/2017.
 */

public class ErrorsContainer<T> {

    @SerializedName("errors")
    T errors;

}
