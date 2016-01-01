package com.ubiquity.core.datastore.exceptions;

public class RecordDoesNotFitTemplateException extends UbiquityException {
    public RecordDoesNotFitTemplateException() {
        super("some fields were provided bus did not fit the data definition");
    }
}
