package com.isf.departmentupload.exception;

/**
 * @see com.isf.departmentupload.parser.XmlParser checkForDuplicate method
 */
public class SameDepartmentsFoundException extends RuntimeException {
    public SameDepartmentsFoundException(String message) {
        super(message);
    }
}
