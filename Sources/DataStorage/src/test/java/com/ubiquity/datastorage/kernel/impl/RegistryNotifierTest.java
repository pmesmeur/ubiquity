package com.ubiquity.datastorage.kernel.impl;

import com.ubiquity.datastorage.kernel.interfaces.IFieldTemplate;
import com.ubiquity.datastorage.kernel.interfaces.IRecordTemplate;
import com.ubiquity.datastorage.kernel.interfaces.IRegistryListener;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;


public class RegistryNotifierTest {

    private RegistryNotifier registryNotifier;

    @Before
    public void init() {
        registryNotifier = new RegistryNotifier();
    }


    @Test
    public void testAddListener() {
        registryNotifier.addListener(createDummyRegistryListener());
        Assert.assertEquals(1, registryNotifier.listeners.size());
    }


    private IRegistryListener createDummyRegistryListener() {
        return new IRegistryListener() {
            @Override
            public void onRegisterInserted(IRecordTemplate recordTemplate) {
            }

            @Override
            public void onRegisterDeleted(String registerId) {
            }
        };
    }


    @Test
    public void testRemoveListener() {
        IRegistryListener dummyRegistryListener1 = createDummyRegistryListener();
        IRegistryListener dummyRegistryListener2 = createDummyRegistryListener();
        IRegistryListener dummyRegistryListener3 = createDummyRegistryListener();

        registryNotifier.addListener(dummyRegistryListener1);
        registryNotifier.addListener(dummyRegistryListener2);
        registryNotifier.addListener(dummyRegistryListener3);
        Assert.assertEquals(3, registryNotifier.listeners.size());

        registryNotifier.removeListener(dummyRegistryListener1);
        registryNotifier.removeListener(dummyRegistryListener2);
        registryNotifier.removeListener(dummyRegistryListener3);
        Assert.assertEquals(0, registryNotifier.listeners.size());
    }


    @Test
    public void testRegisterInserted() {
        NotificationCounter notificationCounter = new NotificationCounter();
        registryNotifier.addListener(notificationCounter);
        Assert.assertEquals(0, notificationCounter.getNbInsertion());
        registryNotifier.registerInserted(createDummyRecordTemplate("DummyRecordTemplate"));
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
    public void testRegisterDeleted() {
        NotificationCounter notificationCounter = new NotificationCounter();
        registryNotifier.addListener(notificationCounter);
        registryNotifier.registerInserted(createDummyRecordTemplate("DummyRecordTemplate"));
        registryNotifier.registerDeleted("DummyRecordTemplate");
        Assert.assertEquals(1, notificationCounter.getNbDeletion());
    }


    class NotificationCounter implements IRegistryListener {

        private int nbInsertion = 0;
        private int nbDeletion = 0;

        @Override
        public void onRegisterInserted(IRecordTemplate recordTemplate) {
            nbInsertion++;
        }

        @Override
        public void onRegisterDeleted(String registerId) {
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
