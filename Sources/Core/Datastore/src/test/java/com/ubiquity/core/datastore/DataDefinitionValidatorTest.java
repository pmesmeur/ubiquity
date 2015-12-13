package com.ubiquity.core.datastore;

import static com.ubiquity.core.datastore.IFieldDefinition.DataType.BOOLEAN;
import static com.ubiquity.core.datastore.IFieldDefinition.DataType.OBJECT;
import static com.ubiquity.core.datastore.IFieldDefinition.Kind.PRIMARY;

import org.junit.Test;

import com.ubiquity.core.datastore.utils.DataDefinitionBuilder;

public class DataDefinitionValidatorTest {

    @Test
    public void testSuccess() {
        DataDefinitionBuilder dataDefinitionBuilder = new DataDefinitionBuilder();
        dataDefinitionBuilder.withIdentifier("Identifier").addFieldDefinition()
                .withName("Field").withType(BOOLEAN).withKind(PRIMARY)
                .build();
        DataDefinitionValidator
                .validate(dataDefinitionBuilder.build());
    }

    @Test(expected = AssertionError.class)
    public void testNullDataDefinition() {
        DataDefinitionValidator.validate(null);
    }

    @Test(expected = AssertionError.class)
    public void testNullIdentifier() {
        DataDefinitionBuilder dataDefinitionBuilder = new DataDefinitionBuilder();
        dataDefinitionBuilder.withIdentifier(null).addFieldDefinition()
                .withName("Field").withType(BOOLEAN).withKind(PRIMARY)
                .build();
        DataDefinitionValidator
                .validate(dataDefinitionBuilder.build());
    }

    @Test(expected = AssertionError.class)
    public void testEmptyIdentifier() {
        DataDefinitionBuilder dataDefinitionBuilder = new DataDefinitionBuilder();
        dataDefinitionBuilder.withIdentifier("").addFieldDefinition()
                .withName("Field").withType(BOOLEAN).withKind(PRIMARY)
                .build();
        DataDefinitionValidator
                .validate(dataDefinitionBuilder.build());
    }

    @Test(expected = AssertionError.class)
    public void testEmptyDataDefinition() {
        DataDefinitionBuilder dataDefinitionBuilder = new DataDefinitionBuilder();
        DataDefinitionValidator
                .validate(dataDefinitionBuilder.withIdentifier("Identifier").build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIndexedFieldObject() {
        DataDefinitionBuilder dataDefinitionBuilder = new DataDefinitionBuilder();
        dataDefinitionBuilder.withIdentifier("Identifier").addFieldDefinition()
                .withName("Field").withType(OBJECT).withKind(PRIMARY)
                .build();
        DataDefinitionValidator
                .validate(dataDefinitionBuilder.build());
    }

}
