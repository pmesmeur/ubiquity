package com.ubiquity.tests.basic;

import com.ubiquity.core.datastore.DataStore;
import com.ubiquity.core.datastore.IDataDefinition;
import com.ubiquity.tests.basic.DataDescriptorParser.IDataInsertor;

import java.io.File;

public class BasicTest {

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

        scanFolderAndInsertDataDescriptors(folder);
        scanFolderAndInsertData(folder);
    }

    private void scanFolderAndInsertDataDescriptors(File folder) {
        for (File fileEntry : folder.listFiles()) {
            String fileName = fileEntry.getName();

            if (fileName.endsWith(".dsc")) {
                parseAndInsertDataDescriptor(DIR_NAME + "/" + fileName);
            }
        }
    }

    private void parseAndInsertDataDescriptor(String s) {
        DataDescriptorParser dataDescriptorParser = new DataDescriptorParser();
        dataDescriptorParser.parse(s, new IDataInsertor() {

            public void insert(String shelf, IDataDefinition dataDefinition) {
                dataStore.insertDataShelf(shelf);
                dataStore.insertData(shelf, dataDefinition);
                System.out.println("Data: {" + shelf + "," + dataDefinition.getIdentifier() + "}");
            }
        });
    }

    private void scanFolderAndInsertData(File folder) {
        for (File fileEntry : folder.listFiles()) {
            String fileName = fileEntry.getName();

            if (fileName.endsWith(".txt")) {
                parseAndInsertData(DIR_NAME + "/" + fileName);
            }
        }
    }

    private void parseAndInsertData(String s) {

    }

}
