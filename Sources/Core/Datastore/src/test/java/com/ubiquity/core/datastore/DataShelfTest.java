package com.ubiquity.core.datastore;

import com.ubiquity.core.datastore.exceptions.DataAlreadyExistsException;
import com.ubiquity.core.datastore.exceptions.DataNotFoundException;
import org.junit.Assert;
import org.junit.Test;

import static com.ubiquity.core.datastore.IFieldDefinition.Kind.PRIMARY;
import static com.ubiquity.core.datastore.utils.DataDefinitionHelper.createEntryDataDefinition;

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

    @Test(expected = DataAlreadyExistsException.class)
    public void testInsertExistingData() {
        DataShelf dataShelf = DataShelf.create(DATA_SHELF_ID);

        IDataDefinition dataDefinition = createEntryDataDefinition(PRIMARY);

        dataShelf.insertData(dataDefinition);
        dataShelf.insertData(dataDefinition);
    }

    @Test(expected = AssertionError.class)
    public void testGetDataWithNullIdentifier() {
        DataShelf dataShelf = DataShelf.create("DataShelfIdentifier");
        dataShelf.getData(null);
    }

    @Test(expected = DataNotFoundException.class)
    public void testGetDataWithUnknownIdentifier() {
        DataShelf dataShelf = DataShelf.create("DataShelfIdentifier");
        dataShelf.getData("DataIdentifier");
    }
}
