package com.ubiquity.core.datastore.utils;

import static com.ubiquity.core.datastore.IFieldDefinition.Type.*;

import com.ubiquity.core.datastore.IDataDefinition;

public class DataDefinitionHelper {

    public static IDataDefinition createBasicEntryDataDefinition() {
        return createBasicEntryDataDefinition(false, true);
    }

    public static IDataDefinition createFullIndexedEntryDataDefinition() {
        return createBasicEntryDataDefinition(true, true);
    }

    private static IDataDefinition createBasicEntryDataDefinition(boolean isIndexed, boolean isMandatory) {
        DataDefinitionBuilder dataDefinitionBuilder = new DataDefinitionBuilder();

        return dataDefinitionBuilder.withIdentifier("BasicData")
            .addFieldDefinition()
                .withName("Field1")
                .withType(BOOLEAN)
                .withIsIndexed(isIndexed)
                .withIsMandatory(isMandatory)
                .build()
            .addFieldDefinition()
                .withName("Field2")
                .withType(DOUBLE)
                .withIsIndexed(isIndexed)
                .withIsMandatory(isMandatory)
                .build()
            .addFieldDefinition()
                .withName("Field3")
                .withType(CHAR)
                .withIsIndexed(isIndexed)
                .withIsMandatory(isMandatory)
                .build()
            .addFieldDefinition()
                .withName("Field4")
                .withType(INTEGER)
                .withIsIndexed(isIndexed)
                .withIsMandatory(isMandatory)
                .build()
            .addFieldDefinition()
                .withName("Field5")
                .withType(STRING)
                .withIsIndexed(isIndexed)
                .withIsMandatory(isMandatory)
                .build()
            .addFieldDefinition()
                .withName("Field6")
                .withType(OBJECT)
                .withIsIndexed(false)
                .withIsMandatory(isMandatory)
                .build()
            .build();
    }

}
