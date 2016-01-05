package com.ubiquity.datastorage.multithreading.commands;

import com.ubiquity.datastorage.kernel.DataStore;
import com.ubiquity.datastorage.kernel.IRecordTemplate;


public class RegisterInserter implements ICommand {

    private final String registry;
    private final IRecordTemplate recordTemplate;

    public RegisterInserter(String registry, IRecordTemplate recordTemplate) {
        this.registry = registry;
        this.recordTemplate = recordTemplate;
    }

    @Override
    public void execute(DataStore dataStore) {
        dataStore.insertRegister(registry, recordTemplate);
    }
}
