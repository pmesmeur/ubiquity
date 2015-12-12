package com.ubiquity.core.datastore;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Strings;

public final class DataShelf {

    private final String identifier;
    private final Map<String, IDataType> dataTypes;

    public static DataShelf create(String identifier) {
        return new DataShelf(identifier);
    }

    private DataShelf(String identifier) {
        checkIdentifier(identifier);
        this.identifier = identifier;
        dataTypes = new HashMap<String, IDataType>();
    }

    public String getIdentifier() {
        return identifier;
    }

    public void insertDataType(IDataType dataType) {
        checkDataType(dataType);

        String identifier = dataType.getIdentifier();
        if (dataTypes.containsKey(identifier)) {
            throw new IllegalArgumentException(
                    "Data-type of identifier \"" + identifier + "\" already exists");
        }
        dataTypes.put(identifier, dataType);
    }

    private void checkDataType(IDataType dataType) {
        assert dataType != null;
        assert!Strings.isNullOrEmpty(dataType.getIdentifier());
    }

    private static void checkIdentifier(String identifier) {
        assert identifier != null;
        assert!identifier.isEmpty();

        /// TODO: additional checks such as allowed characters...
    }

}
