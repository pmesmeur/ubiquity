package com.ubiquity.core.datastore;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.base.Strings;
import com.ubiquity.core.datastore.exceptions.DataShelfNotFoundException;


public class DataStore {

    private final Map<String, DataShelf> dataShelves = new ConcurrentHashMap<String, DataShelf>();


    public DataShelf insertDataShelf(String identifier) {
        DataShelf dataShelf = DataShelf.create(identifier);
        dataShelves.put(identifier, dataShelf);
        return dataShelf;
    }


    public DataShelf getDataShelf(String identifier) {
        DataShelf dataShelf = dataShelves.get(identifier);
        if (dataShelf == null) {
            throw new DataShelfNotFoundException(identifier);
        }
        return dataShelf;
    }


    public Set<String> getAllShelvesId() {
        return dataShelves.keySet();
    }


    public void insertData(String shelf, IDataDefinition dataDefinition) {
        assert!Strings.isNullOrEmpty(shelf);
        assert dataDefinition != null;

        DataShelf dataShelf = getDataShelf(shelf);
        dataShelf.insertData(dataDefinition);
    }
}
