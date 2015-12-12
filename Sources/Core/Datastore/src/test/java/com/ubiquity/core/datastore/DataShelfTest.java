package com.ubiquity.core.datastore;

import org.junit.Assert;
import org.junit.Test;

public class DataShelfTest {

    final String DATA_SHELF_ID = "DataShelfIdentifier";
    final String DATA_TYPE_ID = "DataTypeIdentifier";


    @Test(expected = AssertionError.class)
    public void testNullIdentifier() {
        DataShelf.create(null);
    }


    @Test(expected = AssertionError.class)
    public void testEmptyIdentifier() {
        DataShelf.create("");
    }


    @Test
    public void testGetIdentifier() {
        DataShelf dataShelf = DataShelf.create(DATA_SHELF_ID);
        Assert.assertNotNull(dataShelf);

        Assert.assertArrayEquals(DATA_SHELF_ID.getBytes(), dataShelf.getIdentifier().getBytes());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testInsertExistingDataType() {
        DataShelf dataShelf = DataShelf.create(DATA_SHELF_ID);

        dataShelf.insertDataType(new IDataType() {
            public String getIdentifier() {
                return DATA_TYPE_ID;
            }
        });

        dataShelf.insertDataType(new IDataType() {
            public String getIdentifier() {
                return DATA_TYPE_ID;
            }
        });
    }

}
