package com.ubiquity.datastorage.kernel;

import com.ubiquity.datastorage.kernel.interfaces.IRecordFactory;
import com.ubiquity.datastorage.kernel.interfaces.IRegister;


public class RecordFactory implements IRecordFactory {

    @Override
    public IRegister create(IRecordTemplate recordTemplate) {
        return Register.create(recordTemplate);
    }

}
