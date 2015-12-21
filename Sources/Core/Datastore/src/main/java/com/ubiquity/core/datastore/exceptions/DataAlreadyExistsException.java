package com.ubiquity.core.datastore.exceptions;

public class DataAlreadyExistsException extends UbiquityException {
    public DataAlreadyExistsException(String identifier) {
        super("Data-type of identifier \"" + identifier + "\" already exists");
    }
}
