package com.isf.usersupload.utill;

import java.util.Objects;

public class UserKey {

    private String depCode;
    private String depJob;

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

    public UserKey(String depCode, String depJob) {
        this.depCode = depCode;
        this.depJob = depJob;
    }

    public UserKey() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserKey that = (UserKey) o;
        return depCode.equals(that.depCode) &&
                depJob.equals(that.depJob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(depCode, depJob);
    }

    @Override
    public String toString() {
        return "UserKey{" +
                "depCode='" + depCode + '\'' +
                ", depJob='" + depJob + '\'' +
                '}';
    }
}
