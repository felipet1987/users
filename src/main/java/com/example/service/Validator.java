package com.example.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Validator {

    private static final String EMAIL_PATTERN = "^[\\w\\.-]+@[\\w\\.-]+\\.\\w+$";
    private static final String  PASSWORD_PATTERN = "^(?=(?:[^0-9]*[0-9]){2})(?=.*[A-Z])[a-zA-Z0-9]{8,12}$";


    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public static boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
}
