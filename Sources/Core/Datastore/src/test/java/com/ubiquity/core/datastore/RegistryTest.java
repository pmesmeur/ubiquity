package com.ubiquity.core.datastore;

import static com.ubiquity.core.datastore.IFieldTemplate.Kind.PRIMARY;
import static com.ubiquity.core.datastore.utils.RecordTemplateHelper.createRecordTempalte;

import org.junit.Assert;
import org.junit.Test;

import com.ubiquity.core.datastore.exceptions.RegisterAlreadyExistsException;
import com.ubiquity.core.datastore.exceptions.RegisterNotFoundException;

public class RegistryTest {

    final String DATA_Registry_ID = "RegistryIdentifier";

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
        Registry registry = Registry.create(DATA_Registry_ID);
        Assert.assertNotNull(registry);

        Assert.assertArrayEquals(DATA_Registry_ID.getBytes(),
                registry.getIdentifier().getBytes());
    }

    @Test(expected = RegisterAlreadyExistsException.class)
    public void testInsertExistingRegister() {
        Registry registry = Registry.create(DATA_Registry_ID);

        IRecordTemplate recordTempalte = createRecordTempalte(PRIMARY);

        registry.insertRegister(recordTempalte);
        registry.insertRegister(recordTempalte);
    }

    @Test(expected = AssertionError.class)
    public void testGetRegisterWithNullIdentifier() {
        Registry registry = Registry.create("RegistryIdentifier");
        registry.getRegister(null);
    }

    @Test(expected = RegisterNotFoundException.class)
    public void testGetRegisterWithUnknownIdentifier() {
        Registry registry = Registry.create("RegistryIdentifier");
        registry.getRegister("UnknownRegisterIdentifier");
    }
}