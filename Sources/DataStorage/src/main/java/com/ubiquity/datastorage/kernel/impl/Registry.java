package com.ubiquity.datastorage.kernel.impl;

import com.google.common.base.Strings;
import com.ubiquity.datastorage.kernel.exceptions.NullFactoryException;
import com.ubiquity.datastorage.kernel.exceptions.RegisterAlreadyExistsException;
import com.ubiquity.datastorage.kernel.exceptions.RegisterNotFoundException;
import com.ubiquity.datastorage.kernel.interfaces.IRecordFactory;
import com.ubiquity.datastorage.kernel.interfaces.IRecordTemplate;
import com.ubiquity.datastorage.kernel.interfaces.IRegister;
import com.ubiquity.datastorage.kernel.interfaces.IRegistry;

import java.util.HashMap;
import java.util.Map;

public final class Registry implements IRegistry {

    private final IRecordFactory recordFactory;
    private final String identifier;
    private final Map<String, IRegister> registers;


    public static Registry create(IRecordFactory recordFactory, String identifier) {
        return new Registry(recordFactory, identifier);
    }

    private Registry(IRecordFactory recordFactory, String identifier) {
        checkRecordFactory(recordFactory);
        checkIdentifier(identifier);

        this.recordFactory = recordFactory;
        this.identifier = identifier;
        this.registers = new HashMap<String, IRegister>();
    }

    private void checkRecordFactory(IRecordFactory recordFactory) {
        if (recordFactory == null) {
            throw new NullFactoryException(Registry.class, IRecordFactory.class);
        }
    }

    private static void checkIdentifier(String identifier) {
        assert identifier != null;
        assert!identifier.isEmpty();

        /// TODO: additional checks such as allowed characters...
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

    private void checkRecordTemplate(IRecordTemplate recordTemplate) {
        assert recordTemplate != null;
        assert!Strings.isNullOrEmpty(recordTemplate.getIdentifier());
    }

}
