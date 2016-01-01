package com.ubiquity.core.datastore;

import static com.ubiquity.core.datastore.IFieldTemplate.Kind.*;
import static com.ubiquity.core.datastore.IFieldTemplate.Type.*;

import org.junit.Test;

import com.ubiquity.core.datastore.exceptions.DoubleTypeCannotBeUniqueException;
import com.ubiquity.core.datastore.exceptions.NoPrimaryFieldException;
import com.ubiquity.core.datastore.exceptions.ObjectTypeCannotBeIndexException;
import com.ubiquity.core.datastore.utils.RecordTemplateBuilder;

public class RecordTemplateValidatorTest {

    @Test
    public void testSuccess() {
        RecordTemplateBuilder recordTemplateBuilder = new RecordTemplateBuilder();
        recordTemplateBuilder.withIdentifier("Identifier").addFieldTemplate()
                .withName("Field").withType(BOOLEAN).withKind(PRIMARY)
                .build();
        RecordTemplateValidator
                .validate(recordTemplateBuilder.build());
    }

    @Test(expected = AssertionError.class)
    public void testNullRecordTemplate() {
        RecordTemplateValidator.validate(null);
    }

    @Test(expected = AssertionError.class)
    public void testNullIdentifier() {
        RecordTemplateBuilder recordTemplateBuilder = new RecordTemplateBuilder();
        recordTemplateBuilder.withIdentifier(null).addFieldTemplate()
                .withName("Field").withType(BOOLEAN).withKind(PRIMARY)
                .build();
        RecordTemplateValidator
                .validate(recordTemplateBuilder.build());
    }

    @Test(expected = AssertionError.class)
    public void testEmptyIdentifier() {
        RecordTemplateBuilder recordTemplateBuilder = new RecordTemplateBuilder();
        recordTemplateBuilder.withIdentifier("").addFieldTemplate()
                .withName("Field").withType(BOOLEAN).withKind(PRIMARY)
                .build();
        RecordTemplateValidator
                .validate(recordTemplateBuilder.build());
    }

    @Test(expected = AssertionError.class)
    public void testEmptyRecordTemplate() {
        RecordTemplateBuilder recordTemplateBuilder = new RecordTemplateBuilder();
        RecordTemplateValidator
                .validate(recordTemplateBuilder.withIdentifier("Identifier").build());
    }

    @Test(expected = ObjectTypeCannotBeIndexException.class)
    public void testIndexedFieldObject() {
        RecordTemplateBuilder recordTemplateBuilder = new RecordTemplateBuilder();
        recordTemplateBuilder.withIdentifier("Identifier").addFieldTemplate()
                .withName("Field").withType(OBJECT).withKind(INDEXED)
                .build();
        RecordTemplateValidator
                .validate(recordTemplateBuilder.build());
    }

    @Test(expected = DoubleTypeCannotBeUniqueException.class)
    public void testUniqueFieldObject() {
        RecordTemplateBuilder recordTemplateBuilder = new RecordTemplateBuilder();
        recordTemplateBuilder.withIdentifier("Identifier").addFieldTemplate()
                .withName("Field").withType(DOUBLE).withKind(PRIMARY)
                .build();
        RecordTemplateValidator
                .validate(recordTemplateBuilder.build());
    }

    @Test(expected = NoPrimaryFieldException.class)
    public void testExceptionIfNoPrimaryField() {
        RecordTemplateBuilder recordTemplateBuilder = new RecordTemplateBuilder();
        recordTemplateBuilder.withIdentifier("Identifier").addFieldTemplate()
                .withName("Field").withType(OBJECT).withKind(MANDATORY)
                .build();
        RecordTemplateValidator
                .validate(recordTemplateBuilder.build());
    }

}
