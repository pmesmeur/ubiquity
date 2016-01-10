package com.ubiquity.datastorage.multithreading;


import com.ubiquity.datastorage.kernel.interfaces.IRecordTemplate;
import com.ubiquity.datastorage.kernel.interfaces.IRegister;
import com.ubiquity.datastorage.kernel.interfaces.IRegistry;
import com.ubiquity.datastorage.multithreading.commands.RegisterInserter;
import com.ubiquity.datastorage.multithreading.commands.RegistryInserter;
import com.ubiquity.datastorage.utils.RecordTemplate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class SchedulerTest {

    private Scheduler scheduler;

    @Before
    public void init() {
        scheduler = new Scheduler();
    }

    @Test
    public void testExecuteCommands() throws InterruptedException {
        final String DATA_REGISTRY_NAME = "The.Data.Registry";
        IRecordTemplate recordTemplate = new RecordTemplate();

        scheduler.execute(new RegistryInserter(DATA_REGISTRY_NAME));
        Thread.sleep(100);
        scheduler.execute(new RegisterInserter(DATA_REGISTRY_NAME, recordTemplate));
        Thread.sleep(100);

        IRegistry registry = scheduler.getRegistry(DATA_REGISTRY_NAME);
        assertNotNull(registry);

        IRegister register = registry.getRegister(recordTemplate.getIdentifier());
        assertNotNull(register);

    }

}
