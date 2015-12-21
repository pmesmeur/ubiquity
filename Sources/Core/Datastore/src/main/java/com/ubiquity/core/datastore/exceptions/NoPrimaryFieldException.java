package com.ubiquity.core.datastore.exceptions;

public class NoPrimaryFieldException extends UbiquityException {
    public NoPrimaryFieldException(String identifier) {
        super("The data \"" + identifier + "\" is not defined has having any primary field");
    }
}
