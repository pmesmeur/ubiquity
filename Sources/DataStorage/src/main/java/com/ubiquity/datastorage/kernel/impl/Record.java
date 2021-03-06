package com.ubiquity.datastorage.kernel.impl;

import com.ubiquity.datastorage.kernel.exceptions.MissingMandatoryFieldException;
import com.ubiquity.datastorage.kernel.exceptions.RecordDoesNotFitTemplateException;
import com.ubiquity.datastorage.kernel.exceptions.WrongFieldTypeException;
import com.ubiquity.datastorage.kernel.interfaces.IFieldTemplate;
import com.ubiquity.datastorage.kernel.interfaces.IRecordTemplate;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Map;

public class Record {

    private final Object fields[];

    public Record(IRecordTemplate recordTemplate, Map<String, Object> values) {
        assert recordTemplate != null;
        assert values != null;

        Collection<IFieldTemplate> fieldTemplates = recordTemplate.getFieldTemplates();
        this.fields = new Object[fieldTemplates.size()];
        populateFields(fieldTemplates, values);
    }

    private void populateFields(Collection<IFieldTemplate> fieldTemplates,
            Map<String, Object> values) {
        int index = 0;
        int nbInserted = 0;

        for (IFieldTemplate fieldTemplate : fieldTemplates) {
            if (populateField(fieldTemplate, index++, values)) {
                nbInserted++;
            }
        }

        if (nbInserted != values.size()) {
            throw new RecordDoesNotFitTemplateException();
        }
    }

    private boolean populateField(IFieldTemplate fieldTemplate, int index,
            Map<String, Object> values) {
        boolean inserted = false;
        if (values.containsKey(fieldTemplate.getIdentifier())) {
            fields[index] = fieldValue(fieldTemplate, values);
            inserted = true;
        } else if (isFieldMandatory(fieldTemplate)) {
            throw new MissingMandatoryFieldException(fieldTemplate.getIdentifier());
        }

        return inserted;
    }

    private boolean isFieldMandatory(IFieldTemplate fieldTemplate) {
        return fieldTemplate.getKind().isMandatory();
    }

    private Object fieldValue(IFieldTemplate fieldTemplate, Map<String, Object> values) {
        String fieldName = fieldTemplate.getIdentifier();
        IFieldTemplate.Type type = fieldTemplate.getType();
        Object fieldValue = values.get(fieldName);

        if (fieldValue == null && fieldTemplate.getKind().isMandatory()) {
            throw new MissingMandatoryFieldException(fieldName);
        }

        if (fieldValue != null && type != IFieldTemplate.Type.OBJECT) {
            fieldValue = checkAndgetImmutableValue(fieldName, type, fieldValue);
        }

        return fieldValue;
    }

    private Object checkAndgetImmutableValue(String fieldName, IFieldTemplate.Type type, Object fieldValue) {
        Object result = null;

        switch (type) {

            case DOUBLE:
                result = checkAndGetImmutableDoubleValue(fieldName, fieldValue);
                break;

            case STRING:
                result = checkAndGetImmutableStringValue(fieldName, fieldValue);
                break;

            case BOOLEAN:
                result = checkAndGetImmutableBooleanValue(fieldName, fieldValue);
                break;

            case INTEGER:
                result = checkAndGetImmutableIntegerValue(fieldName, fieldValue);
                break;

            case CHAR:
                result = checkAndGetImmutableCharValue(fieldName, fieldValue);
                break;

            case DATE:
                result = checkAndGetImmutableDateValue(fieldName, fieldValue);
                break;

            case TIME:
                result = checkAndGetImmutableTimeValue(fieldName, fieldValue);
                break;

            case DURATION:
                result = checkAndGetImmutableDurationValue(fieldName, fieldValue);
                break;

            case OBJECT:
            default:
                /// Not supposed to come here
        }

        return result;
    }

    private Object checkAndGetImmutableDoubleValue(String fieldName, Object fieldValue) {
        if (!(fieldValue instanceof Double)) {
            throwBadType(fieldName, Double.class);
        }
        return fieldValue;
    }

    private Object checkAndGetImmutableIntegerValue(String fieldName, Object fieldValue) {
        if (!(fieldValue instanceof Integer)) {
            throwBadType(fieldName, Integer.class);
        }
        return fieldValue;
    }

    private Object checkAndGetImmutableStringValue(String fieldName, Object fieldValue) {
        if (!(fieldValue instanceof String)) {
            throwBadType(fieldName, String.class);
        }
        return fieldValue;
    }

    private Object checkAndGetImmutableCharValue(String fieldName, Object fieldValue) {
        if (!(fieldValue instanceof Character)) {
            throwBadType(fieldName, Character.class);
        }
        return fieldValue;
    }

    private Object checkAndGetImmutableBooleanValue(String fieldName, Object fieldValue) {
        if (!(fieldValue instanceof Boolean)) {
            throwBadType(fieldName, Boolean.class);
        }
        return fieldValue;
    }

    private Object checkAndGetImmutableDateValue(String fieldName, Object fieldValue) {
        if (!(fieldValue instanceof LocalDate)) {
            throwBadType(fieldName, LocalDate.class);
        }
        return fieldValue;
    }

    private Object checkAndGetImmutableTimeValue(String fieldName, Object fieldValue) {
        if (!(fieldValue instanceof LocalTime)) {
            throwBadType(fieldName, LocalTime.class);
        }
        return fieldValue;
    }

    private Object checkAndGetImmutableDurationValue(String fieldName, Object fieldValue) {
        if (!(fieldValue instanceof Duration)) {
            throwBadType(fieldName, Duration.class);
        }
        return fieldValue;
    }

    private void throwBadType(String fieldName, Class<?> clazz) {
        throw new WrongFieldTypeException(fieldName, clazz);
    }

}
