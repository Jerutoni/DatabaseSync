package com.isf.departmentupload.exception;

public class SameDepartmentsFoundException extends RuntimeException {
    public SameDepartmentsFoundException(String message) {
        super(message);
    }
}
