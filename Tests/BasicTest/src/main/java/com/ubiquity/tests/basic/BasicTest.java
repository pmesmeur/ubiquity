package com.ubiquity.tests.basic;

import com.ubiquity.core.datastore.DataShelf;
import com.ubiquity.core.datastore.DataStore;
import com.ubiquity.core.datastore.IDataDefinition;

import java.io.FileNotFoundException;
import java.io.IOException;

public class BasicTest {

    protected BasicTest() {
    }

    public static void main(String[] args) {
        BasicTest basicTest = new BasicTest();
        parse("Tests/BasicTest/src/main/resources/musicbrainz/Area.dsc");
        basicTest.run();
    }

    private static void parse(String s) {
        DataDescriptorParser dataDescriptorParser = new DataDescriptorParser();
        dataDescriptorParser.parse(s);
        String store = dataDescriptorParser.getStore();
        String name = dataDescriptorParser.getName();
        System.out.println("Data: {" + store + "," + name + "}");
    }

    private void run() {
        DataStore dataStore = new DataStore();
        String dataShelfName = BasicTest.class.getPackage().toString();

        DataShelf dataShelf = dataStore.addDataShelf(dataShelfName);
        // dataShelf.insertData();
    }

    private IDataDefinition createDataDefinition() {
        return null;
    }
}
