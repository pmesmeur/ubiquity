package com.ubiquity.datastorage.kernel.interfaces;

import com.ubiquity.datastorage.kernel.IRecordTemplate;

import java.util.Map;

public interface IRegister {
    IRecordTemplate getDefinition();

    void insert(Map<String, Object> recordFields);
}
