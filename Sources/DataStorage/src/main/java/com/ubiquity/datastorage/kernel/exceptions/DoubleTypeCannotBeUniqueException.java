package com.ubiquity.datastorage.kernel.exceptions;

public class DoubleTypeCannotBeUniqueException extends UbiquityException {
    public DoubleTypeCannotBeUniqueException(String fieldName) {
        super("Field \"" + fieldName + "\" is unique and is of 'DOUBLE' type");
    }
}
