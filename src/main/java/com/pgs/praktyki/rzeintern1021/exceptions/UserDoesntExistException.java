package com.pgs.praktyki.rzeintern1021.exceptions;

public class UserDoesntExistException extends RuntimeException {
    public UserDoesntExistException(String msg) {
        super(msg);
    }
}
