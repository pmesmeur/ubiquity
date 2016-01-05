package com.ubiquity.datastorage.kernel;

public interface IFieldTemplate {
            String getIdentifier();

    Type getType();

    Kind getKind();

    enum Type {
        INTEGER,
        DOUBLE,
        STRING,
        BOOLEAN,
        OBJECT,
        CHAR,
        DATE,
        TIME,
        DURATION
    }
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

}
