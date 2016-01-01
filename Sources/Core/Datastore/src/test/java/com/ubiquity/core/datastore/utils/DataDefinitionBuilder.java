package com.ubiquity.core.datastore.utils;

import java.util.ArrayList;
import java.util.Collection;

import com.ubiquity.core.datastore.IDataDefinition;
import com.ubiquity.core.datastore.IFieldTemplate;
import com.ubiquity.core.datastore.IFieldTemplate.Kind;
import com.ubiquity.core.datastore.IFieldTemplate.Type;

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
        private Type type;
        private Kind kind;

        public DataDefinitionBuilder build() {
            fieldDefinitions.add(new IFieldTemplate() {

                @Override
                public String getName() {
                    return name;
                }

                @Override
                public Type getType() {
                    return type;
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

        public FieldDefinitionBuilder withType(Type type) {
            this.type = type;
            return this;
        }

        public FieldDefinitionBuilder withKind(Kind kind) {
            this.kind = kind;
            return this;
        }
    }
}
