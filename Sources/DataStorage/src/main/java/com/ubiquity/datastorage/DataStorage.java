package com.ubiquity.datastorage;


import com.ubiquity.datastorage.kernel.DataStore;
import com.ubiquity.datastorage.kernel.IRecordTemplate;
import com.ubiquity.datastorage.kernel.Registry;
import com.ubiquity.datastorage.multithreading.Scheduler;
import com.ubiquity.datastorage.multithreading.commands.RegisterInserter;
import com.ubiquity.datastorage.multithreading.commands.RegistryInserter;

public class DataStorage {
    private Scheduler scheduler;

    public DataStorage() {
        this.scheduler = new Scheduler();
    }

    public Registry getRegistry(String registry) {
        return scheduler.getRegistry(registry);
    }

    public void insertRegistry(String registry) {
        scheduler.execute(new RegistryInserter(registry));
    }

    public void insertRegister(String registry, IRecordTemplate recordTemplate) {
        scheduler.execute(new RegisterInserter(registry, recordTemplate));
    }
}
