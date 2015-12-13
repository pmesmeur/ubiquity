package com.ubiquity.core.datastore;

public interface IFieldDefinition {
    enum DataType { INTEGER, DOUBLE, STRING, BOOLEAN, OBJECT, CHAR }
    enum Kind {
        PRIMARY(true, true, true),
        INDEXED(true, true, false),
        MANDATORY(true, false, false),
        OPTIONAL(false, false, false);

        private final boolean isMandatory;
        private final boolean isIndexed;
        private final boolean isUnique;

        Kind(boolean isMandatory, boolean isIndexed, boolean isUnique) {
            this.isMandatory = isMandatory;
            this.isIndexed = isIndexed;
            this.isUnique = isUnique;
        }

        public boolean isMandatory() {
            return isMandatory;
        }

        public boolean isIndexed() {
            return isIndexed;
        }

        public boolean isUnique() {
            return isUnique;
        }
    }

    String getName();
    DataType getType();
    Kind getKind();

}
