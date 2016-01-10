package com.ubiquity.datastorage.kernel.impl;


import com.ubiquity.datastorage.kernel.interfaces.IDataStoreListener;

import java.util.HashSet;
import java.util.Set;

public class DataStoreNotifier {
    protected final Set<IDataStoreListener> listeners;


    public DataStoreNotifier() {
        listeners = new HashSet<IDataStoreListener>(); /// TODO:concurrence
    }


    public void addListener(IDataStoreListener dataStoreListener) {
        assert dataStoreListener != null;
        listeners.add(dataStoreListener);
    }


    public void removeListener(IDataStoreListener dataStoreListener) {
        assert dataStoreListener != null;
        listeners.remove(dataStoreListener);
    }


    public void registryInserted(final String identifier) {
        listeners.stream().forEach(p -> p.onRegistryInserted(identifier));
    }


    public void registryDeleted(final String identifier) {
        listeners.stream().forEach(p -> p.onRegistryDeleted(identifier));
    }

    public int getNbListeners() {
        return listeners.size();
    }
}
