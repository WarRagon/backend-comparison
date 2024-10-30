package com.example.springboot.util;

public class CustomJWTException extends RuntimeException{
    public CustomJWTException(String msg) {
        super(msg);
    }
}
