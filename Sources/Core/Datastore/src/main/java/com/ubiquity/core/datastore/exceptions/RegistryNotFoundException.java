package com.ubiquity.core.datastore.exceptions;

public class RegistryNotFoundException extends UbiquityException {
    public RegistryNotFoundException(String RegistryName) {
        super("Cannot find registry \"" + RegistryName + "\"");
    }
}
