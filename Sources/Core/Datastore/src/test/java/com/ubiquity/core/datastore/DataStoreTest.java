package com.ubiquity.core.datastore;

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


    @Test(expected = DataShelfNotFoundException.class)
    public void testInsertDataOnUnknownShelf() {
        dataStore.insertData("ThisShelfDoesNotExist", new DataDefinition());
    }

    @Test(expected = AssertionError.class)
    public void testInsertDataWithEmptyIdentifier() {
        dataStore.insertData("", new DataDefinition());
    }

    @Test(expected = AssertionError.class)
    public void testInsertDataWithNullIdentifier() {
        dataStore.insertData(null, new DataDefinition());
    }

    @Test(expected = AssertionError.class)
    public void testInsertDataWithNullDefinition() {
        dataStore.insertData("ThisShelfDoesNotExist", null);
    }

    private class DataDefinition implements IDataDefinition {
        @Override
        public String getIdentifier() {
            return "AnIdentifier";
        }

        @Override
        public Collection<IFieldDefinition> getFieldDefinitions() {
            return null;
        }
    }

}