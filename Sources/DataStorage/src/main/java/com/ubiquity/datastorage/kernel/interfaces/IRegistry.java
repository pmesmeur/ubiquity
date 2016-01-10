package com.ubiquity.datastorage.kernel.interfaces;


public interface IRegistry {
    String getIdentifier();
    void insertRegister(IRecordTemplate recordTemplate);
    IRegister getRegister(String identifier);
}
