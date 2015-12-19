package com.ubiquity.core.datastore.index;

import java.util.Collection;

public interface IIndex {
    void insertObject(Object indexedValue, Object indexedObject);
    Collection<Object> getIndexedObjects();
}
