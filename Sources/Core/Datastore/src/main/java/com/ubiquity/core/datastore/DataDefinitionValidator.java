package com.ubiquity.core.datastore;

import com.google.common.base.Strings;

import java.util.Collection;

import static com.ubiquity.core.datastore.IFieldDefinition.Type.OBJECT;

public class DataDefinitionValidator {

    public static void validate(IDataDefinition dataDefinition) {
        assert dataDefinition != null;

        validateIdentifier(dataDefinition.getIdentifier());
        validateFieldDefinitions(dataDefinition.getFieldDefinitions());
    }

    private static void validateIdentifier(String identifier) {
        assert !Strings.isNullOrEmpty(identifier);
    }


    private static void validateFieldDefinitions(Collection<IFieldDefinition> fieldDefinitions) {
        assert fieldDefinitions != null;
        assert fieldDefinitions.size() > 0;

        for (IFieldDefinition fieldDefinition : fieldDefinitions) {
            validateFieldDefinition(fieldDefinition);
        }
    }

    private static void validateFieldDefinition(IFieldDefinition fieldDefinition) {
        String name = fieldDefinition.getName();
        assert !Strings.isNullOrEmpty(name);

        if (fieldDefinition.isIndexed()) {
            if (fieldDefinition.getType() == OBJECT) {
                throw new IllegalArgumentException("Field \"" + name + "\" is indexed and is of 'OBJECT' type" );
            }

            if (!fieldDefinition.isMandatory()) {
                throw new IllegalArgumentException("Field \"" + name + "\" is index but is not mandatory");
            }
        }
        /// TODO: check that two fields does not have the same value
    }

}
