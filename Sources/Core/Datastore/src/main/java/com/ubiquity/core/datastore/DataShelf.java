package com.ubiquity.core.datastore;


public final class DataShelf {

    private final String identifier;


    public static DataShelf create(String identifier) {
        return new DataShelf(identifier);
    }


    private DataShelf(String identifier) {
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
