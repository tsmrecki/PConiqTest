package com.smrecki.payconiqtest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tomislav on 29/08/2017.
 */

public class User {

    @SerializedName("id")
    long id;

    @SerializedName("login")
    String username;

    @SerializedName("avatar_url")
    String avatarUrl;


    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
