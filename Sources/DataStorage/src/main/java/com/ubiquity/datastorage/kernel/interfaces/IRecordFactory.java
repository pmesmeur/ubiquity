package com.ubiquity.datastorage.kernel.interfaces;


public interface IRecordFactory {
    IRegister create(IRecordTemplate recordTemplate);
}
