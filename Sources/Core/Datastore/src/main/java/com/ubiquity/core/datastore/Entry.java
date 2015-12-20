package com.ubiquity.core.datastore;

import java.util.Collection;
import java.util.Map;

import com.ubiquity.core.datastore.IFieldDefinition.DataType;

public class Entry {

    private final Object fields[];

    public Entry(IDataDefinition dataDefinition, Map<String, Object> values) {
        assert dataDefinition != null;
        assert values != null;

        Collection<IFieldDefinition> fieldDefinitions = dataDefinition.getFieldDefinitions();
        this.fields = new Object[fieldDefinitions.size()];
        populateFields(fieldDefinitions, values);
    }

    private void populateFields(Collection<IFieldDefinition> fieldDefinitions,
            Map<String, Object> values) {
        int index = 0;
        int nbInserted = 0;

        for (IFieldDefinition fieldDefinition : fieldDefinitions) {
            if (populateField(fieldDefinition, index++, values)) {
                nbInserted++;
            }
        }

        if (nbInserted != values.size()) {
            throw new IllegalArgumentException("some fields were provided bus did not fit the data definition");
        }
    }

    private boolean populateField(IFieldDefinition fieldDefinition, int index,
            Map<String, Object> values) {
        boolean inserted = false;
        if (values.containsKey(fieldDefinition.getName())) {
            fields[index] = fieldValue(fieldDefinition, values);
            inserted = true;
        } else if (isFieldMandatory(fieldDefinition)) {
            throw new IllegalArgumentException(
                    "field \"" + fieldDefinition.getName() + "\" is mandatory but is missing");
        }

        return inserted;
    }

    private boolean isFieldMandatory(IFieldDefinition fieldDefinition) {
        return fieldDefinition.getKind().isMandatory();
    }

    private Object fieldValue(IFieldDefinition fieldDefinition, Map<String, Object> values) {
        String fieldName = fieldDefinition.getName();
        DataType dataType = fieldDefinition.getType();
        Object fieldValue = values.get(fieldName);

        if (dataType != DataType.OBJECT) {
            fieldValue = checkAndCloneValue(fieldName, dataType, fieldValue);
        }

        return fieldValue;
    }

    private Object checkAndCloneValue(String fieldName, DataType dataType, Object fieldValue) {
        Object result = null;

        switch (dataType) {

        case DOUBLE:
            result = checkAndCloneDoubleValue(fieldName, fieldValue);
            break;

        case STRING:
            result = checkAndCloneStringValue(fieldName, fieldValue);
            break;

        case BOOLEAN:
            result = checkAndCloneBooleanValue(fieldName, fieldValue);
            break;

        case INTEGER:
            result = checkAndCloneIntegerValue(fieldName, fieldValue);
            break;

        case CHAR:
            result = checkAndCloneCharValue(fieldName, fieldValue);
            break;

        case OBJECT:
        default:
            /// Not supposed to come here

        }

        return result;
    }

    private Object checkAndCloneDoubleValue(String fieldName, Object fieldValue) {
        if (!(fieldValue instanceof Double)) {
            throwBadType(fieldName, Double.class);
        }
        return new Double((Double) fieldValue);
    }

    private Object checkAndCloneIntegerValue(String fieldName, Object fieldValue) {
        if (!(fieldValue instanceof Integer)) {
            throwBadType(fieldName, Integer.class);
        }
        return new Integer((Integer) fieldValue);
    }

    private Object checkAndCloneStringValue(String fieldName, Object fieldValue) {
        if (!(fieldValue instanceof String)) {
            throwBadType(fieldName, String.class);
        }
        return new String((String) fieldValue);
    }

    private Object checkAndCloneCharValue(String fieldName, Object fieldValue) {
        if (!(fieldValue instanceof Character)) {
            throwBadType(fieldName, Character.class);
        }
        return new Character((Character) fieldValue);
    }

    private Object checkAndCloneBooleanValue(String fieldName, Object fieldValue) {
        if (!(fieldValue instanceof Boolean)) {
            throwBadType(fieldName, Boolean.class);
        }
        return new Boolean((Boolean) fieldValue);
    }

    private void throwBadType(String fieldName, Class<?> clazz) {
        throw new IllegalArgumentException(
                clazz.toString() + ": invalid type for field \"" + fieldName + "\"");
    }

}
