package com.ubiquity.core.datastore;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Strings;
import com.ubiquity.core.datastore.exceptions.DataAlreadyExistsException;

public final class DataShelf {

    private final String identifier;
    private final Map<String, Data> data;

    private DataShelf(String identifier) {
        checkIdentifier(identifier);
        this.identifier = identifier;
        this.data = new HashMap<String, Data>();
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

    public void insertData(IDataDefinition dataDefinition) {
        checkDataDefinition(dataDefinition);

        String identifier = dataDefinition.getIdentifier();
        if (data.containsKey(identifier)) {
            throw new DataAlreadyExistsException(identifier);
        }

        data.put(identifier, new Data(dataDefinition));
    }

    private void checkDataDefinition(IDataDefinition dataDefinition) {
        assert dataDefinition != null;
        assert!Strings.isNullOrEmpty(dataDefinition.getIdentifier());
    }

}
