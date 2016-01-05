package com.ubiquity.datastorage.utils;

import com.ubiquity.datastorage.kernel.IFieldTemplate;
import com.ubiquity.datastorage.kernel.IRecordTemplate;

import java.util.ArrayList;
import java.util.Collection;

import static com.ubiquity.datastorage.kernel.IFieldTemplate.Kind.PRIMARY;
import static com.ubiquity.datastorage.kernel.IFieldTemplate.Type.STRING;

public class RecordTemplate implements IRecordTemplate {

    private final Collection<IFieldTemplate> fieldTemplate;

    public RecordTemplate() {
        this.fieldTemplate = new ArrayList<IFieldTemplate>();
        fieldTemplate.add(new IFieldTemplate() {
            @Override
            public String getIdentifier() {
                return "DummyField";
            }

            @Override
            public Type getType() {
                return STRING;
            }

            @Override
            public Kind getKind() {
                return PRIMARY;
            }
        });
    }

    @Override
    public String getIdentifier() {
        return "AnIdentifier";
    }

    @Override
    public Collection<IFieldTemplate> getFieldTemplates() {
        return fieldTemplate;
    }
}
