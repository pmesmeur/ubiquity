package com.ubiquity.tests.basic;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import com.google.common.base.Strings;
import com.ubiquity.core.datastore.DataStore;
import com.ubiquity.core.datastore.IFieldTemplate;
import com.ubiquity.core.datastore.IRecordTemplate;
import com.ubiquity.core.datastore.exceptions.ValueOfPrimaryFieldAlreadyInsertedException;

public class BasicTest implements DataParser.TypeProvider {

    private final DataStore dataStore;
    String DIR_NAME = "Tests/BasicTest/src/main/resources/musicbrainz";


    protected BasicTest() {
        dataStore = new DataStore();
    }


    public static void main(String[] args) {
        BasicTest basicTest = new BasicTest();
        basicTest.run();
    }


    private void run() {
        final File folder = new File(DIR_NAME);
        scanFolder(folder);
    }


    private void scanFolder(File folder) {
        for (File fileEntry : folder.listFiles()) {
            String fileName = fileEntry.getName();

            if (fileName.endsWith(".dsc")) {
                insertData(fileName);
            }
        }
    }


    private void insertData(String dscFileName) {
        DataInsertor dataInsertor = new DataInsertor();

        dataInsertor.parseAndInsertDataDescriptor(DIR_NAME + "/" + dscFileName);

        String dataFileName = dscFileName.replace(".dsc", ".txt");
        dataInsertor.parseAndInsertData(DIR_NAME + "/" + dataFileName);
    }

    @Override
    public IFieldTemplate.Type getType(String registry, String identifier, String field) {
        assert!Strings.isNullOrEmpty(registry);
        assert!Strings.isNullOrEmpty(identifier);
        assert!Strings.isNullOrEmpty(field);

        Collection<IFieldTemplate> fieldTemplates = dataStore.getRegistry(registry)
                .getRegister(identifier)
                .getDefinition().getFieldTemplates();

        for (IFieldTemplate fieldTemplate : fieldTemplates) {
            if (fieldTemplate.getName().equals(field)) {
                return fieldTemplate.getType();
            }
        }

        return null;
    }

    private class DataInsertor
            implements DataDescriptorParser.IDataInsertor, DataParser.IDataInsertor {

        private String registry;
        private String identifier;

        public DataInsertor() {
        }

        private void parseAndInsertDataDescriptor(String fileName) {
            DataDescriptorParser dataDescriptorParser = new DataDescriptorParser();
            dataDescriptorParser.parse(fileName, this);
        }

        private void parseAndInsertData(String fileName) {
            DataParser dataParser = new DataParser(registry, identifier);
            dataParser.parse(fileName, BasicTest.this, this);
        }

        @Override
        public void insert(String registry, IRecordTemplate recordTemplate) {
            this.registry = registry;
            this.identifier = recordTemplate.getIdentifier();

            dataStore.insertRegistry(registry);
            dataStore.insertRegister(registry, recordTemplate);
            System.out.println("Data: {" + registry + "," + identifier + "}");
        }

        @Override
        public void insert(Map<String, Object> dataFields) {
            try {
                dataStore.getRegistry(registry).getRegister(identifier).insert(dataFields);
            }
 catch (ValueOfPrimaryFieldAlreadyInsertedException e) {
            }
        }

    }

}
