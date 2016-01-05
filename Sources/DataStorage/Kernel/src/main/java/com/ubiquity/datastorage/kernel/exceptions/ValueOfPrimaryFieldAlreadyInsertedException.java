package com.ubiquity.datastorage.kernel.exceptions;

public class ValueOfPrimaryFieldAlreadyInsertedException extends UbiquityException {
    public ValueOfPrimaryFieldAlreadyInsertedException() {
        super("value already indexed");
    }
}
