package com.ubiquity.datastorage.kernel.exceptions;

public class RegisterNotFoundException extends UbiquityException {
    public RegisterNotFoundException(String registerName) {
        super("Cannot find register \"" + registerName + "\"");
    }
}
