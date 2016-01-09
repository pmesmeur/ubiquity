package com.ubiquity.datastorage.kernel;


public interface IRegistry {
    String getIdentifier();
    void insertRegister(IRecordTemplate recordTemplate);
    Register getRegister(String identifier);
}
