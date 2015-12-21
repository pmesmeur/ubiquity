package com.ubiquity.core.datastore.exceptions;

public class EntryDoesNotFitDataDefinitionException extends UbiquityException {
    public EntryDoesNotFitDataDefinitionException() {
        super("some fields were provided bus did not fit the data definition");
    }
}
