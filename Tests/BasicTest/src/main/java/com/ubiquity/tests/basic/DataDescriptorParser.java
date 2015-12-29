package com.ubiquity.tests.basic;


import static com.ubiquity.core.datastore.IFieldDefinition.DataType.*;
import static com.ubiquity.core.datastore.IFieldDefinition.Kind.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ubiquity.core.datastore.IDataDefinition;
import com.ubiquity.core.datastore.IFieldDefinition;
import com.ubiquity.core.datastore.IFieldDefinition.DataType;
import com.ubiquity.core.datastore.IFieldDefinition.Kind;

public class DataDescriptorParser {

    private String shelf;
    private String name;
    private Map<String, FieldDefinition> fieldDefinitions;


    public DataDescriptorParser() {
        this.shelf = null;
        this.name = null;
        this.fieldDefinitions = new HashMap<String, FieldDefinition>();
    }

    public void parse(String fileName, IDataInsertor dataInsertor) {
        TextFileReader textFileReader = new TextFileReader();

        try {
            parseImpl(fileName, textFileReader);
            insertAllData(dataInsertor);
        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseImpl(String fileName, TextFileReader textFileReader) throws IOException {
        textFileReader.open(fileName);
        textFileReader.read(new LineProcessor());
    }

    private void insertAllData(IDataInsertor dataInsertor) {
        final Collection<IFieldDefinition> fieldDef = new ArrayList<IFieldDefinition>();

        for (IFieldDefinition iter : fieldDefinitions.values()) {
            fieldDef.add(iter);
        }

        dataInsertor.insert(shelf, new IDataDefinition() {
            public String getIdentifier() {
                return name;
            }

            public Collection<IFieldDefinition> getFieldDefinitions() {
                return fieldDef;
            }
        });
    }


    private enum Section {HEADER, IRRELEVANT, DEFINITION, INDEX}


    public interface IDataInsertor {
        void insert(String shelf, IDataDefinition dataDefinition);
    }

    private class LineProcessor implements ILineProcessor {

        private int lineIndex;

        public LineProcessor() {
            lineIndex = 0;
        }

        public void processLine(String line) {
            switch (getSection(line)) {
                case HEADER:
                    processHeader(line);
                    break;

                case DEFINITION:
                    processDefinition(line);
                    break;

                case INDEX:
                    processIndex(line);
                case IRRELEVANT:
                default:
            }

            lineIndex++;
        }

        private Section getSection(String line) {
            if (lineIndex == 0)
                return Section.HEADER;
            else if (isIrrelevantSection(line))
                return Section.IRRELEVANT;
            else if (line.startsWith("    \""))
                return Section.INDEX;
            else
                return Section.DEFINITION;
        }

        private boolean isIrrelevantSection(String line) {
            return lineIndex < 3 || line.startsWith("Indexes:") || line.trim().isEmpty();
        }

        private void processHeader(String line) {
            String fullDataName = getFullDataName(line);
            shelf = getStore(fullDataName);
            name = getName(fullDataName);
        }

        private void processDefinition(String line) {
            String fieldName = getFieldName(line);
            String strType = getFieldType(line);
            DataType dataType = strTypeToDataType(strType);

            fieldDefinitions.put(fieldName, new FieldDefinition(fieldName, dataType));
        }

        private String getFieldName(String line) {
            String[] values = line.split("\\|");
            return values[0].trim();
        }

        private String getFieldType(String line) {
            String[] values = line.split("\\|");
            return values[1].trim();
        }

        private void processIndex(String line) {
            String fieldName = getFieldNameFromIndexLine(line);
            Kind kind = getFieldKindFromIndexLine(line);

            fieldDefinitions.get(fieldName).setKind(kind);
        }

        private String getFieldNameFromIndexLine(String line) {
            String[] result = line.split("[\\(\\)]");
            return result[1];
        }

        private Kind getFieldKindFromIndexLine(String line) {
            Kind fieldKind = Kind.OPTIONAL;

            if (line.contains("PRIMARY")) {
                fieldKind = PRIMARY;
            } else if (line.contains("btree")) {
                fieldKind = INDEXED;
            }

            return fieldKind;
        }

        private DataType strTypeToDataType(String strType) {
            assert strType != null;

            if (strType.equals("integer") || strType.equals("smallint"))
                return INTEGER;

            if (strType.contains("text") || strType.contains("character varying"))
                return STRING;

            if (strType.contains("boolean"))
                return BOOLEAN;

            throw new IllegalArgumentException("Don't know data-type \"" + strType + "\"");
        }

        private String getFullDataName(String line) {
            String[] values = line.split("\\\"");
            return values[1];
        }

        private String getStore(String fullDataName) {
            String result = "";
            String[] values = fullDataName.split("\\.");

            if (values.length == 0) {
                /// throw
            }
            else {
                result = values[0];
                for (int i = 1 ; i < values.length - 1; i++) {
                    result += "." + values[i];
                }
            }

            return result;
        }

        private String getName(String fullDataName) {
            String result = "";
            String[] values = fullDataName.split("\\.");

            if (values.length == 0) {
                /// throw
            }
            else {
                result = values[values.length - 1];
            }

            return result;
        }
    }

    private class FieldDefinition implements IFieldDefinition {

        private final String name;
        private final DataType type;
        private Kind kind;

        public FieldDefinition(String name, DataType type) {
            this.name = name;
            this.type = type;
            this.kind = OPTIONAL;
        }

        public String getName() {
            return name;
        }

        public DataType getType() {
            return type;
        }

        public Kind getKind() {
            return kind;
        }

        public void setKind(Kind kind) {
            this.kind = kind;
        }
    }
}
