package com.example.controller.exception;

public class PasswordException extends RuntimeException {
    public PasswordException() {
        super("Password Invald");
    }
}
