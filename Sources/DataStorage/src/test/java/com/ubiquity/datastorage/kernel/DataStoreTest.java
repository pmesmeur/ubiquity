package com.ubiquity.datastorage.kernel;

import com.ubiquity.datastorage.kernel.exceptions.NullFactoryException;
import com.ubiquity.datastorage.kernel.exceptions.RegistryNotFoundException;
import com.ubiquity.datastorage.kernel.interfaces.*;
import com.ubiquity.datastorage.utils.RecordTemplate;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertNotNull;

public class DataStoreTest {

    private final int NB_INSERTIONS = 3;
    private DataStore dataStore;


    @Before
    public void init() {
        RecordFactory recordFactory = new RecordFactory();
        RegistryFactory registryFactory = new RegistryFactory(recordFactory);

        dataStore = new DataStore(registryFactory);
    }


    @Test(expected = NullFactoryException.class)
    public void testNullFactory() {
        IRegistryFactory nullRegistryFactory = null;
        new DataStore(nullRegistryFactory);
    }


    @Test
    public void testRegistriesInsert() {
        testRegistryInsertions(NB_INSERTIONS);

        Set<String> RegistriesId = dataStore.getAllRegistriesId();
        Assert.assertEquals(RegistriesId.size(), NB_INSERTIONS);
    }


    private void testRegistryInsertions(int quantity) {
        for (int index = 0; index < quantity; index++) {
            testRegistryInsertion("id" + index);
        }
    }


    private void testRegistryInsertion(String identifier) {
        IRegistry registry = dataStore.insertRegistry(identifier);
        Assert.assertEquals(registry, dataStore.getRegistry(identifier));
    }


    @Test(expected = RegistryNotFoundException.class)
    public void testGetUnexistingRegistry() {
        testRegistryInsertions(NB_INSERTIONS);
        dataStore.getRegistry("hello-world");
    }


    @Test(expected = AssertionError.class)
    public void testNullNamedRegistryInsert() {
        dataStore.insertRegistry(null);
    }


    @Test(expected = AssertionError.class)
    public void testEmptyNamedRegistryInsert() {
        dataStore.insertRegistry("");
    }


    @Test
    public void testInsertData() {
        final String DATA_REGISTRY_NAME = "The.Data.Registry";
        RecordTemplate recordTemplate = new RecordTemplate();

        dataStore.insertRegistry(DATA_REGISTRY_NAME);
        dataStore.insertRegister(DATA_REGISTRY_NAME, recordTemplate);

        IRegistry registry = dataStore.getRegistry(DATA_REGISTRY_NAME);
        assertNotNull(registry);

        IRegister register = registry.getRegister(recordTemplate.getIdentifier());
        assertNotNull(register);
    }


    @Test(expected = RegistryNotFoundException.class)
    public void testInsertDataOnUnknownRegistry() {
        dataStore.insertRegister("ThisRegistryDoesNotExist", new RecordTemplate());
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
        dataStore.insertRegister("ThisRegistryDoesNotExist", null);
    }

}