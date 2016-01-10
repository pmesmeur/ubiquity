package com.ubiquity.datastorage;


import com.ubiquity.datastorage.kernel.DataStore;
import com.ubiquity.datastorage.kernel.RecordFactory;
import com.ubiquity.datastorage.kernel.RegistryFactory;
import com.ubiquity.datastorage.kernel.interfaces.IRecordTemplate;
import com.ubiquity.datastorage.kernel.interfaces.IRegistry;

public class DataStorage {
    private DataStore dataStore;

    public DataStorage() {
        RecordFactory recordFactory = new RecordFactory();
        RegistryFactory registryFactory = new RegistryFactory(recordFactory);

        this.dataStore = new DataStore(registryFactory);
    }

    public IRegistry getRegistry(String registry) {
        return dataStore.getRegistry(registry);
    }

    public void insertRegistry(String registry) {
        dataStore.insertRegistry(registry);
    }

    public void insertRegister(String registry, IRecordTemplate recordTemplate) {
        dataStore.insertRegister(registry, recordTemplate);
    }
}
