package com.ubiquity.datastorage.utils;

import com.ubiquity.datastorage.kernel.interfaces.IFieldTemplate;
import com.ubiquity.datastorage.kernel.interfaces.IRecordTemplate;

import java.util.ArrayList;
import java.util.Collection;

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
        private IFieldTemplate.Type type;
        private IFieldTemplate.Kind kind;

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

        public FieldTemplateBuilder withType(IFieldTemplate.Type type) {
            this.type = type;
            return this;
        }

        public FieldTemplateBuilder withKind(IFieldTemplate.Kind kind) {
            this.kind = kind;
            return this;
        }
    }
}
