package com.ubiquity.core.datastore.indexes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ubiquity.core.datastore.Entry;
import com.ubiquity.core.datastore.exceptions.ValueOfPrimaryFieldAlreadyInsertedException;

class SingleIndex implements IIndex {

    private final Map<Object, Entry> indexedEntries;

    public SingleIndex() {
        indexedEntries = new HashMap();
    }

    @Override
    public void insertEntry(Object indexedValue, Entry indexedEntry) {
        if (indexedEntries.containsKey(indexedValue)) {
            throw new ValueOfPrimaryFieldAlreadyInsertedException();
        }
        indexedEntries.put(indexedValue, indexedEntry);
    }

    @Override
    public void removeEntry(Object indexedValue, Entry indexedObject) {
        indexedEntries.remove(indexedValue, indexedObject);
    }

    @Override
    public Collection<Entry> getEntry() {
        return indexedEntries.values();
    }
}