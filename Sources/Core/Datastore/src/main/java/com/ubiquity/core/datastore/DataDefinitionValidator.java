package com.ubiquity.core.datastore;

import static com.ubiquity.core.datastore.IFieldDefinition.DataType.OBJECT;

import java.util.Collection;

import com.google.common.base.Strings;
import com.ubiquity.core.datastore.exceptions.IndexingObjectException;
import com.ubiquity.core.datastore.exceptions.NoPrimaryFieldException;

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
            Collection<IFieldDefinition> fieldDefinitions) {
        assert fieldDefinitions != null;
        assert fieldDefinitions.size() > 0;

        boolean hasAtLeastOneUniqueField = false;

        for (IFieldDefinition fieldDefinition : fieldDefinitions) {
            hasAtLeastOneUniqueField |= fieldDefinition.getKind().isUnique();
            validateFieldDefinition(fieldDefinition);
        }

        if (!hasAtLeastOneUniqueField) {
            throw new NoPrimaryFieldException(identifier);
        }
    }

    private static void validateFieldDefinition(IFieldDefinition fieldDefinition) {
        String fieldName = fieldDefinition.getName();
        assert!Strings.isNullOrEmpty(fieldName);

        if (fieldDefinition.getKind().isIndexed()) {
            if (fieldDefinition.getType() == OBJECT) {
                throw new IndexingObjectException(fieldName);
            }

            if (!fieldDefinition.getKind().isMandatory()) {
                throw new IllegalArgumentException(
                        "Field \"" + fieldName + "\" is index but is not mandatory");
            }
        }
        /// TODO: check that two fields does not have the same value
    }

}
