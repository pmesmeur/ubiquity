package com.ubiquity.datastorage.kernel.impl;


import com.ubiquity.datastorage.kernel.interfaces.IRecordTemplate;
import com.ubiquity.datastorage.kernel.interfaces.IRegistryListener;

import java.util.HashSet;
import java.util.Set;

public class RegistryNotifier {
    protected final Set<IRegistryListener> listeners;


    public RegistryNotifier() {
        listeners = new HashSet<IRegistryListener>(); /// TODO:concurrence
    }


    public void addListener(IRegistryListener dataStoreListener) {
        assert dataStoreListener != null;
        listeners.add(dataStoreListener);
    }


    public void removeListener(IRegistryListener dataStoreListener) {
        assert dataStoreListener != null;
        listeners.remove(dataStoreListener);
    }


    public void registerInserted(IRecordTemplate recordTemplate) {
        listeners.stream().forEach(p -> p.onRegisterInserted(recordTemplate));
    }


    public void registerDeleted(String identifier) {
        listeners.stream().forEach(p -> p.onRegisterDeleted(identifier));
    }

}
