package com.ubiquity.datastorage.kernel.indexes;

import com.ubiquity.datastorage.kernel.Record;

import java.util.Collection;

public interface IIndex {
            void insertRecord(Object indexedValue, Record indexedRecord);

    void removeRecord(Object indexedValue, Record indexedRecord);

    Collection<Record> getRecords();
}
