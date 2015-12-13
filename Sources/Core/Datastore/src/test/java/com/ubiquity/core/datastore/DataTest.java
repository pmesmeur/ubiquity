package com.ubiquity.core.datastore;

import static com.ubiquity.core.datastore.utils.DataDefinitionHelper.createBasicEntryDataDefinition;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class DataTest {

    @Test(expected = AssertionError.class)
    public void testCreateWithNullDataDefinition() {
        new Data(null);
    }

    @Test
    public void testInsertEntry() {
        Data data = new Data(createBasicEntryDataDefinition());

        Map<String, Object> entryValues = new HashMap<String, Object>();

        entryValues.put("Field1", new Boolean(true));
        entryValues.put("Field2", new Double(1.));
        entryValues.put("Field3", new Character('c'));
        entryValues.put("Field4", new Integer(27));
        entryValues.put("Field5", new String("Ubiquity"));
        entryValues.put("Field6", new HashMap<String, String>());

        data.insert(entryValues);
    }

    @Test
    public void testQueryEntry() {

    }
}
