package com.ubiquity.tests.basic;


import java.util.Map;

public class DataParser {
    public void parse(String fileName, IDataInsertor iDataInsertor) {

    }

    public interface IDataInsertor {
        void insert(String dataShelf, String dataIdentifier, Map<String, Object> dataFields);
    }
}
