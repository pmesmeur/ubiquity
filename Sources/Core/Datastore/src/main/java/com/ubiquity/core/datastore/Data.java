package com.ubiquity.core.datastore;

import java.util.*;


public class Data {

    private final IDataDefinition dataDefinition;
    private final Collection<Entry> entries;
    private final Map<String, Index> indexes;

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
        for (Map.Entry<String, Index> indexEntry : indexes.entrySet()) {
            String attributeName = indexEntry.getKey();
            Index index = indexEntry.getValue();

            Object v = entryValues.get(attributeName);
            index.insertObject(v, entry);
        }
    }


    private static Map<String, Index> buildIndexes(IDataDefinition dataDefinition) {
        Map<String, Index> indexes = new HashMap<String, Index>();

        for (IFieldDefinition fieldDefinition : dataDefinition.getFieldDefinitions()) {
            if (fieldDefinition.isIndexed()) {
                indexes.put(fieldDefinition.getName(), new Index());
            }
        }

        return (indexes);
    }

    protected Map<String, Index> getIndexes() {
        return indexes;
    }

    protected Collection<Entry> getEntries() {
        return entries;
    }
}
