package com.ubiquity.tests.basic;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class DataParser {

    public DataParser() {
        init();
    }

    private void init() {
    }

    public void parse(String fileName, IDataInsertor dataInsertor) {
        init();
        TextFileReader textFileReader = new TextFileReader();

        try {
            parseImpl(fileName, textFileReader, dataInsertor);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseImpl(String fileName, TextFileReader textFileReader, IDataInsertor dataInsertor) throws IOException {
        textFileReader.open(fileName);
        textFileReader.read(new LineProcessor());
    }


    public interface IDataInsertor {
        void insert(String dataShelf, String dataIdentifier, Map<String, Object> dataFields);
    }

    private class LineProcessor implements ILineProcessor {
        public void processLine(String line) {

        }
    }

}
