package com.ubiquity.core.datastore.exceptions;

public class IndexingObjectException extends UbiquityException {
    public IndexingObjectException(String fieldName) {
        super("Field \"" + fieldName + "\" is indexed and is of 'OBJECT' type");
    }
}
