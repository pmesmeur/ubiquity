package com.ubiquity.core.datastore;

import static com.ubiquity.core.datastore.IFieldTemplate.Kind.PRIMARY;
import static com.ubiquity.core.datastore.IFieldTemplate.Type.STRING;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.ubiquity.core.datastore.exceptions.RegistryNotFoundException;

public class DataStoreTest {

    private final int NB_INSERTIONS = 3;
    private DataStore dataStore;


    @Before
    public void init() {
        dataStore = new DataStore();
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
        Registry registry1 = dataStore.insertRegistry(identifier);
        Assert.assertEquals(registry1, dataStore.getRegistry(identifier));
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
        final String DATA_Registry_NAME = "The.Data.Registry";
        RecordTemplate recordTemplate = new RecordTemplate();

        dataStore.insertRegistry(DATA_Registry_NAME);
        dataStore.insertRegister(DATA_Registry_NAME, recordTemplate);

        Registry registry = dataStore.getRegistry(DATA_Registry_NAME);
        assertNotNull(registry);

        Register register = registry.getRegister(recordTemplate.getIdentifier());
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


    private class RecordTemplate implements IRecordTemplate {

        private final Collection<IFieldTemplate> fieldTemplate;

        public RecordTemplate() {
            this.fieldTemplate = new ArrayList<IFieldTemplate>();
            fieldTemplate.add(new IFieldTemplate() {
                @Override
                public String getName() {
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