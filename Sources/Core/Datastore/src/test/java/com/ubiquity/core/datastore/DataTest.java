package com.ubiquity.core.datastore;

import static com.ubiquity.core.datastore.utils.DataDefinitionHelper.createBasicEntryDataDefinition;
import static com.ubiquity.core.datastore.utils.DataDefinitionHelper.createFullIndexedEntryDataDefinition;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class DataTest {

    @Test(expected = AssertionError.class)
    public void testCreateWithNullDataDefinition() {
        new Data(null);
    }

    @Test
    public void testInsertEntry() {
        Data data = new Data(createBasicEntryDataDefinition());
        Map<String, Object> entryValues = createEntryValues();

        data.insert(entryValues);
    }

    private Map<String, Object> createEntryValues() {
        Map<String, Object> entryValues = new HashMap<String, Object>();

        entryValues.put("Field1", new Boolean(true));
        entryValues.put("Field2", new Double(1.));
        entryValues.put("Field3", new Character('c'));
        entryValues.put("Field4", new Integer(27));
        entryValues.put("Field5", new String("Ubiquity"));
        entryValues.put("Field6", new HashMap<String, String>());
        return entryValues;
    }

    @Test
    public void testNoIndexes() {
        Data data = new Data(createBasicEntryDataDefinition());
        Assert.assertEquals(0, data.getIndexes().size());
    }

    @Test
    public void testIndexesCreated() {
        Data data = new Data(createFullIndexedEntryDataDefinition());
        Map<String, Index> indexes = data.getIndexes();

        Assert.assertEquals(5, indexes.size());
        Assert.assertTrue(indexes.containsKey("Field1"));
        Assert.assertTrue(indexes.containsKey("Field2"));
        Assert.assertTrue(indexes.containsKey("Field3"));
        Assert.assertTrue(indexes.containsKey("Field4"));
        Assert.assertTrue(indexes.containsKey("Field5"));
        Assert.assertFalse(indexes.containsKey("Field6"));
    }

    @Test
    public void testEntryIndexed() {
        Data data = new Data(createFullIndexedEntryDataDefinition());
        Map<String, Object> entryValues = createEntryValues();
        data.insert(entryValues);
        data.insert(entryValues);
        data.insert(entryValues);

        Assert.assertEquals(3, data.getEntries().size());

        Map<String, Index> indexes = data.getIndexes();
        for (Index index : indexes.values()) {
            Assert.assertTrue(index.getIndexedObjects().size() == data.getEntries().size());
        }
    }
}
