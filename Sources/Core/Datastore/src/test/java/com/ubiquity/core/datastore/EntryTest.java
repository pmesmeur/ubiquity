package com.ubiquity.core.datastore;

import static com.ubiquity.core.datastore.utils.RecordTemplateHelper.createBasicRecordTemplate;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ubiquity.core.datastore.exceptions.MissingMandatoryFieldException;
import com.ubiquity.core.datastore.exceptions.WrongFieldTypeException;

public class EntryTest {

    @Test(expected = AssertionError.class)
    public void testCreatingRecordWithNullTemplate() {
        Map<String, Object> entryValues = new HashMap<String, Object>();

        entryValues.put("Field1", new String("Ubiquity"));
        entryValues.put("Field2", new Double(1.));
        entryValues.put("Field3", new Character('c'));
        entryValues.put("Field4", new Integer(27));
        entryValues.put("Field5", new Boolean(true));
        entryValues.put("Field6", new HashMap<String, String>());

        new Entry(null, entryValues);
    }

    @Test(expected = AssertionError.class)
    public void testCreatingEntryWithNullValues() {
        new Entry(createBasicRecordTemplate(), null);
    }

    @Test
    public void testCreatingBasicEntry() {
        Map<String, Object> entryValues = new HashMap<String, Object>();

        entryValues.put("Field1", new String("Ubiquity"));
        entryValues.put("Field2", new Double(1.));
        entryValues.put("Field3", new Character('c'));
        entryValues.put("Field4", new Integer(27));
        entryValues.put("Field5", new Boolean(true));
        entryValues.put("Field6", new HashMap<String, String>());
        entryValues.put("Field7", LocalDate.MAX);
        entryValues.put("Field8", LocalTime.MAX);
        entryValues.put("Field9", Duration.ZERO);

        new Entry(createBasicRecordTemplate(), entryValues);
    }

    @Test(expected = MissingMandatoryFieldException.class)
    public void testCreatingEntryWithMissingMandatoryField() {
        Map<String, Object> entryValues = new HashMap<String, Object>();

        entryValues.put("Field1", new String("Ubiquity"));
        entryValues.put("Field2", new Double(1.));
        entryValues.put("Field4", new Integer(27));
        entryValues.put("Field5", new Boolean(true));
        entryValues.put("Field6", new HashMap<String, String>());

        new Entry(createBasicRecordTemplate(), entryValues);
    }

    @Test(expected = MissingMandatoryFieldException.class)
    public void testCreatingEntryWithMissingMandatoryFieldHavingNullValue() {
        Map<String, Object> entryValues = new HashMap<String, Object>();

        entryValues.put("Field1", new String("Ubiquity"));
        entryValues.put("Field2", new Double(1.));
        entryValues.put("Field3", null);
        entryValues.put("Field4", new Integer(27));
        entryValues.put("Field5", new Boolean(true));
        entryValues.put("Field6", new HashMap<String, String>());

        new Entry(createBasicRecordTemplate(), entryValues);
    }

    @Test(expected = WrongFieldTypeException.class)
    public void testCreatingEntryWithBadTypedField() {
        Map<String, Object> entryValues = new HashMap<String, Object>();

        entryValues.put("Field1", new String("Ubiquity"));
        entryValues.put("Field2", new Double(1.));
        entryValues.put("Field3", new Integer(666));
        entryValues.put("Field4", new Integer(27));
        entryValues.put("Field5", new Boolean(true));
        entryValues.put("Field6", new HashMap<String, String>());

        new Entry(createBasicRecordTemplate(), entryValues);
    }

}
