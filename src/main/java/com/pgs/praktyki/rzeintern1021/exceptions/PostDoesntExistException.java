package com.pgs.praktyki.rzeintern1021.exceptions;

public class PostDoesntExistException extends RuntimeException {
    public PostDoesntExistException(String msg) {
        super(msg);
    }
}
