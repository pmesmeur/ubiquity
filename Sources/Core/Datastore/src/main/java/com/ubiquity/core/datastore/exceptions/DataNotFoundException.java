package com.ubiquity.core.datastore.exceptions;

public class DataNotFoundException extends UbiquityException {
    public DataNotFoundException(String dataName) {
        super("Cannot find data \"" + dataName + "\"");
    }
}
