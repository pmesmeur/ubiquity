package com.ubiquity.core.datastore.utils;

import static com.ubiquity.core.datastore.IFieldDefinition.DataType.*;
import static com.ubiquity.core.datastore.IFieldDefinition.Kind;
import static com.ubiquity.core.datastore.IFieldDefinition.Kind.INDEXED;
import static com.ubiquity.core.datastore.IFieldDefinition.Kind.MANDATORY;
import static com.ubiquity.core.datastore.IFieldDefinition.Kind.OPTIONAL;

import com.ubiquity.core.datastore.IDataDefinition;

public class DataDefinitionHelper {

    public static IDataDefinition createBasicEntryDataDefinition() {
        return createBasicEntryDataDefinition(MANDATORY);
    }

    public static IDataDefinition createFullIndexedEntryDataDefinition() {
        return createBasicEntryDataDefinition(INDEXED);
    }

    private static IDataDefinition createBasicEntryDataDefinition(Kind kind) {
        DataDefinitionBuilder dataDefinitionBuilder = new DataDefinitionBuilder();

        return dataDefinitionBuilder.withIdentifier("BasicData")
            .addFieldDefinition()
                .withName("Field1")
                .withType(BOOLEAN)
                .withKind(kind)
                .build()
            .addFieldDefinition()
                .withName("Field2")
                .withType(DOUBLE)
                .withKind(kind)
                .build()
            .addFieldDefinition()
                .withName("Field3")
                .withType(CHAR)
                .withKind(kind)
                .build()
            .addFieldDefinition()
                .withName("Field4")
                .withType(INTEGER)
                .withKind(kind)
                .build()
            .addFieldDefinition()
                .withName("Field5")
                .withType(STRING)
                .withKind(kind)
                .build()
            .addFieldDefinition()
                .withName("Field6")
                .withType(OBJECT)
                .withKind(kind.isIndexed() ? MANDATORY : OPTIONAL)
                .build()
            .build();
    }

}
