package com.ubiquity.datastorage.kernel.impl;

import com.google.common.base.Strings;
import com.ubiquity.datastorage.kernel.exceptions.DoubleTypeCannotBeUniqueException;
import com.ubiquity.datastorage.kernel.exceptions.NoPrimaryFieldException;
import com.ubiquity.datastorage.kernel.exceptions.ObjectTypeCannotBeAnIndexException;
import com.ubiquity.datastorage.kernel.interfaces.IFieldTemplate;
import com.ubiquity.datastorage.kernel.interfaces.IRecordTemplate;

import java.util.Collection;

class RecordTemplateValidator {

    private RecordTemplateValidator() {
    }

    public static void validate(IRecordTemplate recordTemplate) {
        assert recordTemplate != null;

        validateIdentifier(recordTemplate.getIdentifier());
        validateFieldTempates(recordTemplate.getIdentifier(),
                recordTemplate.getFieldTemplates());
    }

    private static void validateIdentifier(String identifier) {
        assert !Strings.isNullOrEmpty(identifier);
    }


    private static void validateFieldTempates(String identifier,
            Collection<IFieldTemplate> fieldTemplates) {
        assert fieldTemplates != null;
        assert fieldTemplates.size() > 0;

        boolean hasAtLeastOneUniqueField = false;

        for (IFieldTemplate fieldTemplate : fieldTemplates) {
            hasAtLeastOneUniqueField |= fieldTemplate.getKind().isUnique();
            validateFieldTemplate(fieldTemplate);
        }

        if (!hasAtLeastOneUniqueField) {
            throw new NoPrimaryFieldException(identifier);
        }
    }

    private static void validateFieldTemplate(IFieldTemplate fieldTemplate) {
        String fieldName = fieldTemplate.getIdentifier();
        assert!Strings.isNullOrEmpty(fieldName);

        if (fieldTemplate.getKind().isIndexed()) {
            if (fieldTemplate.getType() == IFieldTemplate.Type.OBJECT) {
                throw new ObjectTypeCannotBeAnIndexException(fieldName);
            }
        }

        if (fieldTemplate.getKind().isUnique()) {
            if (fieldTemplate.getType() == IFieldTemplate.Type.DOUBLE) {
                throw new DoubleTypeCannotBeUniqueException(fieldName);
            }
        }
        /// TODO: check that two fields does not have the same value
    }

}
