package com.isf.usersupload.exception;

/**
 * @see com.isf.usersupload.parser.XmlParser checkForDuplicate method
 */
public class SameUsersFoundException extends RuntimeException {
    public SameUsersFoundException(String message) {
        super(message);
    }
}
