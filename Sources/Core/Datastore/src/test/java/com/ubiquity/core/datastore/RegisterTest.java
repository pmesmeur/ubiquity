package com.ubiquity.core.datastore;

import static com.ubiquity.core.datastore.IFieldTemplate.Kind.PRIMARY;
import static com.ubiquity.core.datastore.utils.RecordTemplateHelper.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.ubiquity.core.datastore.exceptions.RecordDoesNotFitTemplateException;
import com.ubiquity.core.datastore.exceptions.ValueOfPrimaryFieldAlreadyInsertedException;
import com.ubiquity.core.datastore.indexes.IIndex;

public class RegisterTest {

    @Test(expected = AssertionError.class)
    public void testCreateWithNullRecordTemplate() {
        new Register(null);
    }

    @Test
    public void testInsertEntry() {
        Register register = new Register(createBasicRecordTemplate());
        Map<String, Object> entryValues = createEntryValues();

        register.insert(entryValues);
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
        entryValues.put("Field7", LocalDate.MAX);
        entryValues.put("Field8", LocalTime.MAX);
        entryValues.put("Field9", Duration.ZERO);
        return entryValues;
    }

    @Test
    public void testIndexesCreated() {
        Register register = new Register(createBasicRecordTemplate());
        Map<String, IIndex> indexes = register.getIndexes();

        Assert.assertEquals(8, indexes.size());
        Assert.assertTrue(indexes.containsKey("Field1"));
        Assert.assertTrue(indexes.containsKey("Field2"));
        Assert.assertTrue(indexes.containsKey("Field3"));
        Assert.assertTrue(indexes.containsKey("Field4"));
        Assert.assertTrue(indexes.containsKey("Field5"));
        Assert.assertFalse(indexes.containsKey("Field6"));
        Assert.assertTrue(indexes.containsKey("Field7"));
        Assert.assertTrue(indexes.containsKey("Field8"));
        Assert.assertTrue(indexes.containsKey("Field9"));
    }

    @Test
    public void testEntryIndexed() {
        Register register = new Register(createBasicRecordTemplate());
        register.insert(createEntryValues("HelloWorld"));
        register.insert(createEntryValues("Foo"));
        register.insert(createEntryValues("Bar"));

        Assert.assertEquals(3, register.getEntries().size());

        Map<String, IIndex> indexes = register.getIndexes();
        for (IIndex index : indexes.values()) {
            Assert.assertTrue(index.getEntry().size() == register.getEntries().size());
        }
    }

    @Test(expected = ValueOfPrimaryFieldAlreadyInsertedException.class)
    public void testDoubleEntryOnPrimaryField() {
        Register register = new Register(createPrimaryOptionalRecordTemplate());
        Map<String, Object> entryValues = createPrimaryOptionalEntryValues();
        register.insert(entryValues);
        register.insert(entryValues);
    }

    private Map<String, Object> createPrimaryOptionalEntryValues() {
        Map<String, Object> entryValues = new HashMap<String, Object>();

        entryValues.put("Field1", new String("Ubiquity"));
        entryValues.put("Field2", new Double(1.));

        return entryValues;
    }

    @Test
    public void testSizeOfIndexesWhenErrorOnPrimaryField() {
        Register register = new Register(createRecordTempalte(PRIMARY));
        register.insert(createEntryValues("Field1", 1., 'A', 1, Boolean.FALSE, register,
                LocalDate.MIN, LocalTime.MIN, Duration.ZERO));
        try {
            register.insert(createEntryValues("Field2", 2., 'B', 1, Boolean.TRUE, register,
                    LocalDate.MAX, LocalTime.MAX, Duration.ZERO));
        } catch (ValueOfPrimaryFieldAlreadyInsertedException e) {
        }

        for (Map.Entry<String, IIndex> indexEntry : register.getIndexes().entrySet()) {
            IIndex index = indexEntry.getValue();
            Assert.assertEquals(1, index.getEntry().size());
        }
    }

    private Map<String, Object> createEntryValues(String field1, Double field2, Character field3,
            Integer field4, Boolean field5, Object field6, LocalDate field7, LocalTime field8, Duration field9) {
        Map<String, Object> entryValues = new HashMap<String, Object>();

        entryValues.put("Field1", field1);
        entryValues.put("Field2", field2);
        entryValues.put("Field3", field3);
        entryValues.put("Field4", field4);
        entryValues.put("Field5", field5);
        entryValues.put("Field6", field6);
        entryValues.put("Field7", field7);
        entryValues.put("Field8", field8);
        entryValues.put("Field9", field9);

        return entryValues;
    }

    @Test(expected = RecordDoesNotFitTemplateException.class)
    public void testInsertRecordThatDoesNotFitTemplate() {
        Register register = new Register(createPrimaryOptionalRecordTemplate());
        Map<String, Object> entryValues = createEntryValues();

        register.insert(entryValues);
    }

}
