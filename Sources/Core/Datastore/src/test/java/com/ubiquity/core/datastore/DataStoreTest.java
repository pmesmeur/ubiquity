package com.ubiquity.core.datastore;

import static com.ubiquity.core.datastore.IFieldTemplate.Kind.PRIMARY;
import static com.ubiquity.core.datastore.IFieldTemplate.Type.STRING;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.ubiquity.core.datastore.exceptions.DataShelfNotFoundException;

public class DataStoreTest {

    private final int NB_INSERTIONS = 3;
    private DataStore dataStore;


    @Before
    public void init() {
        dataStore = new DataStore();
    }


    @Test
    public void testShelvesInsert() {
        testDataShelfInsertions(NB_INSERTIONS);

        Set<String> dataShelvesId = dataStore.getAllShelvesId();
        Assert.assertEquals(dataShelvesId.size(), NB_INSERTIONS);
    }


    private void testDataShelfInsertions(int quantity) {
        for (int index = 0; index < quantity; index++) {
            testDataShelfInsertion("id" + index);
        }
    }


    private void testDataShelfInsertion(String identifier) {
        DataShelf dataShelf1 = dataStore.insertDataShelf(identifier);
        Assert.assertEquals(dataShelf1, dataStore.getDataShelf(identifier));
    }


    @Test(expected = DataShelfNotFoundException.class)
    public void testGetUnexistingShelf() {
        testDataShelfInsertions(NB_INSERTIONS);
        dataStore.getDataShelf("hello-world");
    }


    @Test(expected = AssertionError.class)
    public void testNullNamedShelfInsert() {
        dataStore.insertDataShelf(null);
    }


    @Test(expected = AssertionError.class)
    public void testEmptyNamedShelfInsert() {
        dataStore.insertDataShelf("");
    }


    @Test
    public void testInsertData() {
        final String DATA_SHELF_NAME = "The.Data.Shelf";
        RecordTemplate recordTemplate = new RecordTemplate();

        dataStore.insertDataShelf(DATA_SHELF_NAME);
        dataStore.insertRegister(DATA_SHELF_NAME, recordTemplate);

        DataShelf dataShelf = dataStore.getDataShelf(DATA_SHELF_NAME);
        assertNotNull(dataShelf);

        Register register = dataShelf.getRegister(recordTemplate.getIdentifier());
        assertNotNull(register);
    }


    @Test(expected = DataShelfNotFoundException.class)
    public void testInsertDataOnUnknownShelf() {
        dataStore.insertRegister("ThisShelfDoesNotExist", new RecordTemplate());
    }


    @Test(expected = AssertionError.class)
    public void testInsertRecordWithEmptyIdentifier() {
        dataStore.insertRegister("", new RecordTemplate());
    }


    @Test(expected = AssertionError.class)
    public void testInsertRecordWithNullIdentifier() {
        dataStore.insertRegister(null, new RecordTemplate());
    }


    @Test(expected = AssertionError.class)
    public void testInsertRecordWithNullDefinition() {
        dataStore.insertRegister("ThisShelfDoesNotExist", null);
    }


    private class RecordTemplate implements IRecordTemplate {

        private final Collection<IFieldTemplate> fieldTemplate;

        public RecordTemplate() {
            this.fieldTemplate = new ArrayList<IFieldTemplate>();
            fieldTemplate.add(new IFieldTemplate() {
                @Override
                public String getName() {
                    return "DummyField";
                }

                @Override
                public Type getType() {
                    return STRING;
                }

                @Override
                public Kind getKind() {
                    return PRIMARY;
                }
            });
        }

        @Override
        public String getIdentifier() {
            return "AnIdentifier";
        }

        @Override
        public Collection<IFieldTemplate> getFieldTemplates() {
            return fieldTemplate;
        }
    }

}