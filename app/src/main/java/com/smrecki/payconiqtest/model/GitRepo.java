package com.smrecki.payconiqtest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tomislav on 29/08/2017.
 */

public class GitRepo {

    @SerializedName("id")
    long id;

    @SerializedName("name")
    String name;

    @SerializedName("full_name")
    String fullName;

    @SerializedName("owner")
    User owner;

    @SerializedName("private")
    boolean isPrivate;

    @SerializedName("description")
    String description;

    @SerializedName("homepage")
    String homepageUrl;


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
