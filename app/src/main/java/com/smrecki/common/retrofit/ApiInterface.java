package com.smrecki.common.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tomislav on 29/08/2017.
 */

public class ApiInterface {


    public class GeneralError {

        @SerializedName("message")
        String message;

        public String getMessage() {
            return message;
        }
    }
}
