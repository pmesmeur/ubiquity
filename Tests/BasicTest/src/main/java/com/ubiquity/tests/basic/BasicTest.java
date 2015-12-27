package com.ubiquity.tests.basic;

import com.ubiquity.core.datastore.DataStore;
import com.ubiquity.core.datastore.IDataDefinition;

import java.io.File;

public class BasicTest {

    private final DataStore dataStore;

    protected BasicTest() {
        dataStore = new DataStore();
    }

    public static void main(String[] args) {
        BasicTest basicTest = new BasicTest();
        basicTest.run();
    }

    private void run() {
        String dirName = "Tests/BasicTest/src/main/resources/musicbrainz";
        final File folder = new File(dirName);

        for (final File fileEntry : folder.listFiles()) {
            String fileName = fileEntry.getName();

            if (fileName.endsWith(".dsc")) {
                parseAndInsert(dirName + "/" + fileName);
            }
        }
    }

    private IDataDefinition parseAndInsert(String s) {
        DataDescriptorParser dataDescriptorParser = new DataDescriptorParser();
        dataDescriptorParser.parse(s);

        String shelf = dataDescriptorParser.getShelf();
        String name = dataDescriptorParser.getName();
        IDataDefinition dataDefinition = dataDescriptorParser.getDataDefinition();

        dataStore.insertDataShelf(shelf);
        dataStore.insertData(shelf, dataDefinition);

        System.out.println("Data: {" + shelf + "," + name + "}");
        return null;
    }

    private IDataDefinition createDataDefinition() {
        return null;
    }
}
