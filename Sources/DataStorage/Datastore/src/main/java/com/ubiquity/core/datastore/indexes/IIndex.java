package com.ubiquity.core.datastore.indexes;

import com.ubiquity.core.datastore.Record;

import java.util.Collection;

public interface IIndex {
            void insertRecord(Object indexedValue, Record indexedRecord);

    void removeRecord(Object indexedValue, Record indexedRecord);

    Collection<Record> getRecords();
}
