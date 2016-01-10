package com.ubiquity.datastorage.multithreading;


import com.ubiquity.datastorage.kernel.DataStore;
import com.ubiquity.datastorage.kernel.RecordFactory;
import com.ubiquity.datastorage.kernel.RegistryFactory;
import com.ubiquity.datastorage.kernel.interfaces.IRegistry;
import com.ubiquity.datastorage.multithreading.commands.ICommand;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Scheduler {

    final ExecutorService service;
    private final DataStore dataStore;


    public Scheduler() {
        this.service = Executors.newFixedThreadPool(10);
        RecordFactory recordFactory = new RecordFactory();
        RegistryFactory registryFactory = new RegistryFactory(recordFactory);

        this.dataStore = new DataStore(registryFactory);
    }


    public IRegistry getRegistry(String registry) {
        return dataStore.getRegistry(registry);
    }


    public void execute(final ICommand command) {
        service.execute(new Runnable(){

            @Override
            public void run() {
                command.execute(dataStore);
            }

        });
    }

    public void shutdown() {
        service.shutdown();
    }
}
