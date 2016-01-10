package com.ubiquity.datastorage.kernel.interfaces;

import java.util.Map;

public interface IRegister {
    IRecordTemplate getDefinition();

    void insert(Map<String, Object> recordFields);
}
