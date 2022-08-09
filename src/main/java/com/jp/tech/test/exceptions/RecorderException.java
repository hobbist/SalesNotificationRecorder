package com.jp.tech.test.exceptions;

public class RecorderException extends Exception {
    private String message;
    public RecorderException(String message){
        this.message=message;
    }
}
