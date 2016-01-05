package com.ubiquity.datastorage.multithreading.commands;


import com.ubiquity.datastorage.kernel.DataStore;

public interface ICommand {
    void execute(DataStore dataStore);
}
