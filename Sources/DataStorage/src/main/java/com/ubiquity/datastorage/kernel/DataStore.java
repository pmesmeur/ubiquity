package com.ubiquity.datastorage.kernel;

import com.google.common.base.Strings;
import com.ubiquity.datastorage.kernel.exceptions.RegistryNotFoundException;
import com.ubiquity.datastorage.kernel.interfaces.IRegistry;
import com.ubiquity.datastorage.kernel.interfaces.IRegistryFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class DataStore {

    private final Map<String, IRegistry> registries = new ConcurrentHashMap<String, IRegistry>();
    private final IRegistryFactory registryFactory;

    public DataStore() {
        this.registryFactory = new RegistryFactory();
    }

    public IRegistry insertRegistry(String identifier) {
        IRegistry registry = registryFactory.createRegistry(identifier);
        registries.put(identifier, registry);
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
}
