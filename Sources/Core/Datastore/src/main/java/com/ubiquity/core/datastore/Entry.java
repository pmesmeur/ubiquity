package com.ubiquity.core.datastore;

import com.ubiquity.core.datastore.IFieldDefinition.Type;

import java.util.Collection;
import java.util.Map;

public class Entry {

    private final Object fields[];

    public Entry(IDataDefinition dataDefinition, Map<String, Object> values) {
        assert dataDefinition != null;
        assert values != null;

        Collection<IFieldDefinition> fieldDefinitions = dataDefinition.getFieldDefinitions();
        this.fields = new Object[fieldDefinitions.size()];
        populateFields(fieldDefinitions, values);
    }


    private void populateFields(Collection<IFieldDefinition> fieldDefinitions, Map<String, Object> values) {
        int index = 0;

        for (IFieldDefinition fieldDefinition : fieldDefinitions) {
            populateField(fieldDefinition, index++, values);
        }
    }


    private void populateField(IFieldDefinition fieldDefinition, int index, Map<String, Object> values) {

        if (isFieldMandatoryAndMissing(fieldDefinition, values)) {
            /// TODO: throw
        }
        else {
            fields[index] = fieldValue(fieldDefinition, values);
        }
    }

    private Object fieldValue(IFieldDefinition fieldDefinition, Map<String, Object> values) {
        String fieldName = fieldDefinition.getName();
        Type fieldType = fieldDefinition.getType();
        Object fieldValue = values.get(fieldName);

        if (fieldType != Type.OBJECT) {
            fieldValue = checkAndCloneValue(fieldName, fieldType, fieldValue);
        }

        return fieldValue;
    }


    private boolean isFieldMandatoryAndMissing(IFieldDefinition fieldDefinition, Map<String, Object> values) {
        return fieldDefinition.isMandatory() && !values.containsKey(fieldDefinition.getName());
    }


    private Object checkAndCloneValue(String fieldName, Type type, Object fieldValue) {
        Object result = null;

        switch (type) {

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
                ; /// Not supposed to come here

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
        throw new IllegalArgumentException(clazz.toString() + ": invalid type for field \"" + fieldName + "\"");
    }

}
