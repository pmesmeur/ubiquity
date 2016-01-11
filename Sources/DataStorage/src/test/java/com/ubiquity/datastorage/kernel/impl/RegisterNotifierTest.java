package com.ubiquity.datastorage.kernel.impl;

import com.ubiquity.datastorage.kernel.interfaces.IRegisterListener;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class RegisterNotifierTest {

    private RegisterNotifier registerNotifier;

    @Before
    public void init() {
        registerNotifier = new RegisterNotifier();
    }


    @Test
    public void testAddListener() {
        registerNotifier.addListener(createDummyRegisterListener());
        Assert.assertEquals(1, registerNotifier.listeners.size());
    }


    private IRegisterListener createDummyRegisterListener() {
        return new NotificationCounter();
    }


    @Test
    public void testRemoveListener() {
        IRegisterListener dummyRegisterListener1 = createDummyRegisterListener();
        IRegisterListener dummyRegisterListener2 = createDummyRegisterListener();
        IRegisterListener dummyRegisterListener3 = createDummyRegisterListener();

        registerNotifier.addListener(dummyRegisterListener1);
        registerNotifier.addListener(dummyRegisterListener2);
        registerNotifier.addListener(dummyRegisterListener3);
        Assert.assertEquals(3, registerNotifier.listeners.size());

        registerNotifier.removeListener(dummyRegisterListener1);
        registerNotifier.removeListener(dummyRegisterListener2);
        registerNotifier.removeListener(dummyRegisterListener3);
        Assert.assertEquals(0, registerNotifier.listeners.size());
    }


    @Test
    public void testRegisterInserted() {
        NotificationCounter notificationCounter = new NotificationCounter();
        registerNotifier.addListener(notificationCounter);
        Assert.assertEquals(0, notificationCounter.getNbInsertion());
        registerNotifier.recordInserted(createDummyRecord());
        Assert.assertEquals(1, notificationCounter.getNbInsertion());
    }


    private Map<String, Object> createDummyRecord() {
        return new HashMap<>();
    }


    @Test
    public void testRegisterUpdated() {
        NotificationCounter notificationCounter = new NotificationCounter();
        registerNotifier.addListener(notificationCounter);
        Assert.assertEquals(0, notificationCounter.getNbInsertion());
        registerNotifier.recordUpdated(createDummyRecord());
        Assert.assertEquals(1, notificationCounter.getNbUpdate());
    }


    @Test
    public void testRegisterDeleted() {
        NotificationCounter notificationCounter = new NotificationCounter();
        Map<String, Object> dummyRecord = createDummyRecord();

        registerNotifier.addListener(notificationCounter);
        registerNotifier.recordInserted(dummyRecord);
        registerNotifier.recordDeleted(dummyRecord);
        Assert.assertEquals(1, notificationCounter.getNbDeletion());
    }


    class NotificationCounter implements IRegisterListener {

        private int nbInsertion = 0;
        private int nbUpdate = 0;
        private int nbDeletion = 0;

        public int getNbInsertion() {
            return nbInsertion;
        }

        public int getNbUpdate() {
            return nbUpdate;
        }

        public int getNbDeletion() {
            return nbDeletion;
        }

        @Override
        public void onRecordInserted(Map<String, Object> recordFields) {
            nbInsertion++;
        }

        @Override
        public void onRecordUpdated(Map<String, Object> recordFields) {
            nbUpdate++;
        }

        @Override
        public void onRecordDeleted(Map<String, Object> recordFields) {
            nbDeletion++;
        }
    }

}
