package com.ubiquity.datastorage.kernel;

import com.ubiquity.datastorage.kernel.exceptions.NullFactoryException;
import com.ubiquity.datastorage.kernel.exceptions.RegisterAlreadyExistsException;
import com.ubiquity.datastorage.kernel.exceptions.RegisterNotFoundException;
import com.ubiquity.datastorage.kernel.interfaces.IRecordFactory;
import com.ubiquity.datastorage.kernel.interfaces.IRecordTemplate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.ubiquity.datastorage.kernel.interfaces.IFieldTemplate.Kind.PRIMARY;
import static com.ubiquity.datastorage.kernel.utils.RecordTemplateHelper.createRecordTempalte;

public class RegistryTest {

    private final String DATA_REGISTRY_ID = "RegistryIdentifier";

    private IRecordFactory recordFactory;


    @Before
    public void init() {
        recordFactory = new RecordFactory();
    }


    @Test(expected = NullFactoryException.class)
    public void testNullFactory() {
        IRecordFactory nullRecordFactory = null;
        Registry.create(nullRecordFactory, "RegistryIdentifier");
    }


    @Test(expected = AssertionError.class)
    public void testNullIdentifier() {
        createRegistry(null);
    }


    @Test(expected = AssertionError.class)
    public void testEmptyIdentifier() {
        createRegistry("");
    }


    private Registry createRegistry(String identifier) {
        return Registry.create(recordFactory, identifier);
    }


    @Test
    public void testGetIdentifier() {
        Registry registry = createRegistry(DATA_REGISTRY_ID);
        Assert.assertNotNull(registry);

        Assert.assertArrayEquals(DATA_REGISTRY_ID.getBytes(),
                registry.getIdentifier().getBytes());
    }


    @Test(expected = RegisterAlreadyExistsException.class)
    public void testInsertExistingRegister() {
        Registry registry = createRegistry(DATA_REGISTRY_ID);

        IRecordTemplate recordTempalte = createRecordTempalte(PRIMARY);

        registry.insertRegister(recordTempalte);
        registry.insertRegister(recordTempalte);
    }


    @Test(expected = AssertionError.class)
    public void testGetRegisterWithNullIdentifier() {
        Registry registry = createRegistry("RegistryIdentifier");
        registry.getRegister(null);
    }


    @Test(expected = RegisterNotFoundException.class)
    public void testGetRegisterWithUnknownIdentifier() {
        Registry registry = createRegistry("RegistryIdentifier");
        registry.getRegister("UnknownRegisterIdentifier");
    }
}
