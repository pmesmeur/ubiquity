package com.ubiquity.core.datastore.utils;

import java.util.ArrayList;
import java.util.Collection;

import com.ubiquity.core.datastore.IDataDefinition;
import com.ubiquity.core.datastore.IFieldDefinition;
import com.ubiquity.core.datastore.IFieldDefinition.DataType;
import com.ubiquity.core.datastore.IFieldDefinition.Kind;

public class DataDefinitionBuilder {

    private String identifier;
    private ArrayList<IFieldDefinition> fieldDefinitions = new ArrayList<IFieldDefinition>();

    public IDataDefinition build() {
        return new IDataDefinition() {

            @Override
            public String getIdentifier() {
                return identifier;
            }

            @Override
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

                @Override
                public String getName() {
                    return name;
                }

                @Override
                public DataType getType() {
                    return dataType;
                }

                @Override
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
