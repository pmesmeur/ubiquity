package com.ubiquity.datastorage.kernel.impl;

import com.ubiquity.datastorage.kernel.interfaces.IDataStoreListener;
import com.ubiquity.datastorage.kernel.interfaces.IFieldTemplate;
import com.ubiquity.datastorage.kernel.interfaces.IRecordTemplate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;


public class DataStoreNotifierTest {

    private DataStoreNotifier dataStoreNotifier;

    @Before
    public void init() {
        dataStoreNotifier = new DataStoreNotifier();
    }


    @Test
    public void testAddListener() {
        dataStoreNotifier.addListener(createDummyDataStoreListener());
        Assert.assertEquals(1, dataStoreNotifier.listeners.size());
    }


    private IDataStoreListener createDummyDataStoreListener() {
        return new NotificationCounter();
    }


    @Test
    public void testRemoveListener() {
        IDataStoreListener dummyDataStoreListenerListener1 = createDummyDataStoreListener();
        IDataStoreListener dummyDataStoreListenerListener2 = createDummyDataStoreListener();
        IDataStoreListener dummyDataStoreListenerListener3 = createDummyDataStoreListener();

        dataStoreNotifier.addListener(dummyDataStoreListenerListener1);
        dataStoreNotifier.addListener(dummyDataStoreListenerListener2);
        dataStoreNotifier.addListener(dummyDataStoreListenerListener3);
        Assert.assertEquals(3, dataStoreNotifier.listeners.size());

        dataStoreNotifier.removeListener(dummyDataStoreListenerListener1);
        dataStoreNotifier.removeListener(dummyDataStoreListenerListener2);
        dataStoreNotifier.removeListener(dummyDataStoreListenerListener3);
        Assert.assertEquals(0, dataStoreNotifier.listeners.size());
    }


    @Test
    public void testRegistryInserted() {
        NotificationCounter notificationCounter = new NotificationCounter();
        dataStoreNotifier.addListener(notificationCounter);
        Assert.assertEquals(0, notificationCounter.getNbInsertion());
        dataStoreNotifier.registryInserted("DummyRegistry");
        Assert.assertEquals(1, notificationCounter.getNbInsertion());
    }


    private IRecordTemplate createDummyRecordTemplate(String recordTemplateIdentifier) {
        return new IRecordTemplate() {
            @Override
            public String getIdentifier() {
                return recordTemplateIdentifier;
            }

            @Override
            public Collection<IFieldTemplate> getFieldTemplates() {
                return null;
            }
        };
    }


    @Test
    public void testRegistryDeleted() {
        NotificationCounter notificationCounter = new NotificationCounter();
        dataStoreNotifier.addListener(notificationCounter);
        dataStoreNotifier.registryInserted("DummyRegistry");
        dataStoreNotifier.registryDeleted("DummyRegistry");
        Assert.assertEquals(1, notificationCounter.getNbDeletion());
    }


    class NotificationCounter implements IDataStoreListener {

        private int nbInsertion = 0;
        private int nbDeletion = 0;

        @Override
        public void onRegistryInserted(String registryId) {
            nbInsertion++;
        }

        @Override
        public void onRegistryDeleted(String registryId) {
            nbDeletion++;
        }

        public int getNbInsertion() {
            return nbInsertion;
        }

        public int getNbDeletion() {
            return nbDeletion;
        }
    }

}
