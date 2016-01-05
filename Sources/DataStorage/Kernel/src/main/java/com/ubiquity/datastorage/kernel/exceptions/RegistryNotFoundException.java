package com.ubiquity.datastorage.kernel.exceptions;

public class RegistryNotFoundException extends UbiquityException {
    public RegistryNotFoundException(String RegistryName) {
        super("Cannot find registry \"" + RegistryName + "\"");
    }
}
