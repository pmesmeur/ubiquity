package com.ubiquity.datastorage.kernel.exceptions;

public class RecordDoesNotFitTemplateException extends UbiquityException {
    public RecordDoesNotFitTemplateException() {
        super("some fields were provided bus did not fit the record template");
    }
}
