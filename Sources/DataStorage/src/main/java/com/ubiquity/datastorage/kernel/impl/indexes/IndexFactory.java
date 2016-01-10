package com.ubiquity.datastorage.kernel.impl.indexes;

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
