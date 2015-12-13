package com.ubiquity.core.datastore.utils;

import com.ubiquity.core.datastore.IDataDefinition;
import com.ubiquity.core.datastore.IFieldDefinition;
import com.ubiquity.core.datastore.IFieldDefinition.Type;

import java.util.ArrayList;
import java.util.Collection;

public class DataDefinitionBuilder {

    private String identifier;
    private ArrayList<IFieldDefinition> fieldDefinitions = new ArrayList<IFieldDefinition>();

    IDataDefinition build() {
        return new IDataDefinition() {
            public String getIdentifier() {
                return identifier;
            }

            public Collection<IFieldDefinition> getFieldDefinitions() {
                return fieldDefinitions;
            }
        };
    }


    public DataDefinitionBuilder withIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }


    public FieldDefinitionBuilder addFieldDefinition() {
        return new FieldDefinitionBuilder();
    }


    public class FieldDefinitionBuilder {
        private String name;
        private Type type;
        private boolean isIndexed;
        private boolean isMandatory;

        public DataDefinitionBuilder build() {
            fieldDefinitions.add(new IFieldDefinition() {

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
            });
            return DataDefinitionBuilder.this;
        }

        public FieldDefinitionBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public FieldDefinitionBuilder withType(Type type) {
            this.type = type;
            return this;
        }

        public FieldDefinitionBuilder withIsIndexed(boolean isIndexed) {
            this.isIndexed = isIndexed;
            return this;
        }

        public FieldDefinitionBuilder withIsMandatory(boolean isMandatory) {
            this.isMandatory = isMandatory;
            return this;
        }
    }
}
