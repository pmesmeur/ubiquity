package com.ubiquity.core.datastore.index;

import java.util.Collection;

import com.ubiquity.core.datastore.Entry;

public interface IIndex {
    void insertObject(Object indexedValue, Object indexedObject);

    void removeObject(Object indexedValue, Entry indexedObject);
    Collection<Object> getIndexedObjects();
}
