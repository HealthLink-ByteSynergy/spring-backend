package com.example.demo.exception;

public class CustomExcpetion extends Exception{
    public CustomExcpetion() {
    }

    public CustomExcpetion(String message) {
        super(message);
    }

    public CustomExcpetion(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomExcpetion(Throwable cause) {
        super(cause);
    }

    public CustomExcpetion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

