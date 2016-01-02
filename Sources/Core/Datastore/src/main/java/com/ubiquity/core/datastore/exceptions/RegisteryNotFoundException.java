package com.ubiquity.core.datastore.exceptions;

public class RegisteryNotFoundException extends UbiquityException {
    public RegisteryNotFoundException(String RegisteryName) {
        super("Cannot find registery \"" + RegisteryName + "\"");
    }
}
