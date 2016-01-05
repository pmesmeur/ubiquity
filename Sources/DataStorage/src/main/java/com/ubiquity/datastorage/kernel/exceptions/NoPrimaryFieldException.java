package com.ubiquity.datastorage.kernel.exceptions;

public class NoPrimaryFieldException extends UbiquityException {
    public NoPrimaryFieldException(String identifier) {
        super("The register \"" + identifier + "\" is not defined has having any primary field");
    }
}
