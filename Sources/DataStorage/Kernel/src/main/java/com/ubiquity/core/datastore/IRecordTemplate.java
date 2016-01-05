package com.ubiquity.core.datastore;

import java.util.Collection;

public interface IRecordTemplate {
    String getIdentifier();

    Collection<IFieldTemplate> getFieldTemplates();
}
