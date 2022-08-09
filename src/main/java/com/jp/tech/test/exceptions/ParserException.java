package com.jp.tech.test.exceptions;

public class ParserException extends Exception{
    private String message;
    public ParserException(String message){
        this.message=message;
    }

}
