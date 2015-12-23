package com.ubiquity.tests.basic;

import com.ubiquity.core.datastore.DataStore;
import com.ubiquity.core.datastore.IDataDefinition;

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
        parseAndInsert("Tests/BasicTest/src/main/resources/musicbrainz/Area.dsc");
    }

    private IDataDefinition parseAndInsert(String s) {
        DataDescriptorParser dataDescriptorParser = new DataDescriptorParser();
        dataDescriptorParser.parse(s);
        String shelf = dataDescriptorParser.getShelf();
        String name = dataDescriptorParser.getName();

        dataStore.insertDataShelf(shelf);
        dataStore.insertData(shelf, dataDescriptorParser.getDataDefinition());

        System.out.println("Data: {" + shelf + "," + name + "}");
        return null;
    }

    private IDataDefinition createDataDefinition() {
        return null;
    }
}
