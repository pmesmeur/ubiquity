package com.ubiquity.core.datastore;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Map;

import com.ubiquity.core.datastore.IFieldTemplate.Type;
import com.ubiquity.core.datastore.exceptions.EntryDoesNotFitDataDefinitionException;
import com.ubiquity.core.datastore.exceptions.MissingMandatoryFieldException;
import com.ubiquity.core.datastore.exceptions.WrongFieldTypeException;

public class Entry {

    private final Object fields[];

    public Entry(IDataDefinition dataDefinition, Map<String, Object> values) {
        assert dataDefinition != null;
        assert values != null;

        Collection<IFieldTemplate> fieldDefinitions = dataDefinition.getFieldDefinitions();
        this.fields = new Object[fieldDefinitions.size()];
        populateFields(fieldDefinitions, values);
    }

    private void populateFields(Collection<IFieldTemplate> fieldDefinitions,
            Map<String, Object> values) {
        int index = 0;
        int nbInserted = 0;

        for (IFieldTemplate fieldDefinition : fieldDefinitions) {
            if (populateField(fieldDefinition, index++, values)) {
                nbInserted++;
            }
        }

        if (nbInserted != values.size()) {
            throw new EntryDoesNotFitDataDefinitionException();
        }
    }

    private boolean populateField(IFieldTemplate fieldDefinition, int index,
            Map<String, Object> values) {
        boolean inserted = false;
        if (values.containsKey(fieldDefinition.getName())) {
            fields[index] = fieldValue(fieldDefinition, values);
            inserted = true;
        } else if (isFieldMandatory(fieldDefinition)) {
            throw new MissingMandatoryFieldException(fieldDefinition.getName());
        }

        return inserted;
    }

    private boolean isFieldMandatory(IFieldTemplate fieldDefinition) {
        return fieldDefinition.getKind().isMandatory();
    }

    private Object fieldValue(IFieldTemplate fieldDefinition, Map<String, Object> values) {
        String fieldName = fieldDefinition.getName();
        Type type = fieldDefinition.getType();
        Object fieldValue = values.get(fieldName);

        if (fieldValue == null && fieldDefinition.getKind().isMandatory()) {
            throw new MissingMandatoryFieldException(fieldName);
        }

        if (fieldValue != null && type != Type.OBJECT) {
            fieldValue = checkAndgetImmutableValue(fieldName, type, fieldValue);
        }

        return fieldValue;
    }

    private Object checkAndgetImmutableValue(String fieldName, Type type, Object fieldValue) {
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
