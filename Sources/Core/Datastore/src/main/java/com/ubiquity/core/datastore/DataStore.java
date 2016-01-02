package com.ubiquity.core.datastore;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.base.Strings;
import com.ubiquity.core.datastore.exceptions.RegistryNotFoundException;


public class DataStore {

    private final Map<String, Registry> registries = new ConcurrentHashMap<String, Registry>();


    public Registry insertRegistry(String identifier) {
        Registry registry = Registry.create(identifier);
        registries.put(identifier, registry);
        return registry;
    }


    public Registry getRegistry(String identifier) {
        Registry registry = registries.get(identifier);
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

        Registry registry = getRegistry(registryId);
        registry.insertRegister(recordTemplate);
    }
}
