package com.smrecki.payconiqtest.model;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.smrecki.payconiqtest.data.database.AppDatabase;

import java.util.Date;

/**
 * Created by tomislav on 29/08/2017.
 */
@Table(database = AppDatabase.class)
public class GitRepo extends BaseModel {

    @PrimaryKey
    @SerializedName("id")
    long id;

    @Column
    @SerializedName("name")
    String name;

    @Column
    @SerializedName("full_name")
    String fullName;

    @ForeignKey(saveForeignKeyModel = true)
    @SerializedName("owner")
    User owner;

    @Column
    @SerializedName("private")
    boolean isPrivate;

    @Column
    @SerializedName("description")
    String description;

    @Column
    @SerializedName("homepage")
    String homepageUrl;

    @Column
    @SerializedName("created_at")
    Date createdAt;

    @Column
    @SerializedName("updated_at")
    Date updatedAt;


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public User getOwner() {
        return owner;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getDescription() {
        return description;
    }

    public String getHomepageUrl() {
        return homepageUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GitRepo)) return false;

        GitRepo gitRepo = (GitRepo) o;

        return id == gitRepo.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
