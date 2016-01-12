package com.ubiquity.datastorage.kernel.interfaces;


public interface IRegistryListener {
    void onRegisterInserted(IRecordTemplate recordTemplate);

    void onRegisterDeleted(String registerId);
}
