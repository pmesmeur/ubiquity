package com.ubiquity.datastorage.kernel.impl.indexes;

import com.ubiquity.datastorage.kernel.impl.Record;
import com.ubiquity.datastorage.kernel.exceptions.ValueOfPrimaryFieldAlreadyInsertedException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class SingleIndex implements IIndex {

    private final Map<Object, Record> indexedEntries;

    public SingleIndex() {
        indexedEntries = new HashMap<Object, Record>();
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
