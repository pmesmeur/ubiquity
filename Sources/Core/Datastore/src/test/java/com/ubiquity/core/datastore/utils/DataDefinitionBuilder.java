package com.ubiquity.core.datastore.utils;

import java.util.ArrayList;
import java.util.Collection;

import com.ubiquity.core.datastore.IDataDefinition;
import com.ubiquity.core.datastore.IFieldTemplate;
import com.ubiquity.core.datastore.IFieldTemplate.Kind;
import com.ubiquity.core.datastore.IFieldTemplate.Type;

public class DataDefinitionBuilder {

    private String identifier;
    private ArrayList<IFieldTemplate> fieldTemplates = new ArrayList<IFieldTemplate>();

    public IDataDefinition build() {
        return new IDataDefinition() {

            @Override
            public String getIdentifier() {
                return identifier;
            }

            @Override
            public Collection<IFieldTemplate> getFieldTemplates() {
                return fieldTemplates;
            }
        };
    }


    public DataDefinitionBuilder withIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }


    public FieldTemplateBuilder addFieldTemplate() {
        return new FieldTemplateBuilder();
    }


    public class FieldTemplateBuilder {
        private String name;
        private Type type;
        private Kind kind;

        public DataDefinitionBuilder build() {
            fieldTemplates.add(new IFieldTemplate() {

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

        public FieldTemplateBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public FieldTemplateBuilder withType(Type type) {
            this.type = type;
            return this;
        }

        public FieldTemplateBuilder withKind(Kind kind) {
            this.kind = kind;
            return this;
        }
    }
}
