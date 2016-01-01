package com.ubiquity.core.datastore;

import java.util.Collection;

public interface IDataDefinition {
    String getIdentifier();

    Collection<IFieldTemplate> getFieldTemplates();
}
