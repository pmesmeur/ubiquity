package com.ubiquity.datastorage.multithreading.commands;


import com.ubiquity.datastorage.kernel.DataStore;

public class RegistryInserter implements ICommand {
    private String registry;

    public RegistryInserter(String registry) {
        this.registry = registry;
    }

    @Override
    public void execute(DataStore dataStore) {
        dataStore.insertRegistry(registry);
    }
}
