package com.ubiquity.datastorage.kernel.impl;

import com.google.common.base.Strings;
import com.ubiquity.datastorage.kernel.exceptions.NullFactoryException;
import com.ubiquity.datastorage.kernel.exceptions.RegisterAlreadyExistsException;
import com.ubiquity.datastorage.kernel.exceptions.RegisterNotFoundException;
import com.ubiquity.datastorage.kernel.interfaces.*;

import java.util.HashMap;
import java.util.Map;

public final class Registry implements IRegistry {

    private final IRecordFactory recordFactory;
    private final String identifier;
    private final Map<String, IRegister> registers;
    private final RegistryNotifier registryNotifier;


    private Registry(IRecordFactory recordFactory, String identifier) {
        checkRecordFactory(recordFactory);
        checkIdentifier(identifier);

        this.recordFactory = recordFactory;
        this.identifier = identifier;
        this.registers = new HashMap<>();
        this.registryNotifier = new RegistryNotifier();
    }

    public static Registry create(IRecordFactory recordFactory, String identifier) {
        return new Registry(recordFactory, identifier);
    }

    private static void checkIdentifier(String identifier) {
        assert identifier != null;
        assert !identifier.isEmpty();

        /// TODO: additional checks such as allowed characters...
    }

    private void checkRecordFactory(IRecordFactory recordFactory) {
        if (recordFactory == null) {
            throw new NullFactoryException(Registry.class, IRecordFactory.class);
        }
    }


    public void addListener(IRegistryListener registryListener) {
        registryNotifier.addListener(registryListener);
    }


    public void removeListener(IRegistryListener registryListener) {
        registryNotifier.removeListener(registryListener);
    }


    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public void insertRegister(IRecordTemplate recordTemplate) {
        checkRecordTemplate(recordTemplate);

        String identifier = recordTemplate.getIdentifier();
        if (registers.containsKey(identifier)) {
            throw new RegisterAlreadyExistsException(identifier);
        }

        registers.put(identifier, recordFactory.create(recordTemplate));
        registryNotifier.registerInserted(recordTemplate);
    }


    private void checkRecordTemplate(IRecordTemplate recordTemplate) {
        assert recordTemplate != null;
        assert !Strings.isNullOrEmpty(recordTemplate.getIdentifier());
    }


    @Override
    public IRegister getRegister(String identifier) {
        assert identifier != null;

        IRegister result = registers.get(identifier);
        if (result == null) {
            throw new RegisterNotFoundException(identifier);
        }

        return result;
    }


    @Override
    public IRegister deleteRegister(String identifier) {
        IRegister register = registers.remove(identifier);

        if (register != null) {
            String registerIdentifier = register.getDefinition().getIdentifier();
            registryNotifier.registerDeleted(registerIdentifier);
        } else {
            throw new RegisterNotFoundException(identifier);
        }

        return register;
    }
}
