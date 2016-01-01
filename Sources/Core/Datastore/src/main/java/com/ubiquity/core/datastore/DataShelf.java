package com.ubiquity.core.datastore;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Strings;
import com.ubiquity.core.datastore.exceptions.RegisterAlreadyExistsException;
import com.ubiquity.core.datastore.exceptions.RegisterNotFoundException;

public final class DataShelf {

    private final String identifier;
    private final Map<String, Register> registers;

    private DataShelf(String identifier) {
        checkIdentifier(identifier);
        this.identifier = identifier;
        this.registers = new HashMap<String, Register>();
    }

    public static DataShelf create(String identifier) {
        return new DataShelf(identifier);
    }

    private static void checkIdentifier(String identifier) {
        assert identifier != null;
        assert!identifier.isEmpty();

        /// TODO: additional checks such as allowed characters...
    }

    public String getIdentifier() {
        return identifier;
    }

    public void insertRegister(IRecordTemplate recordTemplate) {
        checkRecordTemplate(recordTemplate);

        String identifier = recordTemplate.getIdentifier();
        if (registers.containsKey(identifier)) {
            throw new RegisterAlreadyExistsException(identifier);
        }

        registers.put(identifier, new Register(recordTemplate));
    }

    public Register getRegister(String identifier) {
        assert identifier != null;

        Register result = registers.get(identifier);
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
