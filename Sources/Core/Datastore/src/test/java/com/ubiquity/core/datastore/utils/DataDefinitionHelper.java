package com.ubiquity.core.datastore.utils;

import static com.ubiquity.core.datastore.IFieldDefinition.Type.*;

import com.ubiquity.core.datastore.IDataDefinition;

public class DataDefinitionHelper {

    public static IDataDefinition createBasicEntryDataDefinition() {
        DataDefinitionBuilder dataDefinitionBuilder = new DataDefinitionBuilder();

        return dataDefinitionBuilder.withIdentifier("BasicData")
            .addFieldDefinition()
                .withName("Field1")
                .withType(BOOLEAN)
                .withIsIndexed(false)
                .withIsMandatory(true)
                .build()
            .addFieldDefinition()
                .withName("Field2")
                .withType(DOUBLE)
                .withIsIndexed(false)
                .withIsMandatory(true)
                .build()
            .addFieldDefinition()
                .withName("Field3")
                .withType(CHAR)
                .withIsIndexed(false)
                .withIsMandatory(true)
                .build()
            .addFieldDefinition()
                .withName("Field4")
                .withType(INTEGER)
                .withIsIndexed(false)
                .withIsMandatory(true)
                .build()
            .addFieldDefinition()
                .withName("Field5")
                .withType(STRING)
                .withIsIndexed(false)
                .withIsMandatory(true)
                .build()
            .addFieldDefinition()
                .withName("Field6")
                .withType(OBJECT)
                .withIsIndexed(false)
                .withIsMandatory(true)
                .build()
            .build();
    }

}
