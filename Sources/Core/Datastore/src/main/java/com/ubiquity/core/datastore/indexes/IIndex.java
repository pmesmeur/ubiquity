package com.ubiquity.core.datastore.indexes;

import java.util.Collection;

import com.ubiquity.core.datastore.Record;

public interface IIndex {
            void insertRecord(Object indexedValue, Record indexedRecord);

    void removeRecord(Object indexedValue, Record indexedRecord);

    Collection<Record> getRecords();
}
