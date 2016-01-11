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


    public void addListener(IRegistryListener registryListener) {
        assert registryListener != null;
        listeners.add(registryListener);
    }


    public void removeListener(IRegistryListener registryListener) {
        assert registryListener != null;
        listeners.remove(registryListener);
    }


    public void registerInserted(IRecordTemplate recordTemplate) {
        listeners.stream().forEach(p -> p.onRegisterInserted(recordTemplate));
    }


    public void registerDeleted(String identifier) {
        listeners.stream().forEach(p -> p.onRegisterDeleted(identifier));
    }

}
