package com.ubiquity.tests.basic;


import static com.ubiquity.core.datastore.IFieldTemplate.Kind.*;
import static com.ubiquity.core.datastore.IFieldTemplate.Type.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ubiquity.core.datastore.IFieldTemplate;
import com.ubiquity.core.datastore.IFieldTemplate.Kind;
import com.ubiquity.core.datastore.IFieldTemplate.Type;
import com.ubiquity.core.datastore.IRecordTemplate;

public class DataDescriptorParser {

    private String registery;
    private String name;
    private Map<String, FieldTemplate> fieldTemplates;


    public DataDescriptorParser() {
        this.registery = null;
        this.name = null;
        this.fieldTemplates = new HashMap<String, FieldTemplate>();
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
        final Collection<IFieldTemplate> fieldTemplates = new ArrayList<IFieldTemplate>();

        for (IFieldTemplate iter : this.fieldTemplates.values()) {
            fieldTemplates.add(iter);
        }

        dataInsertor.insert(registery, new IRecordTemplate() {

            @Override
            public String getIdentifier() {
                return name;
            }

            @Override
            public Collection<IFieldTemplate> getFieldTemplates() {
                return fieldTemplates;
            }
        });
    }


    private enum Section {HEADER, IRRELEVANT, DEFINITION, INDEX}


    public interface IDataInsertor {
                void insert(String registery, IRecordTemplate recordTemplate);
    }

    private class LineProcessor implements ILineProcessor {

        private int lineIndex;

        public LineProcessor() {
            lineIndex = 0;
        }

        @Override
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
            registery = getStore(fullDataName);
            name = getName(fullDataName);
        }

        private void processDefinition(String line) {
            String fieldName = getFieldName(line);
            String strType = getFieldType(line);
            Type type = strTypeToFieldType(strType);

            fieldTemplates.put(fieldName, new FieldTemplate(fieldName, type));
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

            fieldTemplates.get(fieldName).setKind(kind);
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

        private Type strTypeToFieldType(String strType) {
            assert strType != null;

            if (strType.equals("integer") || strType.equals("smallint"))
                return INTEGER;

            if (strType.contains("text") || strType.contains("character varying"))
                return STRING;

            if (strType.contains("boolean"))
                return BOOLEAN;

            throw new IllegalArgumentException("Don't know field-type \"" + strType + "\"");
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

    private class FieldTemplate implements IFieldTemplate {

        private final String name;
        private final Type type;
        private Kind kind;

        public FieldTemplate(String name, Type type) {
            this.name = name;
            this.type = type;
            this.kind = OPTIONAL;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Type getType() {
            return type;
        }

        @Override
        public Kind getKind() {
            return kind;
        }

        public void setKind(Kind kind) {
            this.kind = kind;
        }
    }
}
