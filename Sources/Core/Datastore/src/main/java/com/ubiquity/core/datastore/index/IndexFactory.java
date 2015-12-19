package com.ubiquity.core.datastore.index;

public class IndexFactory {

    public enum Kind {
        MULTIPLE,
        UNIQUE
    }

    private IndexFactory() {
    }

    public static IIndex createIndex(Kind kind) {
        if (kind == Kind.MULTIPLE) {
            return new MultipleIndex();
        } else {
            return new SingleIndex();
        }
    }
}
