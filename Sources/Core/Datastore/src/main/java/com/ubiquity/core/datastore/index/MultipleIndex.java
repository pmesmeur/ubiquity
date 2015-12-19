package com.ubiquity.core.datastore.index;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;

class MultipleIndex implements IIndex {

    private final Multimap<Object, Object> indexedObjects;

    public MultipleIndex() {
        indexedObjects = HashMultimap.create();
    }

    @Override
    public void insertObject(Object indexedValue, Object indexedObject) {
        indexedObjects.put(indexedValue, indexedObject);
    }

    @Override
    public Collection<Object> getIndexedObjects() {
        return indexedObjects.values();
    }
}
