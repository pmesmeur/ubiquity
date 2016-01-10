package com.ubiquity.datastorage.kernel.interfaces;

import java.util.Collection;

public interface IRecordTemplate {
    String getIdentifier();

    Collection<IFieldTemplate> getFieldTemplates();
}
