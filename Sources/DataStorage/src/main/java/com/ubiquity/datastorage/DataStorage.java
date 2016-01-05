package com.ubiquity.datastorage;


import com.ubiquity.datastorage.kernel.DataStore;
import com.ubiquity.datastorage.kernel.IRecordTemplate;
import com.ubiquity.datastorage.kernel.Registry;

public class DataStorage {
    private DataStore dataStore;

    public DataStorage() {
        this.dataStore = new DataStore();
    }

    public Registry getRegistry(String registry) {
        return dataStore.getRegistry(registry);
    }

    public void insertRegistry(String registry) {
        dataStore.insertRegistry(registry);
    }

    public void insertRegister(String registry, IRecordTemplate recordTemplate) {
        dataStore.insertRegister(registry, recordTemplate);
    }
}
