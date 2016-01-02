package com.ubiquity.core.datastore.indexes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ubiquity.core.datastore.Record;
import com.ubiquity.core.datastore.exceptions.ValueOfPrimaryFieldAlreadyInsertedException;

class SingleIndex implements IIndex {

    private final Map<Object, Record> indexedEntries;

    public SingleIndex() {
        indexedEntries = new HashMap();
    }

    @Override
    public void insertRecord(Object indexedValue, Record indexedRecord) {
        if (indexedEntries.containsKey(indexedValue)) {
            throw new ValueOfPrimaryFieldAlreadyInsertedException();
        }
        indexedEntries.put(indexedValue, indexedRecord);
    }

    @Override
    public void removeRecord(Object indexedValue, Record indexedObject) {
        indexedEntries.remove(indexedValue, indexedObject);
    }

    @Override
    public Collection<Record> getRecords() {
        return indexedEntries.values();
    }
}
