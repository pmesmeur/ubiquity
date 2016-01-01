package com.ubiquity.core.datastore;

import static com.ubiquity.core.datastore.IFieldTemplate.DataType.DOUBLE;
import static com.ubiquity.core.datastore.IFieldTemplate.DataType.OBJECT;

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
        validateFieldDefinitions(dataDefinition.getIdentifier(),
                dataDefinition.getFieldDefinitions());
    }

    private static void validateIdentifier(String identifier) {
        assert !Strings.isNullOrEmpty(identifier);
    }


    private static void validateFieldDefinitions(String identifier,
            Collection<IFieldTemplate> fieldDefinitions) {
        assert fieldDefinitions != null;
        assert fieldDefinitions.size() > 0;

        boolean hasAtLeastOneUniqueField = false;

        for (IFieldTemplate fieldDefinition : fieldDefinitions) {
            hasAtLeastOneUniqueField |= fieldDefinition.getKind().isUnique();
            validateFieldDefinition(fieldDefinition);
        }

        if (!hasAtLeastOneUniqueField) {
            throw new NoPrimaryFieldException(identifier);
        }
    }

    private static void validateFieldDefinition(IFieldTemplate fieldDefinition) {
        String fieldName = fieldDefinition.getName();
        assert!Strings.isNullOrEmpty(fieldName);

        if (fieldDefinition.getKind().isIndexed()) {
            if (fieldDefinition.getType() == OBJECT) {
                throw new ObjectTypeCannotBeIndexException(fieldName);
            }
        }

        if (fieldDefinition.getKind().isUnique()) {
            if (fieldDefinition.getType() == DOUBLE) {
                throw new DoubleTypeCannotBeUniqueException(fieldName);
            }
        }
        /// TODO: check that two fields does not have the same value
    }

}
