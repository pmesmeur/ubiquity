package com.ubiquity.core.datastore;


public class DataShelf {

    private final String identifier;


    public DataShelf(String identifier) {
        checkIdentifier(identifier);
        this.identifier = identifier;
    }


    public String getIdentifier() {
        return identifier;
    }


    private static void checkIdentifier(String identifier) {
        assert identifier != null;
        assert !identifier.isEmpty();

        /// TODO: additional checks such as allowed characters...
    }

}
