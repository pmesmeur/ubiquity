package com.ubiquity.core.datastore.exceptions;

public class DoubleTypeCannotBeUniqueException extends UbiquityException {
    public DoubleTypeCannotBeUniqueException(String fieldName) {
        super("Field \"" + fieldName + "\" is unique and is of 'DOUBLE' type");
    }
}
