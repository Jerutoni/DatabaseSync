package com.isf.departmentupload.utill;

import java.util.Objects;

public class DepartmentKey {

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

    public DepartmentKey(String depCode, String depJob) {
        this.depCode = depCode;
        this.depJob = depJob;
    }

    public DepartmentKey() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentKey that = (DepartmentKey) o;
        return depCode.equals(that.depCode) &&
                depJob.equals(that.depJob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(depCode, depJob);
    }

    @Override
    public String toString() {
        return "DepartmentKey{" +
                "depCode='" + depCode + '\'' +
                ", depJob='" + depJob + '\'' +
                '}';
    }
}
