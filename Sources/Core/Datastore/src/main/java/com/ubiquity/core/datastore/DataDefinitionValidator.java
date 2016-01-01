package com.ubiquity.core.datastore;

import static com.ubiquity.core.datastore.IFieldTemplate.Type.DOUBLE;
import static com.ubiquity.core.datastore.IFieldTemplate.Type.OBJECT;

import java.util.Collection;

import com.google.common.base.Strings;
import com.ubiquity.core.datastore.exceptions.DoubleTypeCannotBeUniqueException;
import com.ubiquity.core.datastore.exceptions.NoPrimaryFieldException;
import com.ubiquity.core.datastore.exceptions.ObjectTypeCannotBeIndexException;

class DataDefinitionValidator {

    private DataDefinitionValidator() {
    }

    public static void validate(IDataDefinition dataDefinition) {
        assert dataDefinition != null;

        validateIdentifier(dataDefinition.getIdentifier());
        validateFieldTempates(dataDefinition.getIdentifier(),
                dataDefinition.getFieldTemplates());
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
        String fieldName = fieldTemplate.getName();
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
