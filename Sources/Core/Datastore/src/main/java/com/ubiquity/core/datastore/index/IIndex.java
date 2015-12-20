package com.ubiquity.core.datastore.index;

import java.util.Collection;

import com.ubiquity.core.datastore.Entry;

public interface IIndex {
            void insertEntry(Object indexedValue, Entry indexedEntry);

    void removeEntry(Object indexedValue, Entry indexedEntry);

    Collection<Entry> getEntry();
}
