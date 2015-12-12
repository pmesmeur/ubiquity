package com.ubiquity.core.datastore;


import java.util.ArrayList;
import java.util.Collection;

public class Data {

    private final IDataDefinition dataDefinition;
    private final Collection<Entry> entries;

    public Data(IDataDefinition dataDefinition) {
        this.dataDefinition = dataDefinition; /// TODO: make a copy
        this.entries = new ArrayList<Entry>();
    }


}
