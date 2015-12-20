package com.ubiquity.core.datastore.exceptions;

public class DataShelfNotFoundException extends UbiquityException {
    public DataShelfNotFoundException(String dataShelfName) {
        super("Cannot find shelf \"" + dataShelfName + "\"");
    }
}
