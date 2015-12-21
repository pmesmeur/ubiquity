package com.ubiquity.core.datastore;

import static com.ubiquity.core.datastore.IFieldDefinition.Kind.PRIMARY;
import static com.ubiquity.core.datastore.utils.DataDefinitionHelper.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.ubiquity.core.datastore.exceptions.EntryDoesNotFitDataDefinitionException;
import com.ubiquity.core.datastore.exceptions.ValueOfPrimaryFieldAlreadyInsertedException;
import com.ubiquity.core.datastore.indexes.IIndex;

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
        return createEntryValues("Ubiquity");
    }

    private Map<String, Object> createEntryValues(String primariyFieldValue) {
        assert primariyFieldValue != null;
        Map<String, Object> entryValues = new HashMap<String, Object>();

        entryValues.put("Field1", new String(primariyFieldValue));
        entryValues.put("Field2", new Double(1.));
        entryValues.put("Field3", new Character('c'));
        entryValues.put("Field4", new Integer(27));
        entryValues.put("Field5", new Boolean(true));
        entryValues.put("Field6", new HashMap<String, String>());
        return entryValues;
    }

    @Test
    public void testIndexesCreated() {
        Data data = new Data(createBasicEntryDataDefinition());
        Map<String, IIndex> indexes = data.getIndexes();

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
        Data data = new Data(createBasicEntryDataDefinition());
        data.insert(createEntryValues("HelloWorld"));
        data.insert(createEntryValues("Foo"));
        data.insert(createEntryValues("Bar"));

        Assert.assertEquals(3, data.getEntries().size());

        Map<String, IIndex> indexes = data.getIndexes();
        for (IIndex index : indexes.values()) {
            Assert.assertTrue(index.getEntry().size() == data.getEntries().size());
        }
    }

    @Test(expected = ValueOfPrimaryFieldAlreadyInsertedException.class)
    public void testDoubleEntryOnPrimaryField() {
        Data data = new Data(createPrimaryOptionalEntry());
        Map<String, Object> entryValues = createPrimaryOptionalEntryValues();
        data.insert(entryValues);
        data.insert(entryValues);
    }

    private Map<String, Object> createPrimaryOptionalEntryValues() {
        Map<String, Object> entryValues = new HashMap<String, Object>();

        entryValues.put("Field1", new String("Ubiquity"));
        entryValues.put("Field2", new Double(1.));

        return entryValues;
    }

    @Test
    public void testSizeOfIndexesWhenErrorOnPrimaryField() {
        Data data = new Data(createEntryDataDefinition(PRIMARY));
        data.insert(createEntryValues("Field1", 1., 'A', 1, Boolean.FALSE, data));
        try {
            data.insert(createEntryValues("Field2", 2., 'B', 1, Boolean.TRUE, data));
        } catch (ValueOfPrimaryFieldAlreadyInsertedException e) {
        }

        for (Map.Entry<String, IIndex> indexEntry : data.getIndexes().entrySet()) {
            IIndex index = indexEntry.getValue();
            Assert.assertEquals(1, index.getEntry().size());
        }
    }

    private Map<String, Object> createEntryValues(String field1, Double field2, Character field3,
            Integer field4, Boolean field5, Object field6) {
        Map<String, Object> entryValues = new HashMap<String, Object>();

        entryValues.put("Field1", field1);
        entryValues.put("Field2", field2);
        entryValues.put("Field3", field3);
        entryValues.put("Field4", field4);
        entryValues.put("Field5", field5);
        entryValues.put("Field6", field6);

        return entryValues;
    }

    @Test(expected = EntryDoesNotFitDataDefinitionException.class)
    public void testInsertEntryThatDoesNotFitDataDefinition() {
        Data data = new Data(createPrimaryOptionalEntry());
        Map<String, Object> entryValues = createEntryValues();

        data.insert(entryValues);
    }

}
