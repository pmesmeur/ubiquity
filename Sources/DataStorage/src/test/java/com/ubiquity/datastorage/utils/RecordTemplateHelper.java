package com.ubiquity.datastorage.utils;

import com.ubiquity.datastorage.kernel.interfaces.IRecordTemplate;

import static com.ubiquity.datastorage.kernel.interfaces.IFieldTemplate.Kind;
import static com.ubiquity.datastorage.kernel.interfaces.IFieldTemplate.Kind.*;
import static com.ubiquity.datastorage.kernel.interfaces.IFieldTemplate.Type.*;

public class RecordTemplateHelper {

    public static IRecordTemplate createBasicRecordTemplate() {
        return createRecordTempalte(INDEXED);
    }

    public static IRecordTemplate createRecordTempalte(Kind kind) {
        RecordTemplateBuilder recordTemplateBuilder = new RecordTemplateBuilder();

        return recordTemplateBuilder.withIdentifier("BasicRegister")
                .addFieldTemplate()
                .withIdentifier("Field1")
                .withType(STRING)
                .withKind(PRIMARY)
                .build()
                .addFieldTemplate()
                .withIdentifier("Field2")
                .withType(DOUBLE)
                .withKind(kind.isUnique() ? INDEXED : kind)
                .build()
                .addFieldTemplate()
                .withIdentifier("Field3")
                .withType(CHAR)
                .withKind(kind)
                .build()
                .addFieldTemplate()
                .withIdentifier("Field4")
                .withType(INTEGER)
                .withKind(kind)
                .build()
                .addFieldTemplate()
                .withIdentifier("Field5")
                .withType(BOOLEAN)
                .withKind(kind)
                .build()
                .addFieldTemplate()
                .withIdentifier("Field6")
                .withType(OBJECT)
                .withKind(kind.isIndexed() ? MANDATORY : OPTIONAL)
                .build()
                .addFieldTemplate()
                .withIdentifier("Field7")
                .withType(DATE)
                .withKind(kind)
                .build()
                .addFieldTemplate()
                .withIdentifier("Field8")
                .withType(TIME)
                .withKind(kind)
                .build()
                .addFieldTemplate()
                .withIdentifier("Field9")
                .withType(DURATION)
                .withKind(kind)
                .build()
            .build();
    }

    public static IRecordTemplate createPrimaryOptionalRecordTemplate() {
        RecordTemplateBuilder recordTemplateBuilder = new RecordTemplateBuilder();

        return recordTemplateBuilder.withIdentifier("BasicRegister")
                .addFieldTemplate()
                .withIdentifier("Field1")
                .withType(STRING)
                .withKind(PRIMARY)
                .build()
                .addFieldTemplate()
                .withIdentifier("Field2")
                .withType(DOUBLE)
                .withKind(OPTIONAL)
                .build()
                .build();
    }

}
