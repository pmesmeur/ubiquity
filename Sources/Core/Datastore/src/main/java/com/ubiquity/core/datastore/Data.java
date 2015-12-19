package com.ubiquity.core.datastore;

import com.ubiquity.core.datastore.index.IIndex;
import com.ubiquity.core.datastore.index.IndexFactory;

import java.util.*;

import static com.ubiquity.core.datastore.index.IndexFactory.Kind.MULTIPLE;
import static com.ubiquity.core.datastore.index.IndexFactory.Kind.UNIQUE;


public class Data {

    private final IDataDefinition dataDefinition;
    private final Collection<Entry> entries;
    private final Map<String, IIndex> indexes;

    public Data(IDataDefinition dataDefinition) {
        DataDefinitionValidator.validate(dataDefinition);

        this.dataDefinition = dataDefinition; /// TODO: make a copy
        this.entries = new ArrayList<Entry>();
        this.indexes = buildIndexes(dataDefinition);
    }


    public void insert(Map<String, Object> entryValues) {
        Entry entry = new Entry(dataDefinition, entryValues);
        populateIndexes(entryValues, entry);
        entries.add(entry);
    }


    private void populateIndexes(Map<String, Object> entryValues, Entry entry) {
        for (Map.Entry<String, IIndex> indexEntry : indexes.entrySet()) {
            populateIndex(entryValues, entry, indexEntry);
        }
    }

    private void populateIndex(Map<String, Object> entryValues, Entry entry, Map.Entry<String, IIndex> indexEntry) {
        String attributeName = indexEntry.getKey();
        IIndex index = indexEntry.getValue();

        Object v = entryValues.get(attributeName);
        index.insertObject(v, entry);
    }


    private static Map<String, IIndex> buildIndexes(IDataDefinition dataDefinition) {
        Map<String, IIndex> indexes = new HashMap<String, IIndex>();

        for (IFieldDefinition fieldDefinition : dataDefinition.getFieldDefinitions()) {
            buildIndex(indexes, fieldDefinition);
        }

        return (indexes);
    }

    private static void buildIndex(Map<String, IIndex> indexes, IFieldDefinition fieldDefinition) {
        IFieldDefinition.Kind kind = fieldDefinition.getKind();
        if (kind.isIndexed()) {
            indexes.put(fieldDefinition.getName(), IndexFactory.createIndex(kind.isUnique() ? UNIQUE : MULTIPLE));
        }
    }

    protected Map<String, IIndex> getIndexes() {
        return indexes;
    }

    protected Collection<Entry> getEntries() {
        return entries;
    }
}
