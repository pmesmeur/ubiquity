package com.ubiquity.datastorage.kernel;

import com.ubiquity.datastorage.kernel.interfaces.IRegistry;
import com.ubiquity.datastorage.kernel.interfaces.IRegistryFactory;


public class RegistryFactory implements IRegistryFactory {

    private final RecordFactory recordFactory;

    public RegistryFactory(RecordFactory recordFactory) {
        this.recordFactory = recordFactory;
    }

    @Override
    public IRegistry createRegistry(String identifier) {
        return Registry.create(recordFactory, identifier);
    }

}
