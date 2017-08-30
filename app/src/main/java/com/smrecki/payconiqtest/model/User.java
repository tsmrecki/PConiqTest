package com.smrecki.payconiqtest.model;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.smrecki.payconiqtest.data.database.AppDatabase;

/**
 * Created by tomislav on 29/08/2017.
 */
@Table(database = AppDatabase.class)
public class User extends BaseModel {

    @PrimaryKey
    @SerializedName("id")
    long id;

    @Column
    @SerializedName("login")
    String username;

    @Column
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
