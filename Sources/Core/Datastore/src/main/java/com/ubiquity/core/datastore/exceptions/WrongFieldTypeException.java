package com.ubiquity.core.datastore.exceptions;

public class WrongFieldTypeException extends UbiquityException {
    public WrongFieldTypeException(String fieldName, Class<?> clazz) {
        super(clazz.toString() + ": invalid type for field \"" + fieldName + "\"");
    }
}
