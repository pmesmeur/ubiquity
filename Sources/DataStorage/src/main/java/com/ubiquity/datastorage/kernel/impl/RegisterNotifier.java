package com.ubiquity.datastorage.kernel.impl;


import com.ubiquity.datastorage.kernel.interfaces.IRegisterListener;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RegisterNotifier {
    protected final Set<IRegisterListener> listeners;


    public RegisterNotifier() {
        listeners = new HashSet<IRegisterListener>(); /// TODO:concurrence
    }


    public void addListener(IRegisterListener registerListener) {
        assert registerListener != null;
        listeners.add(registerListener);
    }


    public void removeListener(IRegisterListener registerListener) {
        assert registerListener != null;
        listeners.remove(registerListener);
    }


    public void recordInserted(Map<String, Object> recordFields) {
        listeners.stream().forEach(p -> p.onRecordInserted(recordFields));
    }


    public void recordUpdated(Map<String, Object> recordFields) {
        listeners.stream().forEach(p -> p.onRecordUpdated(recordFields));
    }


    public void recordDeleted(Map<String, Object> recordFields) {
        listeners.stream().forEach(p -> p.onRecordDeleted(recordFields));
    }

}
