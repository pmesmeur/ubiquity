package com.ubiquity.core.datastore;

import static com.ubiquity.core.datastore.IFieldTemplate.Kind.PRIMARY;
import static com.ubiquity.core.datastore.utils.RecordTemplateHelper.createRecordTempalte;

import org.junit.Assert;
import org.junit.Test;

import com.ubiquity.core.datastore.exceptions.RegisterAlreadyExistsException;
import com.ubiquity.core.datastore.exceptions.RegisterNotFoundException;

public class RegisteryTest {

    final String DATA_REGISTERY_ID = "RegisteryIdentifier";

    @Test(expected = AssertionError.class)
    public void testNullIdentifier() {
        Registry.create(null);
    }

    @Test(expected = AssertionError.class)
    public void testEmptyIdentifier() {
        Registry.create("");
    }

    @Test
    public void testGetIdentifier() {
        Registry Registery = Registry.create(DATA_REGISTERY_ID);
        Assert.assertNotNull(Registery);

        Assert.assertArrayEquals(DATA_REGISTERY_ID.getBytes(),
                Registery.getIdentifier().getBytes());
    }

    @Test(expected = RegisterAlreadyExistsException.class)
    public void testInsertExistingRegister() {
        Registry Registery = Registry.create(DATA_REGISTERY_ID);

        IRecordTemplate recordTempalte = createRecordTempalte(PRIMARY);

        Registery.insertRegister(recordTempalte);
        Registery.insertRegister(recordTempalte);
    }

    @Test(expected = AssertionError.class)
    public void testGetRegisterWithNullIdentifier() {
        Registry Registery = Registry.create("RegisteryIdentifier");
        Registery.getRegister(null);
    }

    @Test(expected = RegisterNotFoundException.class)
    public void testGetRegisterWithUnknownIdentifier() {
        Registry Registery = Registry.create("RegisteryIdentifier");
        Registery.getRegister("UnknownRegisterIdentifier");
    }
}
