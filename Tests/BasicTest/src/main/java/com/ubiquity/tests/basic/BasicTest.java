package com.ubiquity.tests.basic;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import com.google.common.base.Strings;
import com.ubiquity.core.datastore.DataStore;
import com.ubiquity.core.datastore.IDataDefinition;
import com.ubiquity.core.datastore.IFieldDefinition;
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
            DataInsertor dataInsertor = new DataInsertor();

            if (fileName.endsWith(".dsc")) {
                String fullFileName = DIR_NAME + "/" + fileName;
                parseAndInsertDataDescriptor(fullFileName, dataInsertor);

                String dataFileName = fullFileName.replace(".dsc", ".txt");
                parseAndInsertData(dataFileName, dataInsertor);
            }
        }
    }

    private void parseAndInsertDataDescriptor(String fileName,
            DataDescriptorParser.IDataInsertor dataInsertor) {
        DataDescriptorParser dataDescriptorParser = new DataDescriptorParser();
        dataDescriptorParser.parse(fileName, dataInsertor);
    }

    private void parseAndInsertData(String fileName, DataInsertor dataInsertor) {
        DataParser dataParser = new DataParser(dataInsertor.getShelf(),
                dataInsertor.getIdentifier());
        dataParser.parse(fileName, this, dataInsertor);
    }

    public IFieldDefinition.DataType getType(String shelf, String identifier, String field) {
        assert!Strings.isNullOrEmpty(shelf);
        assert!Strings.isNullOrEmpty(identifier);
        assert!Strings.isNullOrEmpty(field);

        Collection<IFieldDefinition> fieldsDef = dataStore.getDataShelf(shelf).getData(identifier)
                .getDefinition().getFieldDefinitions();

        for (IFieldDefinition fieldDef : fieldsDef) {
            if (fieldDef.getName().equals(field)) {
                return fieldDef.getType();
            }
        }

        return null;
    }

    private class DataInsertor
            implements DataDescriptorParser.IDataInsertor, DataParser.IDataInsertor {

        private String shelf;
        private String identifier;

        public DataInsertor() {
        }

        public void insert(String shelf, IDataDefinition dataDefinition) {
            this.shelf = shelf;
            this.identifier = dataDefinition.getIdentifier();

            dataStore.insertDataShelf(shelf);
            dataStore.insertData(shelf, dataDefinition);
            System.out.println("Data: {" + shelf + "," + identifier + "}");
        }

        public void insert(Map<String, Object> dataFields) {
            try {
                dataStore.getDataShelf(shelf).getData(identifier).insert(dataFields);
            }
 catch (ValueOfPrimaryFieldAlreadyInsertedException e) {
            }
        }

        public String getShelf() {
            return shelf;
        }

        public String getIdentifier() {
            return identifier;
        }
    }


}
