package com.ubiquity.core.datastore;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class DataStore {

    private final Map<String, DataShelf> dataShelves = new ConcurrentHashMap<String, DataShelf>();


    public DataShelf addDataShelf(String identifier) {
        DataShelf dataShelf = DataShelf.create(identifier);
        dataShelves.put(identifier, dataShelf);
        return dataShelf;
    }


    public DataShelf getDataShelf(String identifier) {
        DataShelf dataShelf = dataShelves.get(identifier);
        if (dataShelf == null) {
            throw new NoSuchElementException("Cannot find shelf \"" + identifier + "\"");
        }
        return dataShelf;
    }


    public Set<String> getAllShlevesId() {
        return dataShelves.keySet();
    }
}
