package com.ubiquity.core.datastore;

import static com.ubiquity.core.datastore.utils.DataDefinitionHelper.createBasicEntryDataDefinition;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class EntryTest {

    @Test(expected = AssertionError.class)
    public void testCreatingEntryWithNullDataDefinition() {
        Map<String, Object> entryValues = new HashMap<String, Object>();

        entryValues.put("Field1", new Boolean(true));
        entryValues.put("Field2", new Double(1.));
        entryValues.put("Field3", new Character('c'));
        entryValues.put("Field4", new Integer(27));
        entryValues.put("Field5", new String("Ubiquity"));
        entryValues.put("Field6", new HashMap<String, String>());

        new Entry(null, entryValues);
    }

    @Test(expected = AssertionError.class)
    public void testCreatingEntryWithNullValues() {
        new Entry(createBasicEntryDataDefinition(), null);
    }

    @Test
    public void testCreatingBasicEntry() {
        Map<String, Object> entryValues = new HashMap<String, Object>();

        entryValues.put("Field1", new Boolean(true));
        entryValues.put("Field2", new Double(1.));
        entryValues.put("Field3", new Character('c'));
        entryValues.put("Field4", new Integer(27));
        entryValues.put("Field5", new String("Ubiquity"));
        entryValues.put("Field6", new HashMap<String, String>());

        new Entry(createBasicEntryDataDefinition(), entryValues);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreatingEntryWithMissingMandatoryField() {
        Map<String, Object> entryValues = new HashMap<String, Object>();

        entryValues.put("Field1", new Boolean(true));
        entryValues.put("Field2", new Double(1.));
        entryValues.put("Field4", new Integer(27));
        entryValues.put("Field5", new String("Ubiquity"));
        entryValues.put("Field6", new HashMap<String, String>());

        new Entry(createBasicEntryDataDefinition(), entryValues);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreatingEntryWithBadTypedField() {
        Map<String, Object> entryValues = new HashMap<String, Object>();

        entryValues.put("Field1", new Boolean(true));
        entryValues.put("Field2", new Double(1.));
        entryValues.put("Field3", new Integer(666));
        entryValues.put("Field4", new Integer(27));
        entryValues.put("Field5", new String("Ubiquity"));
        entryValues.put("Field6", new HashMap<String, String>());

        new Entry(createBasicEntryDataDefinition(), entryValues);
    }

}
