package com.ubiquity.core.datastore.utils;

import static com.ubiquity.core.datastore.IFieldTemplate.Kind;
import static com.ubiquity.core.datastore.IFieldTemplate.Kind.*;
import static com.ubiquity.core.datastore.IFieldTemplate.Type.*;

import com.ubiquity.core.datastore.IRecordTemplate;

public class RecordTemplateHelper {

    public static IRecordTemplate createBasicRecordTemplate() {
        return createRecordTempalte(INDEXED);
    }

    public static IRecordTemplate createRecordTempalte(Kind kind) {
        RecordTemplateBuilder recordTemplateBuilder = new RecordTemplateBuilder();

        return recordTemplateBuilder.withIdentifier("BasicData")
                .addFieldTemplate()
                .withName("Field1")
                .withType(STRING)
                .withKind(PRIMARY)
                .build()
                .addFieldTemplate()
                .withName("Field2")
                .withType(DOUBLE)
                .withKind(kind.isUnique() ? INDEXED : kind)
                .build()
                .addFieldTemplate()
                .withName("Field3")
                .withType(CHAR)
                .withKind(kind)
                .build()
                .addFieldTemplate()
                .withName("Field4")
                .withType(INTEGER)
                .withKind(kind)
                .build()
                .addFieldTemplate()
                .withName("Field5")
                .withType(BOOLEAN)
                .withKind(kind)
                .build()
                .addFieldTemplate()
                .withName("Field6")
                .withType(OBJECT)
                .withKind(kind.isIndexed() ? MANDATORY : OPTIONAL)
                .build()
                .addFieldTemplate()
                .withName("Field7")
                .withType(DATE)
                .withKind(kind)
                .build()
                .addFieldTemplate()
                .withName("Field8")
                .withType(TIME)
                .withKind(kind)
                .build()
                .addFieldTemplate()
                .withName("Field9")
                .withType(DURATION)
                .withKind(kind)
                .build()
            .build();
    }

    public static IRecordTemplate createPrimaryOptionalRecordTemplate() {
        RecordTemplateBuilder recordTemplateBuilder = new RecordTemplateBuilder();

        return recordTemplateBuilder.withIdentifier("BasicData")
                .addFieldTemplate()
                .withName("Field1")
                .withType(STRING)
                .withKind(PRIMARY)
                .build()
                .addFieldTemplate()
                .withName("Field2")
                .withType(DOUBLE)
                .withKind(OPTIONAL)
                .build()
                .build();
    }

}
