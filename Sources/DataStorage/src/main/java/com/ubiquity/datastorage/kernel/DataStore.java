package com.ubiquity.datastorage.kernel;

import com.google.common.base.Strings;
import com.ubiquity.datastorage.kernel.exceptions.NullFactoryException;
import com.ubiquity.datastorage.kernel.exceptions.RegistryNotFoundException;
import com.ubiquity.datastorage.kernel.impl.DataStoreNotifier;
import com.ubiquity.datastorage.kernel.interfaces.IDataStoreListener;
import com.ubiquity.datastorage.kernel.interfaces.IRecordTemplate;
import com.ubiquity.datastorage.kernel.interfaces.IRegistry;
import com.ubiquity.datastorage.kernel.interfaces.IRegistryFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class DataStore {

    protected final DataStoreNotifier dataStoreNotifier;
    private final IRegistryFactory registryFactory;
    private final Map<String, IRegistry> registries;

    public DataStore(IRegistryFactory registryFactory) {
        checkRegistryFactory(registryFactory);
        this.registryFactory = registryFactory;
        registries = new ConcurrentHashMap<>();
        dataStoreNotifier = new DataStoreNotifier();
    }


    public void addListener(IDataStoreListener dataStoreListener) {
        dataStoreNotifier.addListener(dataStoreListener);
    }


    public void removeListener(IDataStoreListener dataStoreListener) {
        dataStoreNotifier.removeListener(dataStoreListener);
    }


    private void checkRegistryFactory(IRegistryFactory registryFactory) {
        if (registryFactory == null) {
            throw new NullFactoryException(DataStore.class, IRegistryFactory.class);
        }
    }


    public IRegistry insertRegistry(String identifier) {
        IRegistry registry = registryFactory.createRegistry(identifier);
        registries.put(identifier, registry);
        dataStoreNotifier.registryInserted(identifier);
        return registry;
    }


    public IRegistry getRegistry(String identifier) {
        IRegistry registry = registries.get(identifier);
        if (registry == null) {
            throw new RegistryNotFoundException(identifier);
        }
        return registry;
    }


    public Set<String> getAllRegistriesId() {
        return registries.keySet();
    }


    public void insertRegister(String registryId, IRecordTemplate recordTemplate) {
        assert!Strings.isNullOrEmpty(registryId);
        assert recordTemplate != null;

        IRegistry registry = getRegistry(registryId);
        registry.insertRegister(recordTemplate);
    }

    protected int getNbListeners() {
        return dataStoreNotifier.getNbListeners();
    }
}
