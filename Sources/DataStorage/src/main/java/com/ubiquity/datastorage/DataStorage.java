package com.ubiquity.datastorage;


import com.ubiquity.datastorage.kernel.interfaces.IRecordTemplate;
import com.ubiquity.datastorage.multithreading.Scheduler;
import com.ubiquity.datastorage.multithreading.commands.RegisterInserter;
import com.ubiquity.datastorage.multithreading.commands.RegistryInserter;
import com.ubiquity.datastorage.kernel.interfaces.IRegistry;


public class DataStorage {
    private Scheduler scheduler;

    public DataStorage() {
        this.scheduler = new Scheduler();
    }

    public IRegistry getRegistry(String registry) {
        return scheduler.getRegistry(registry);
    }

    public void insertRegistry(String registry) {
        scheduler.execute(new RegistryInserter(registry));
    }

    public void insertRegister(String registry, IRecordTemplate recordTemplate) {
        scheduler.execute(new RegisterInserter(registry, recordTemplate));
    }
}
