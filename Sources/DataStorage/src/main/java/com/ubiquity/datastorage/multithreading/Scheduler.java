package com.ubiquity.datastorage.multithreading;


import com.ubiquity.datastorage.kernel.DataStore;
import com.ubiquity.datastorage.kernel.Registry;
import com.ubiquity.datastorage.multithreading.commands.ICommand;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Scheduler {

    final ExecutorService service;
    private final DataStore dataStore;


    public Scheduler() {
        this.service = Executors.newFixedThreadPool(10);
        this.dataStore = new DataStore();
    }


    public Registry getRegistry(String registry) {
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
}
