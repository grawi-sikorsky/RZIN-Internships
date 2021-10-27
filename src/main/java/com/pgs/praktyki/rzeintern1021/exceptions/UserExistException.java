package com.pgs.praktyki.rzeintern1021.exceptions;

public class UserExistException extends RuntimeException {
    public UserExistException(String msg) {
        super(msg);
    }
}
