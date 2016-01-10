package com.ubiquity.datastorage.kernel.exceptions;


public class NullFactoryException extends UbiquityException {
    public NullFactoryException(Class<?> sourceClass, Class<?> iRecordFactoryClass) {
        super("Class " + sourceClass.getName() + " is being provided a null instance of " + iRecordFactoryClass.getName());
    }
}