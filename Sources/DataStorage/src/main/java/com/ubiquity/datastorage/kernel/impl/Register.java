package com.ubiquity.datastorage.kernel.impl;

import com.ubiquity.datastorage.kernel.impl.indexes.IIndex;
import com.ubiquity.datastorage.kernel.impl.indexes.IndexFactory;
import com.ubiquity.datastorage.kernel.interfaces.IFieldTemplate;
import com.ubiquity.datastorage.kernel.interfaces.IRecordTemplate;
import com.ubiquity.datastorage.kernel.interfaces.IRegister;
import com.ubiquity.datastorage.kernel.interfaces.IRegisterListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class Register implements IRegister {

    private final IRecordTemplate recordTemplate;
    private final Collection<Record> entries;
    private final Map<String, IIndex> indexes;
    private final RegisterNotifier registerNotifier;

    private Register(IRecordTemplate recordTemplate) {
        RecordTemplateValidator.validate(recordTemplate);

        this.recordTemplate = recordTemplate; /// TODO: make a copy
        this.entries = new ArrayList<Record>();
        this.indexes = buildIndexes(recordTemplate);
        registerNotifier = new RegisterNotifier();
    }

    public static Register create(IRecordTemplate recordTemplate) {
        return new Register(recordTemplate);
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
            indexes.put(fieldTemplate.getIdentifier(),
                    IndexFactory.createIndex(kind.isUnique() ? IndexFactory.Kind.UNIQUE : IndexFactory.Kind.MULTIPLE));
        }
    }


    public void addListener(IRegisterListener registerListener) {
        registerNotifier.addListener(registerListener);
    }


    public void removeListener(IRegisterListener registerListener) {
        registerNotifier.removeListener(registerListener);
    }


    @Override
    public IRecordTemplate getDefinition() {
        return recordTemplate;
    }


    @Override
    public void insert(Map<String, Object> recordFields) {
        Record record = new Record(recordTemplate, recordFields);
        populateIndexes(recordFields, record);
        entries.add(record);
        registerNotifier.recordInserted(recordFields);
    }


    private void populateIndexes(Map<String, Object> recordFields, Record record) {
        try {
            tryPopulateIndexes(recordFields, record);
        }

        catch (RuntimeException e) {
            unpopulateIndexes(recordFields, record);
            throw e;
        }
    }


    private void tryPopulateIndexes(Map<String, Object> recordFields, Record record) {
        for (Map.Entry<String, IIndex> indexEntry : indexes.entrySet()) {
            populateIndex(indexEntry, recordFields, record);
        }
    }


    private void populateIndex(Map.Entry<String, IIndex> indexEntry,
            Map<String, Object> recordFields, Record record) {
        String attributeName = indexEntry.getKey();
        IIndex index = indexEntry.getValue();

        Object indexedObject = recordFields.get(attributeName);
        index.insertRecord(indexedObject, record);
    }

    private void unpopulateIndexes(Map<String, Object> recordFields, Record record) {
        for (Map.Entry<String, IIndex> indexEntry : indexes.entrySet()) {
            unpopulateIndex(indexEntry, recordFields, record);
        }
    }

    private void unpopulateIndex(Map.Entry<String, IIndex> indexEntry,
            Map<String, Object> entryValues, Record record) {
        String attributeName = indexEntry.getKey();
        IIndex index = indexEntry.getValue();

        Object indexedObject = entryValues.get(attributeName);
        index.removeRecord(indexedObject, record);
    }

    protected Map<String, IIndex> getIndexes() {
        return indexes;
    }

    protected Collection<Record> getEntries() {
        return entries;
    }
}
