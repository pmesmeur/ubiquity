package com.ubiquity.core.datastore;

import com.ubiquity.core.datastore.exceptions.DoubleTypeCannotBeUniqueException;
import com.ubiquity.core.datastore.exceptions.NoPrimaryFieldException;
import com.ubiquity.core.datastore.exceptions.ObjectTypeCannotBeIndexException;
import com.ubiquity.core.datastore.utils.RecordTemplateBuilder;
import org.junit.Test;

import static com.ubiquity.core.datastore.IFieldTemplate.Kind.*;
import static com.ubiquity.core.datastore.IFieldTemplate.Type.*;

public class RecordTemplateValidatorTest {

    @Test
    public void testSuccess() {
        RecordTemplateBuilder recordTemplateBuilder = new RecordTemplateBuilder();
        recordTemplateBuilder.withIdentifier("Identifier").addFieldTemplate()
                .withIdentifier("Field").withType(BOOLEAN).withKind(PRIMARY)
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
                .withIdentifier("Field").withType(BOOLEAN).withKind(PRIMARY)
                .build();
        RecordTemplateValidator
                .validate(recordTemplateBuilder.build());
    }

    @Test(expected = AssertionError.class)
    public void testEmptyIdentifier() {
        RecordTemplateBuilder recordTemplateBuilder = new RecordTemplateBuilder();
        recordTemplateBuilder.withIdentifier("").addFieldTemplate()
                .withIdentifier("Field").withType(BOOLEAN).withKind(PRIMARY)
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
                .withIdentifier("Field").withType(OBJECT).withKind(INDEXED)
                .build();
        RecordTemplateValidator
                .validate(recordTemplateBuilder.build());
    }

    @Test(expected = DoubleTypeCannotBeUniqueException.class)
    public void testUniqueFieldObject() {
        RecordTemplateBuilder recordTemplateBuilder = new RecordTemplateBuilder();
        recordTemplateBuilder.withIdentifier("Identifier").addFieldTemplate()
                .withIdentifier("Field").withType(DOUBLE).withKind(PRIMARY)
                .build();
        RecordTemplateValidator
                .validate(recordTemplateBuilder.build());
    }

    @Test(expected = NoPrimaryFieldException.class)
    public void testExceptionIfNoPrimaryField() {
        RecordTemplateBuilder recordTemplateBuilder = new RecordTemplateBuilder();
        recordTemplateBuilder.withIdentifier("Identifier").addFieldTemplate()
                .withIdentifier("Field").withType(OBJECT).withKind(MANDATORY)
                .build();
        RecordTemplateValidator
                .validate(recordTemplateBuilder.build());
    }

}
