package com.ubiquity.datastorage.kernel.exceptions;

public class ObjectTypeCannotBeAnIndexException extends UbiquityException {
    public ObjectTypeCannotBeAnIndexException(String fieldName) {
        super("Field \"" + fieldName + "\" is indexed and is of 'OBJECT' type");
    }
}
