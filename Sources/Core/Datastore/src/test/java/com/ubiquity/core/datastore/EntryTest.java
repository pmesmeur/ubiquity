package com.ubiquity.core.datastore;


import com.ubiquity.core.datastore.IFieldDefinition.Type;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EntryTest {


    @Test(expected = AssertionError.class)
    public void testCreatingEntryWithNullDataDefinition() {
        new Entry(null, createBasicEntryValues());
    }


    @Test(expected = AssertionError.class)
    public void testCreatingEntryWithNullValues() {
        new Entry(createBasicEntryDataDefinition(), null);
    }


    @Test
    public void testCreatingBasicEntry() {
        new Entry(createBasicEntryDataDefinition(), createBasicEntryValues());
    }


    private IDataDefinition createBasicEntryDataDefinition() {
        return new IDataDefinition() {

            public String getIdentifier() {
                return "BasicData";
            }

            public Collection<IFieldDefinition> getFieldDefinitions() {
                ArrayList<IFieldDefinition> fieldDefinitions = new ArrayList<IFieldDefinition>();

                fieldDefinitions.add(fieldDefinition("Field1", Type.BOOLEAN, false, true));
                fieldDefinitions.add(fieldDefinition("Field2", Type.DOUBLE, false, true));
                fieldDefinitions.add(fieldDefinition("Field3", Type.CHAR, false, true));
                fieldDefinitions.add(fieldDefinition("Field4", Type.INTEGER, false, true));
                fieldDefinitions.add(fieldDefinition("Field5", Type.STRING, false, true));
                fieldDefinitions.add(fieldDefinition("Field6", Type.OBJECT, false, true));

                return (fieldDefinitions);
            }
        };
    }


    private Map<String, Object> createBasicEntryValues() {
        Map<String, Object> entryValues = new HashMap<String, Object>();

        entryValues.put("Field1", new Boolean(true));
        entryValues.put("Field2", new Double(1.));
        entryValues.put("Field3", new Character('c'));
        entryValues.put("Field4", new Integer(27));
        entryValues.put("Field5", new String("Ubiquity"));
        entryValues.put("Field6", new HashMap<String, String>());

        return entryValues;
    }


    private IFieldDefinition fieldDefinition(final String name,
                                             final Type type,
                                             final boolean isIndexed,
                                             final boolean isMandatory) {
        return new IFieldDefinition() {
            public String getName() {
                return name;
            }

            public Type getType() {
                return type;
            }

            public boolean isIndexed() {
                return isIndexed;
            }

            public boolean isMandatory() {
                return isMandatory;
            }
        };
    }
}
