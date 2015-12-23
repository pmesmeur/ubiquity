package com.ubiquity.core.datastore;

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


    private void testDataShelfInsertions(int quantity) {
        for (int index = 0 ; index < quantity ; index++) {
            testDataShelfInsertion("id" + index);
        }
    }


    private void testDataShelfInsertion(String identifier) {
        DataShelf dataShelf1 = dataStore.insertDataShelf(identifier);
        Assert.assertEquals(dataShelf1, dataStore.getDataShelf(identifier));
    }

}
