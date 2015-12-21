package com.ubiquity.core.datastore.indexes;

public class IndexFactory {

    private IndexFactory() {
    }

    public static IIndex createIndex(Kind kind) {
        if (kind == Kind.MULTIPLE) {
            return new MultipleIndex();
        } else {
            return new SingleIndex();
        }
    }

    public enum Kind {
        MULTIPLE,
        UNIQUE
    }
}
