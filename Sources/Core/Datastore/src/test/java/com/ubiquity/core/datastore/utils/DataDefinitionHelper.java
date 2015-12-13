package com.ubiquity.core.datastore.utils;

import java.util.ArrayList;
import java.util.Collection;

import com.ubiquity.core.datastore.IDataDefinition;
import com.ubiquity.core.datastore.IFieldDefinition;

public class DataDefinitionHelper {

    public static IDataDefinition createBasicEntryDataDefinition() {
        return new IDataDefinition() {

            public String getIdentifier() {
                return "BasicData";
            }

            public Collection<IFieldDefinition> getFieldDefinitions() {
                ArrayList<IFieldDefinition> fieldDefinitions = new ArrayList<IFieldDefinition>();

                fieldDefinitions
                        .add(fieldDefinition("Field1", IFieldDefinition.Type.BOOLEAN, false, true));
                fieldDefinitions
                        .add(fieldDefinition("Field2", IFieldDefinition.Type.DOUBLE, false, true));
                fieldDefinitions
                        .add(fieldDefinition("Field3", IFieldDefinition.Type.CHAR, false, true));
                fieldDefinitions
                        .add(fieldDefinition("Field4", IFieldDefinition.Type.INTEGER, false, true));
                fieldDefinitions
                        .add(fieldDefinition("Field5", IFieldDefinition.Type.STRING, false, true));
                fieldDefinitions
                        .add(fieldDefinition("Field6", IFieldDefinition.Type.OBJECT, false, true));

                return (fieldDefinitions);
            }
        };
    }

    private static IFieldDefinition fieldDefinition(final String name,
            final IFieldDefinition.Type type,
            final boolean isIndexed,
            final boolean isMandatory) {
        return new IFieldDefinition() {
            public String getName() {
                return name;
            }

            public Type getType() {
                return type;
            }

            public boolean isIndexed() {
                return isIndexed;
            }

            public boolean isMandatory() {
                return isMandatory;
            }
        };
    }

}
