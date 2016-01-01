package com.ubiquity.core.datastore;

import static com.ubiquity.core.datastore.IFieldTemplate.Kind.PRIMARY;
import static com.ubiquity.core.datastore.utils.RecordTemplateHelper.createRecordTempalte;

import org.junit.Assert;
import org.junit.Test;

import com.ubiquity.core.datastore.exceptions.RegisterAlreadyExistsException;
import com.ubiquity.core.datastore.exceptions.RegisterNotFoundException;

public class DataShelfTest {

    final String DATA_SHELF_ID = "DataShelfIdentifier";

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

    @Test(expected = RegisterAlreadyExistsException.class)
    public void testInsertExistingRegister() {
        DataShelf dataShelf = DataShelf.create(DATA_SHELF_ID);

        IRecordTemplate recordTempalte = createRecordTempalte(PRIMARY);

        dataShelf.insertRegister(recordTempalte);
        dataShelf.insertRegister(recordTempalte);
    }

    @Test(expected = AssertionError.class)
    public void testGetRegisterWithNullIdentifier() {
        DataShelf dataShelf = DataShelf.create("DataShelfIdentifier");
        dataShelf.getRegister(null);
    }

    @Test(expected = RegisterNotFoundException.class)
    public void testGetRegisterWithUnknownIdentifier() {
        DataShelf dataShelf = DataShelf.create("DataShelfIdentifier");
        dataShelf.getRegister("UnknownRegisterIdentifier");
    }
}
