package com.ubiquity.datastorage.kernel;

import com.ubiquity.datastorage.kernel.exceptions.NullFactoryException;
import com.ubiquity.datastorage.kernel.exceptions.RegistryNotFoundException;
import com.ubiquity.datastorage.kernel.interfaces.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.ubiquity.datastorage.kernel.interfaces.IFieldTemplate.Kind.PRIMARY;
import static com.ubiquity.datastorage.kernel.interfaces.IFieldTemplate.Type.STRING;
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
    public void testListenersAdditionAndRemoval() {
        IDataStoreListener listener1 = createDataStoreListener(null);
        IDataStoreListener listener2 = createDataStoreListener(null);
        IDataStoreListener listener3 = createDataStoreListener(null);

        dataStore.addListener(listener1);
        dataStore.addListener(listener2);
        dataStore.addListener(listener3);

        Assert.assertEquals(dataStore.getNbListeners(), 3);

        dataStore.removeListener(listener1);
        dataStore.removeListener(listener2);
        dataStore.removeListener(listener3);

        Assert.assertEquals(dataStore.getNbListeners(), 0);
    }


    private IDataStoreListener createDataStoreListener(final Set<String> notifiedRegistryIds) {
        return new IDataStoreListener() {
            @Override
            public void onRegistryInserted(String registryId) {
                notifiedRegistryIds.add(registryId);
            }

            @Override
            public void onRegistryDeleted(String registryId) {
                notifiedRegistryIds.remove(registryId);
            }
        };
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


    private void testRegistryDeletions(int quantity) {
        for (int index = 0; index < quantity; index++) {
            testRegistryDeletion("id" + index);
        }
    }


    private void testRegistryDeletion(String identifier) {
        IRegistry registry = dataStore.deleteRegistry(identifier);
        assertRegistryWasDeleted(registry);
    }

    private void assertRegistryWasDeleted(IRegistry registry) {
        try {
            dataStore.getRegistry(registry.getIdentifier());
        } catch (RegistryNotFoundException e) {
            return;
        }
        Assert.fail();
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


    @Test
    public void testRegistriesInsertNotification() {
        Set<String> notifiedRegistryIds = new HashSet<>();

        dataStore.addListener(createDataStoreListener(notifiedRegistryIds));
        testRegistryInsertions(NB_INSERTIONS);

        Assert.assertEquals(notifiedRegistryIds.size(), NB_INSERTIONS);
    }


    @Test
    public void testRegistriesDeleteNotification() {
        Set<String> notifiedRegistryIds = new HashSet<>();

        dataStore.addListener(createDataStoreListener(notifiedRegistryIds));
        testRegistryInsertions(NB_INSERTIONS);
        testRegistryDeletions(NB_INSERTIONS - 1);

        Assert.assertEquals(notifiedRegistryIds.size(), 1);
    }


    private class RecordTemplate implements IRecordTemplate {

        private final Collection<IFieldTemplate> fieldTemplate;

        public RecordTemplate() {
            this.fieldTemplate = new ArrayList<IFieldTemplate>();
            fieldTemplate.add(new IFieldTemplate() {
                @Override
                public String getIdentifier() {
                    return "DummyField";
                }

                @Override
                public Type getType() {
                    return STRING;
                }

                @Override
                public Kind getKind() {
                    return PRIMARY;
                }
            });
        }

        @Override
        public String getIdentifier() {
            return "AnIdentifier";
        }


        @Override
        public Collection<IFieldTemplate> getFieldTemplates() {
            return fieldTemplate;
        }
    }

}