package com.ubiquity.core.datastore.index;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class SingleIndex implements IIndex {

    private final Map<Object, Object> indexedObjects;

    public SingleIndex() {
        indexedObjects = new HashMap();
    }

    @Override
    public void insertObject(Object indexedValue, Object indexedObject) {
        if (indexedObjects.containsKey(indexedValue)) {
            throw new IllegalArgumentException("value already indexed");
        }
        indexedObjects.put(indexedValue, indexedObject);
    }

    @Override
    public Collection<Object> getIndexedObjects() {
        return indexedObjects.values();
    }
}
