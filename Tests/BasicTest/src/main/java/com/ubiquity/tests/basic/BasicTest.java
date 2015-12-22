package com.ubiquity.tests.basic;

import com.ubiquity.core.datastore.DataShelf;
import com.ubiquity.core.datastore.DataStore;
import com.ubiquity.core.datastore.IDataDefinition;

public class BasicTest {

    protected BasicTest() {
    }

    public static void main(String[] args) {
        BasicTest basicTest = new BasicTest();
        basicTest.run();
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
