package com.example.controller.exception;

public class EmailException extends RuntimeException {
    public EmailException() {
        super("Email Invalid");
    }
}
