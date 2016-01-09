package com.ubiquity.datastorage.kernel;

import com.ubiquity.datastorage.kernel.exceptions.RegisterAlreadyExistsException;
import com.ubiquity.datastorage.kernel.exceptions.RegisterNotFoundException;
import com.ubiquity.datastorage.kernel.interfaces.IRegistry;
import org.junit.Assert;
import org.junit.Test;

import static com.ubiquity.datastorage.kernel.IFieldTemplate.Kind.PRIMARY;
import static com.ubiquity.datastorage.kernel.utils.RecordTemplateHelper.createRecordTempalte;

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
        IRegistry registry = Registry.create(DATA_Registry_ID);
        Assert.assertNotNull(registry);

        Assert.assertArrayEquals(DATA_Registry_ID.getBytes(),
                registry.getIdentifier().getBytes());
    }

    @Test(expected = RegisterAlreadyExistsException.class)
    public void testInsertExistingRegister() {
        IRegistry registry = Registry.create(DATA_Registry_ID);

        IRecordTemplate recordTempalte = createRecordTempalte(PRIMARY);

        registry.insertRegister(recordTempalte);
        registry.insertRegister(recordTempalte);
    }

    @Test(expected = AssertionError.class)
    public void testGetRegisterWithNullIdentifier() {
        IRegistry registry = Registry.create("RegistryIdentifier");
        registry.getRegister(null);
    }

    @Test(expected = RegisterNotFoundException.class)
    public void testGetRegisterWithUnknownIdentifier() {
        IRegistry registry = Registry.create("RegistryIdentifier");
        registry.getRegister("UnknownRegisterIdentifier");
    }
}
