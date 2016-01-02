package com.ubiquity.core.datastore.utils;

import java.util.ArrayList;
import java.util.Collection;

import com.ubiquity.core.datastore.IFieldTemplate;
import com.ubiquity.core.datastore.IFieldTemplate.Kind;
import com.ubiquity.core.datastore.IFieldTemplate.Type;
import com.ubiquity.core.datastore.IRecordTemplate;

public class RecordTemplateBuilder {

    private String identifier;
    private ArrayList<IFieldTemplate> fieldTemplates = new ArrayList<IFieldTemplate>();

    public IRecordTemplate build() {
        return new IRecordTemplate() {

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


    public RecordTemplateBuilder withIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }


    public FieldTemplateBuilder addFieldTemplate() {
        return new FieldTemplateBuilder();
    }


    public class FieldTemplateBuilder {
        private String identifier;
        private Type type;
        private Kind kind;

        public RecordTemplateBuilder build() {
            fieldTemplates.add(new IFieldTemplate() {

                @Override
                public String getIdentifier() {
                    return identifier;
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
            return RecordTemplateBuilder.this;
        }

        public FieldTemplateBuilder withIdentifier(String identifier) {
            this.identifier = identifier;
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
