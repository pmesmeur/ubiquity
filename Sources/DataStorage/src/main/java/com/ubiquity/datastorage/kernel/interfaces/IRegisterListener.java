package com.ubiquity.datastorage.kernel.interfaces;


import java.util.Map;

public interface IRegisterListener {
    void onRecordInserted(Map<String, Object> recordFields);

    void onRecordUpdated(Map<String, Object> recordFields);

    void onRecordDeleted(Map<String, Object> recordFields);
}
