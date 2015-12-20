package com.ubiquity.core.datastore.indexes;

import java.util.Collection;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ubiquity.core.datastore.Entry;

class MultipleIndex implements IIndex {

    private final Multimap<Object, Entry> indexedEntries;

    public MultipleIndex() {
        indexedEntries = HashMultimap.create();
    }

    @Override
    public void insertEntry(Object indexedValue, Entry indexedEntry) {
        indexedEntries.put(indexedValue, indexedEntry);
    }

    @Override
    public void removeEntry(Object indexedValue, Entry indexedEntry) {
        indexedEntries.remove(indexedValue, indexedEntry);
    }

    @Override
    public Collection<Entry> getEntry() {
        return indexedEntries.values();
    }
}
