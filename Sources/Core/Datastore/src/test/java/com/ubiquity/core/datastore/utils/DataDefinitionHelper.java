package com.ubiquity.core.datastore.utils;

import static com.ubiquity.core.datastore.IFieldTemplate.Kind;
import static com.ubiquity.core.datastore.IFieldTemplate.Kind.*;
import static com.ubiquity.core.datastore.IFieldTemplate.Type.*;

import com.ubiquity.core.datastore.IDataDefinition;

public class DataDefinitionHelper {

    public static IDataDefinition createBasicEntryDataDefinition() {
        return createEntryDataDefinition(INDEXED);
    }

    public static IDataDefinition createEntryDataDefinition(Kind kind) {
        DataDefinitionBuilder dataDefinitionBuilder = new DataDefinitionBuilder();

        return dataDefinitionBuilder.withIdentifier("BasicData")
                .addFieldTemplate()
                .withName("Field1")
                .withType(STRING)
                .withKind(PRIMARY)
                .build()
                .addFieldTemplate()
                .withName("Field2")
                .withType(DOUBLE)
                .withKind(kind.isUnique() ? INDEXED : kind)
                .build()
                .addFieldTemplate()
                .withName("Field3")
                .withType(CHAR)
                .withKind(kind)
                .build()
                .addFieldTemplate()
                .withName("Field4")
                .withType(INTEGER)
                .withKind(kind)
                .build()
                .addFieldTemplate()
                .withName("Field5")
                .withType(BOOLEAN)
                .withKind(kind)
                .build()
                .addFieldTemplate()
                .withName("Field6")
                .withType(OBJECT)
                .withKind(kind.isIndexed() ? MANDATORY : OPTIONAL)
                .build()
                .addFieldTemplate()
                .withName("Field7")
                .withType(DATE)
                .withKind(kind)
                .build()
                .addFieldTemplate()
                .withName("Field8")
                .withType(TIME)
                .withKind(kind)
                .build()
                .addFieldTemplate()
                .withName("Field9")
                .withType(DURATION)
                .withKind(kind)
                .build()
            .build();
    }

    public static IDataDefinition createPrimaryOptionalEntry() {
        DataDefinitionBuilder dataDefinitionBuilder = new DataDefinitionBuilder();

        return dataDefinitionBuilder.withIdentifier("BasicData")
                .addFieldTemplate()
                .withName("Field1")
                .withType(STRING)
                .withKind(PRIMARY)
                .build()
                .addFieldTemplate()
                .withName("Field2")
                .withType(DOUBLE)
                .withKind(OPTIONAL)
                .build()
                .build();
    }

}
