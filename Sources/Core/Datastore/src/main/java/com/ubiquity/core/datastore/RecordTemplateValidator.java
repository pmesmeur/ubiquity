package com.ubiquity.core.datastore;

import static com.ubiquity.core.datastore.IFieldTemplate.Type.DOUBLE;
import static com.ubiquity.core.datastore.IFieldTemplate.Type.OBJECT;

import java.util.Collection;

import com.google.common.base.Strings;
import com.ubiquity.core.datastore.exceptions.DoubleTypeCannotBeUniqueException;
import com.ubiquity.core.datastore.exceptions.NoPrimaryFieldException;
import com.ubiquity.core.datastore.exceptions.ObjectTypeCannotBeIndexException;

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
            if (fieldTemplate.getType() == OBJECT) {
                throw new ObjectTypeCannotBeIndexException(fieldName);
            }
        }

        if (fieldTemplate.getKind().isUnique()) {
            if (fieldTemplate.getType() == DOUBLE) {
                throw new DoubleTypeCannotBeUniqueException(fieldName);
            }
        }
        /// TODO: check that two fields does not have the same value
    }

}
