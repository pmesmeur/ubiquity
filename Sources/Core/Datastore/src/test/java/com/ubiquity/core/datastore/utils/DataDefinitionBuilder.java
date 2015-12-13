package com.ubiquity.core.datastore.utils;

import com.ubiquity.core.datastore.IDataDefinition;
import com.ubiquity.core.datastore.IFieldDefinition;
import com.ubiquity.core.datastore.IFieldDefinition.DataType;
import com.ubiquity.core.datastore.IFieldDefinition.Kind;

import java.util.ArrayList;
import java.util.Collection;

public class DataDefinitionBuilder {

    private String identifier;
    private ArrayList<IFieldDefinition> fieldDefinitions = new ArrayList<IFieldDefinition>();

    public IDataDefinition build() {
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
        private DataType dataType;
        private Kind kind;

        public DataDefinitionBuilder build() {
            fieldDefinitions.add(new IFieldDefinition() {

                public String getName() {
                    return name;
                }

                public DataType getType() {
                    return dataType;
                }

                public Kind getKind() {
                    return kind;
                }

            });
            return DataDefinitionBuilder.this;
        }

        public FieldDefinitionBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public FieldDefinitionBuilder withType(DataType dataType) {
            this.dataType = dataType;
            return this;
        }

        public FieldDefinitionBuilder withKind(Kind kind) {
            this.kind = kind;
            return this;
        }
    }
}
