package com.ubiquity.core.datastore;

import static com.ubiquity.core.datastore.indexes.IndexFactory.Kind.MULTIPLE;
import static com.ubiquity.core.datastore.indexes.IndexFactory.Kind.UNIQUE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ubiquity.core.datastore.indexes.IIndex;
import com.ubiquity.core.datastore.indexes.IndexFactory;


public class Register {

    private final IRecordTemplate recordTemplate;
    private final Collection<Entry> entries;
    private final Map<String, IIndex> indexes;

    public Register(IRecordTemplate recordTemplate) {
        RecordTemplateValidator.validate(recordTemplate);

        this.recordTemplate = recordTemplate; /// TODO: make a copy
        this.entries = new ArrayList<Entry>();
        this.indexes = buildIndexes(recordTemplate);
    }

    private static Map<String, IIndex> buildIndexes(IRecordTemplate recordTemplate) {
        Map<String, IIndex> indexes = new HashMap<String, IIndex>();

        for (IFieldTemplate fieldTemplate : recordTemplate.getFieldTemplates()) {
            buildIndex(indexes, fieldTemplate);
        }

        return (indexes);
    }

    private static void buildIndex(Map<String, IIndex> indexes, IFieldTemplate fieldTemplate) {
        IFieldTemplate.Kind kind = fieldTemplate.getKind();
        if (kind.isIndexed()) {
            indexes.put(fieldTemplate.getName(),
                    IndexFactory.createIndex(kind.isUnique() ? UNIQUE : MULTIPLE));
        }
    }

    public IRecordTemplate getDefinition() {
        return recordTemplate;
    }

    public void insert(Map<String, Object> entryValues) {
        Entry entry = new Entry(recordTemplate, entryValues);
        populateIndexes(entryValues, entry);
        entries.add(entry);
    }

    private void populateIndexes(Map<String, Object> entryValues, Entry entry) {
        try {
            for (Map.Entry<String, IIndex> indexEntry : indexes.entrySet()) {
                populateIndex(indexEntry, entryValues, entry);
            }
        }

        catch (RuntimeException e) {
            for (Map.Entry<String, IIndex> indexEntry : indexes.entrySet()) {
                unpopulateIndex(indexEntry, entryValues, entry);
            }
            throw e;
        }
    }

    private void populateIndex(Map.Entry<String, IIndex> indexEntry,
            Map<String, Object> entryValues, Entry entry) {
        String attributeName = indexEntry.getKey();
        IIndex index = indexEntry.getValue();

        Object indexedObject = entryValues.get(attributeName);
        index.insertEntry(indexedObject, entry);
    }

    private void unpopulateIndex(Map.Entry<String, IIndex> indexEntry,
            Map<String, Object> entryValues, Entry entry) {
        String attributeName = indexEntry.getKey();
        IIndex index = indexEntry.getValue();

        Object indexedObject = entryValues.get(attributeName);
        index.removeEntry(indexedObject, entry);
    }

    protected Map<String, IIndex> getIndexes() {
        return indexes;
    }

    protected Collection<Entry> getEntries() {
        return entries;
    }
}