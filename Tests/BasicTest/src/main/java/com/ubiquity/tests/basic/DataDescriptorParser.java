package com.ubiquity.tests.basic;


import java.io.FileNotFoundException;
import java.io.IOException;

public class DataDescriptorParser {
    private int lineIndex;
    private String store;
    private String name;

    public DataDescriptorParser() {
        init();
    }

    private void init() {
        this.lineIndex = 0;
        this.store = null;
        this.name = null;
    }

    public String getStore() {
        return store;
    }

    public String getName() {
        return name;
    }

    public void parse(String fileName) {
        init();
        TextFileReader textFileReader = new TextFileReader();

        try {
            parseImpl(fileName, textFileReader);
        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseImpl(String fileName, TextFileReader textFileReader) throws IOException {
        textFileReader.open(fileName);
        textFileReader.read(new LineProcessor());
    }

    private class LineProcessor implements ILineProcessor {
        public void processLine(String line) {
            if (lineIndex == 0) {
                String fullDataName = getFullDataName(line);
                store = getStore(fullDataName);
                name = getName(fullDataName);
                System.out.println(fullDataName);
            } else if (lineIndex >= 3) {
                System.out.println(lineIndex + ": " + line);
            }
            lineIndex++;
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
}
