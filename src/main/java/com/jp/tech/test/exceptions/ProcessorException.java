package com.jp.tech.test.exceptions;

public class ProcessorException extends Exception {
    private String message;
    public ProcessorException(String message){
        this.message=message;
    }
}
