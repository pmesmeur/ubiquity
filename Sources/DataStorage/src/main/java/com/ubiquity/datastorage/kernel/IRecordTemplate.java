package com.ubiquity.datastorage.kernel;

import java.util.Collection;

public interface IRecordTemplate {
    String getIdentifier();

    Collection<IFieldTemplate> getFieldTemplates();
}
