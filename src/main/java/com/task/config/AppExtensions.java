package com.task.config;

import java.util.regex.Pattern;

public class AppExtensions {
    public static boolean isValidMobileNumber(String mobileNumber) {
        return Pattern.compile("^[0-9]{10}$").matcher(mobileNumber).matches();
    }
}
