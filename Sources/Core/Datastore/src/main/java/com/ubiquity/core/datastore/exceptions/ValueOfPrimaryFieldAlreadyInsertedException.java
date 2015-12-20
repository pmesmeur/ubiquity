package com.ubiquity.core.datastore.exceptions;

public class ValueOfPrimaryFieldAlreadyInsertedException extends UbiquityException {
    public ValueOfPrimaryFieldAlreadyInsertedException() {
        super("value already indexed");
    }
}
