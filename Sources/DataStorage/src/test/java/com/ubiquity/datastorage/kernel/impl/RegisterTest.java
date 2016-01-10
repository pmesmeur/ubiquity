package com.ubiquity.datastorage.kernel.impl;

import com.ubiquity.datastorage.kernel.exceptions.RecordDoesNotFitTemplateException;
import com.ubiquity.datastorage.kernel.exceptions.ValueOfPrimaryFieldAlreadyInsertedException;
import com.ubiquity.datastorage.kernel.impl.indexes.IIndex;
import com.ubiquity.datastorage.kernel.interfaces.IRecordTemplate;
import com.ubiquity.datastorage.kernel.interfaces.IRegister;
import com.ubiquity.datastorage.utils.RecordTemplateHelper;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static com.ubiquity.datastorage.kernel.interfaces.IFieldTemplate.Kind.PRIMARY;

public class RegisterTest {

    @Test(expected = AssertionError.class)
    public void testCreateWithNullRecordTemplate() {
        createRegister(null);
    }

    private Register createRegister(IRecordTemplate recordTemplate) {
        return Register.create(recordTemplate);
    }

    @Test
    public void testInsertRecord() {
        IRegister register = createRegister(RecordTemplateHelper.createBasicRecordTemplate());
        Map<String, Object> recordFields = createRecordFields();

        register.insert(recordFields);
    }

    private Map<String, Object> createRecordFields() {
        return createRecordFields("Ubiquity");
    }

    private Map<String, Object> createRecordFields(String primariyFieldValue) {
        assert primariyFieldValue != null;
        Map<String, Object> recordFields = new HashMap<String, Object>();

        recordFields.put("Field1", new String(primariyFieldValue));
        recordFields.put("Field2", new Double(1.));
        recordFields.put("Field3", new Character('c'));
        recordFields.put("Field4", new Integer(27));
        recordFields.put("Field5", new Boolean(true));
        recordFields.put("Field6", new HashMap<String, String>());
        recordFields.put("Field7", LocalDate.MAX);
        recordFields.put("Field8", LocalTime.MAX);
        recordFields.put("Field9", Duration.ZERO);
        return recordFields;
    }

    @Test
    public void testIndexesCreated() {
        Register register = createRegister(RecordTemplateHelper.createBasicRecordTemplate());
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
    public void testRecordIndexed() {
        Register register = createRegister(RecordTemplateHelper.createBasicRecordTemplate());
        register.insert(createRecordFields("HelloWorld"));
        register.insert(createRecordFields("Foo"));
        register.insert(createRecordFields("Bar"));

        Assert.assertEquals(3, register.getEntries().size());

        Map<String, IIndex> indexes = register.getIndexes();
        for (IIndex index : indexes.values()) {
            Assert.assertTrue(index.getRecords().size() == register.getEntries().size());
        }
    }

    @Test(expected = ValueOfPrimaryFieldAlreadyInsertedException.class)
    public void testDoubleRecordOnPrimaryField() {
        Register register = createRegister(RecordTemplateHelper.createPrimaryOptionalRecordTemplate());
        Map<String, Object> recordFields = createPrimaryOptionalRecordFields();
        register.insert(recordFields);
        register.insert(recordFields);
    }

    private Map<String, Object> createPrimaryOptionalRecordFields() {
        Map<String, Object> recordFields = new HashMap<String, Object>();

        recordFields.put("Field1", new String("Ubiquity"));
        recordFields.put("Field2", new Double(1.));

        return recordFields;
    }

    @Test
    public void testSizeOfIndexesWhenErrorOnPrimaryField() {
        Register register = createRegister(RecordTemplateHelper.createRecordTempalte(PRIMARY));
        register.insert(createRecordFields("Field1", 1., 'A', 1, Boolean.FALSE, register,
                LocalDate.MIN, LocalTime.MIN, Duration.ZERO));
        try {
            register.insert(createRecordFields("Field2", 2., 'B', 1, Boolean.TRUE, register,
                    LocalDate.MAX, LocalTime.MAX, Duration.ZERO));
        } catch (ValueOfPrimaryFieldAlreadyInsertedException e) {
        }

        for (Map.Entry<String, IIndex> indexEntry : register.getIndexes().entrySet()) {
            IIndex index = indexEntry.getValue();
            Assert.assertEquals(1, index.getRecords().size());
        }
    }

    private Map<String, Object> createRecordFields(String field1, Double field2, Character field3,
            Integer field4, Boolean field5, Object field6, LocalDate field7, LocalTime field8,
            Duration field9) {
        Map<String, Object> recordFields = new HashMap<String, Object>();

        recordFields.put("Field1", field1);
        recordFields.put("Field2", field2);
        recordFields.put("Field3", field3);
        recordFields.put("Field4", field4);
        recordFields.put("Field5", field5);
        recordFields.put("Field6", field6);
        recordFields.put("Field7", field7);
        recordFields.put("Field8", field8);
        recordFields.put("Field9", field9);

        return recordFields;
    }

    @Test(expected = RecordDoesNotFitTemplateException.class)
    public void testInsertRecordThatDoesNotFitTemplate() {
        Register register = createRegister(RecordTemplateHelper.createPrimaryOptionalRecordTemplate());
        Map<String, Object> recordFields = createRecordFields();

        register.insert(recordFields);
    }

}
