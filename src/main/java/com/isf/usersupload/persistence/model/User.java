package com.isf.usersupload.persistence.model;

import java.util.Objects;

public class User {
    private int id;
    private String depCode;
    private String depJob;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getDepJob() {
        return depJob;
    }

    public void setDepJob(String depJob) {
        this.depJob = depJob;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User(int id, String depCode, String depJob, String description) {
        this.id = id;
        this.depCode = depCode;
        this.depJob = depJob;
        this.description = description;
    }

    public User(String depCode, String depJob, String description) {
        this.depCode = depCode;
        this.depJob = depJob;
        this.description = description;
    }

    public User() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return depCode.equals(that.depCode) &&
                depJob.equals(that.depJob) &&
                description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(depCode, depJob, description);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", depCode='" + depCode + '\'' +
                ", depJob='" + depJob + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
