package com.ubiquity.datastorage.kernel.interfaces;

import com.ubiquity.datastorage.kernel.IRecordTemplate;


public interface IRegistry {
    String getIdentifier();
    void insertRegister(IRecordTemplate recordTemplate);
    IRegister getRegister(String identifier);
}
