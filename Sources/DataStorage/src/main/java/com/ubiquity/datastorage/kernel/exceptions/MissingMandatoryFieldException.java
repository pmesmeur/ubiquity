package com.ubiquity.datastorage.kernel.exceptions;

public class MissingMandatoryFieldException extends UbiquityException {
    public MissingMandatoryFieldException(String fieldName) {
        super("field \"" + fieldName + "\" is mandatory but is missing");
    }
}
