package com.ubiquity.datastorage.kernel;

import com.ubiquity.datastorage.kernel.interfaces.IRegistry;
import com.ubiquity.datastorage.kernel.interfaces.IRegistryFactory;


public class RegistryFactory implements IRegistryFactory {

    @Override
    public IRegistry createRegistry(String identifier) {
        return Registry.create(identifier);
    }

}
