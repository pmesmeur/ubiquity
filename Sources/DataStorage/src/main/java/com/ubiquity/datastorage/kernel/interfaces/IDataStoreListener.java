package com.ubiquity.datastorage.kernel.interfaces;


public interface IDataStoreListener {
    void onRegistryInserted(String registryId);

    void onRegistryDeleted(String registryId);
}
