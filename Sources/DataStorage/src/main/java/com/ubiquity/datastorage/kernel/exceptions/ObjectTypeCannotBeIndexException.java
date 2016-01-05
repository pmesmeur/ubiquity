package com.ubiquity.datastorage.kernel.exceptions;

public class ObjectTypeCannotBeIndexException extends UbiquityException {
    public ObjectTypeCannotBeIndexException(String fieldName) {
        super("Field \"" + fieldName + "\" is indexed and is of 'OBJECT' type");
    }
}
