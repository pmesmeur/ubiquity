package com.ubiquity.core.datastore;

public interface IFieldDefinition {
    enum Type { INTEGER, DOUBLE, STRING, BOOLEAN, OBJECT, CHAR }

    String getName();
    Type getType();
    boolean isIndexed();
    boolean isMandatory();

}
