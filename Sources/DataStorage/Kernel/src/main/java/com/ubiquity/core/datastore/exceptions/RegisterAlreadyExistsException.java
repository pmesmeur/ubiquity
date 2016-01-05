package com.ubiquity.core.datastore.exceptions;

public class RegisterAlreadyExistsException extends UbiquityException {
    public RegisterAlreadyExistsException(String identifier) {
        super("Data-type of identifier \"" + identifier + "\" already exists");
    }
}
