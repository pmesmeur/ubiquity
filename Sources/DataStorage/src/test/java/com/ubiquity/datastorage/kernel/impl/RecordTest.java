package com.ubiquity.datastorage.kernel.impl;

import com.ubiquity.datastorage.kernel.exceptions.MissingMandatoryFieldException;
import com.ubiquity.datastorage.kernel.exceptions.WrongFieldTypeException;
import com.ubiquity.datastorage.utils.RecordTemplateHelper;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class RecordTest {

    @Test(expected = AssertionError.class)
    public void testCreatingRecordWithNullTemplate() {
        Map<String, Object> recordFields = new HashMap<String, Object>();

        recordFields.put("Field1", new String("Ubiquity"));
        recordFields.put("Field2", new Double(1.));
        recordFields.put("Field3", new Character('c'));
        recordFields.put("Field4", new Integer(27));
        recordFields.put("Field5", new Boolean(true));
        recordFields.put("Field6", new HashMap<String, String>());

        new Record(null, recordFields);
    }

    @Test(expected = AssertionError.class)
    public void testCreatingRecordWithNullValues() {
        new Record(RecordTemplateHelper.createBasicRecordTemplate(), null);
    }

    @Test
    public void testCreatingBasicRecord() {
        Map<String, Object> RecordFields = new HashMap<String, Object>();

        RecordFields.put("Field1", new String("Ubiquity"));
        RecordFields.put("Field2", new Double(1.));
        RecordFields.put("Field3", new Character('c'));
        RecordFields.put("Field4", new Integer(27));
        RecordFields.put("Field5", new Boolean(true));
        RecordFields.put("Field6", new HashMap<String, String>());
        RecordFields.put("Field7", LocalDate.MAX);
        RecordFields.put("Field8", LocalTime.MAX);
        RecordFields.put("Field9", Duration.ZERO);

        new Record(RecordTemplateHelper.createBasicRecordTemplate(), RecordFields);
    }

    @Test(expected = MissingMandatoryFieldException.class)
    public void testCreatingRecordWithMissingMandatoryField() {
        Map<String, Object> recordFields = new HashMap<String, Object>();

        recordFields.put("Field1", new String("Ubiquity"));
        recordFields.put("Field2", new Double(1.));
        recordFields.put("Field4", new Integer(27));
        recordFields.put("Field5", new Boolean(true));
        recordFields.put("Field6", new HashMap<String, String>());

        new Record(RecordTemplateHelper.createBasicRecordTemplate(), recordFields);
    }

    @Test(expected = MissingMandatoryFieldException.class)
    public void testCreatingRecordWithMissingMandatoryFieldHavingNullValue() {
        Map<String, Object> recordFields = new HashMap<String, Object>();

        recordFields.put("Field1", new String("Ubiquity"));
        recordFields.put("Field2", new Double(1.));
        recordFields.put("Field3", null);
        recordFields.put("Field4", new Integer(27));
        recordFields.put("Field5", new Boolean(true));
        recordFields.put("Field6", new HashMap<String, String>());

        new Record(RecordTemplateHelper.createBasicRecordTemplate(), recordFields);
    }

    @Test(expected = WrongFieldTypeException.class)
    public void testCreatingRecordWithBadTypedField() {
        Map<String, Object> recordFields = new HashMap<String, Object>();

        recordFields.put("Field1", new String("Ubiquity"));
        recordFields.put("Field2", new Double(1.));
        recordFields.put("Field3", new Integer(666));
        recordFields.put("Field4", new Integer(27));
        recordFields.put("Field5", new Boolean(true));
        recordFields.put("Field6", new HashMap<String, String>());

        new Record(RecordTemplateHelper.createBasicRecordTemplate(), recordFields);
    }

}
