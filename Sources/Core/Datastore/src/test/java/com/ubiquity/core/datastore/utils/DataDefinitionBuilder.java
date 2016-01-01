package com.ubiquity.core.datastore.utils;

import java.util.ArrayList;
import java.util.Collection;

import com.ubiquity.core.datastore.IDataDefinition;
import com.ubiquity.core.datastore.IFieldTemplate;
import com.ubiquity.core.datastore.IFieldTemplate.DataType;
import com.ubiquity.core.datastore.IFieldTemplate.Kind;

public class DataDefinitionBuilder {

    private String identifier;
    private ArrayList<IFieldTemplate> fieldDefinitions = new ArrayList<IFieldTemplate>();

    public IDataDefinition build() {
        return new IDataDefinition() {

            @Override
            public String getIdentifier() {
                return identifier;
            }

            @Override
            public Collection<IFieldTemplate> getFieldDefinitions() {
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
            fieldDefinitions.add(new IFieldTemplate() {

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
