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

import com.ubiquity.core.datastore.exceptions.RegisteryNotFoundException;

public class DataStoreTest {

    private final int NB_INSERTIONS = 3;
    private DataStore dataStore;


    @Before
    public void init() {
        dataStore = new DataStore();
    }


    @Test
    public void testRegisteriesInsert() {
        testRegisteryInsertions(NB_INSERTIONS);

        Set<String> RegisteriesId = dataStore.getAllRegisteriesId();
        Assert.assertEquals(RegisteriesId.size(), NB_INSERTIONS);
    }


    private void testRegisteryInsertions(int quantity) {
        for (int index = 0; index < quantity; index++) {
            testRegisteryInsertion("id" + index);
        }
    }


    private void testRegisteryInsertion(String identifier) {
        Registry Registery1 = dataStore.insertRegistery(identifier);
        Assert.assertEquals(Registery1, dataStore.getRegistery(identifier));
    }


    @Test(expected = RegisteryNotFoundException.class)
    public void testGetUnexistingRegistery() {
        testRegisteryInsertions(NB_INSERTIONS);
        dataStore.getRegistery("hello-world");
    }


    @Test(expected = AssertionError.class)
    public void testNullNamedRegisteryInsert() {
        dataStore.insertRegistery(null);
    }


    @Test(expected = AssertionError.class)
    public void testEmptyNamedRegisteryInsert() {
        dataStore.insertRegistery("");
    }


    @Test
    public void testInsertData() {
        final String DATA_REGISTERY_NAME = "The.Data.Registery";
        RecordTemplate recordTemplate = new RecordTemplate();

        dataStore.insertRegistery(DATA_REGISTERY_NAME);
        dataStore.insertRegister(DATA_REGISTERY_NAME, recordTemplate);

        Registry Registery = dataStore.getRegistery(DATA_REGISTERY_NAME);
        assertNotNull(Registery);

        Register register = Registery.getRegister(recordTemplate.getIdentifier());
        assertNotNull(register);
    }


    @Test(expected = RegisteryNotFoundException.class)
    public void testInsertDataOnUnknownRegistery() {
        dataStore.insertRegister("ThisRegisteryDoesNotExist", new RecordTemplate());
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
        dataStore.insertRegister("ThisRegisteryDoesNotExist", null);
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