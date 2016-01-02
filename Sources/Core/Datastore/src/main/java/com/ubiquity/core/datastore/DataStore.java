package com.ubiquity.core.datastore;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.base.Strings;
import com.ubiquity.core.datastore.exceptions.RegisteryNotFoundException;


public class DataStore {

    private final Map<String, Registry> Registeries = new ConcurrentHashMap<String, Registry>();


    public Registry insertRegistery(String identifier) {
        Registry Registery = Registry.create(identifier);
        Registeries.put(identifier, Registery);
        return Registery;
    }


    public Registry getRegistery(String identifier) {
        Registry Registery = Registeries.get(identifier);
        if (Registery == null) {
            throw new RegisteryNotFoundException(identifier);
        }
        return Registery;
    }


    public Set<String> getAllRegisteriesId() {
        return Registeries.keySet();
    }


    public void insertRegister(String registery, IRecordTemplate recordTemplate) {
        assert!Strings.isNullOrEmpty(registery);
        assert recordTemplate != null;

        Registry Registery = getRegistery(registery);
        Registery.insertRegister(recordTemplate);
    }
}
