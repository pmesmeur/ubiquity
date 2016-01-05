package com.ubiquity.core.datastore.indexes;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ubiquity.core.datastore.Record;

import java.util.Collection;

class MultipleIndex implements IIndex {

    private final Multimap<Object, Record> indexedEntries;

    public MultipleIndex() {
        indexedEntries = HashMultimap.create();
    }

    @Override
    public void insertRecord(Object indexedValue, Record indexedRecord) {
        indexedEntries.put(indexedValue, indexedRecord);
    }

    @Override
    public void removeRecord(Object indexedValue, Record indexedRecord) {
        indexedEntries.remove(indexedValue, indexedRecord);
    }

    @Override
    public Collection<Record> getRecords() {
        return indexedEntries.values();
    }
}
