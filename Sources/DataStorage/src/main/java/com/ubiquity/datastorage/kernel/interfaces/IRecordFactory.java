package com.ubiquity.datastorage.kernel.interfaces;

import com.ubiquity.datastorage.kernel.IRecordTemplate;


public interface IRecordFactory {
    IRegister create(IRecordTemplate recordTemplate);
}
