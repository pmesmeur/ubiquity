package com.ubiquity.datastorage.kernel.interfaces;


import com.ubiquity.datastorage.kernel.IRecordTemplate;
import com.ubiquity.datastorage.kernel.Register;

public interface IRegistry {
    String getIdentifier();
    void insertRegister(IRecordTemplate recordTemplate);
    Register getRegister(String identifier);
}
