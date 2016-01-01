package com.ubiquity.core.datastore;

import static com.ubiquity.core.datastore.IFieldTemplate.Kind.*;
import static com.ubiquity.core.datastore.IFieldTemplate.Type.*;

import org.junit.Test;

import com.ubiquity.core.datastore.exceptions.DoubleTypeCannotBeUniqueException;
import com.ubiquity.core.datastore.exceptions.NoPrimaryFieldException;
import com.ubiquity.core.datastore.exceptions.ObjectTypeCannotBeIndexException;
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

    @Test(expected = ObjectTypeCannotBeIndexException.class)
    public void testIndexedFieldObject() {
        DataDefinitionBuilder dataDefinitionBuilder = new DataDefinitionBuilder();
        dataDefinitionBuilder.withIdentifier("Identifier").addFieldDefinition()
                .withName("Field").withType(OBJECT).withKind(INDEXED)
                .build();
        DataDefinitionValidator
                .validate(dataDefinitionBuilder.build());
    }

    @Test(expected = DoubleTypeCannotBeUniqueException.class)
    public void testUniqueFieldObject() {
        DataDefinitionBuilder dataDefinitionBuilder = new DataDefinitionBuilder();
        dataDefinitionBuilder.withIdentifier("Identifier").addFieldDefinition()
                .withName("Field").withType(DOUBLE).withKind(PRIMARY)
                .build();
        DataDefinitionValidator
                .validate(dataDefinitionBuilder.build());
    }

    @Test(expected = NoPrimaryFieldException.class)
    public void testExceptionIfNoPrimaryField() {
        DataDefinitionBuilder dataDefinitionBuilder = new DataDefinitionBuilder();
        dataDefinitionBuilder.withIdentifier("Identifier").addFieldDefinition()
                .withName("Field").withType(OBJECT).withKind(MANDATORY)
                .build();
        DataDefinitionValidator
                .validate(dataDefinitionBuilder.build());
    }

}
