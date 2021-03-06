package com.ubiquity.demos.musicbrainz;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.ubiquity.datastorage.kernel.interfaces.IFieldTemplate.Type;

public class DataParser {

    private final String registry;
    private final String identifier;


    public DataParser(String registry, String identifier) {
        assert!Strings.isNullOrEmpty(registry);
        assert !Strings.isNullOrEmpty(identifier);

        this.registry = registry;
        this.identifier = identifier;
    }


    public void parse(String fileName, TypeProvider typeProvider, IDataInsertor dataInsertor) {
        TextFileReader textFileReader = new TextFileReader();

        try {
            parseImpl(fileName, textFileReader, typeProvider, dataInsertor);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void parseImpl(String fileName, TextFileReader textFileReader, TypeProvider typeProvider, IDataInsertor dataInsertor) throws IOException {
        textFileReader.open(fileName);
        textFileReader.read(new LineProcessor(dataInsertor, typeProvider));
    }


    public interface IDataInsertor {
        void insert(Map<String, Object> dataFields);
    }


    public interface TypeProvider {
                Type getType(String registry, String identifier, String field);
    }


    private class LineProcessor implements ILineProcessor {

        private final IDataInsertor dataInsertor;
        private final TypeProvider typeProvider;
        private int lineIndex;
        private List<String> columns;
        private Map<String, Type> columnsType;

        public LineProcessor(IDataInsertor dataInsertor, TypeProvider typeProvider) {
            this.dataInsertor = dataInsertor;
            this.typeProvider = typeProvider;
            this.lineIndex = 0;
            this.columns = new ArrayList<String>();
            this.columnsType = new HashMap<String, Type>();
        }

        @Override
        public void processLine(String line) {
            if (lineIndex == 0) {
                processHeaderLine(line);
            } else if (lineIndex > 1) {
                processDataLine(line);
            }
            lineIndex++;
        }

        private void processHeaderLine(String line) {
            String[] columnsName = line.split("\\|");

            for (int index = 0 ; index < columnsName.length ; index++) {
                processHeaderColumn(columnsName[index]);
            }
        }

        private void processHeaderColumn(String headerColumn) {
            String columnName = headerColumn.trim();

            columns.add(columnName);
            columnsType.put(columnName, typeProvider.getType(registry, identifier, columnName));
        }

        private void processDataLine(String line) {
            String[] columnsvalue = line.split("\\|");

            if (columnsvalue.length == columns.size()) {
                dataInsertor.insert(getDataLineValues(columnsvalue));
            }
        }

        private Map<String, Object> getDataLineValues(String[] columnsvalue) {
            Map<String, Object> dataFields = new HashMap<String, Object>();

            for (int index = 0; index < columnsvalue.length; index++) {
                String columnName = columns.get(index);
                Type columnType = columnsType.get(columnName);
                String columnValue = columnsvalue[index].trim();

                dataFields.put(columnName, typeConvertion(columnValue, columnType));
            }
            return dataFields;
        }

        private Object typeConvertion(String columnValue, Type columnType) {
            if (columnType != Type.STRING && Strings.isNullOrEmpty(columnValue)) {
                return null;
            }

            switch (columnType) {
                case INTEGER:
                    return Integer.parseInt(columnValue);

                case STRING:
                    return columnValue;

                case BOOLEAN:
                    return Boolean.parseBoolean(columnValue);
            }

            return null;
        }
    }

}
