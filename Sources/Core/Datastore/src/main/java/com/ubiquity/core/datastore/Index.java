package com.ubiquity.core.datastore;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class Index {

    private final Multimap<Object, Object> indexedObjects;

    public Index() {
        indexedObjects = HashMultimap.create();
    }


    public void insertObject(Object indexedValue, Object indexedObject) {
        indexedObjects.put(indexedValue, indexedObject);
    }

    protected Multimap<Object, Object> getIndexedObjects() {
        return indexedObjects;
    }
}
