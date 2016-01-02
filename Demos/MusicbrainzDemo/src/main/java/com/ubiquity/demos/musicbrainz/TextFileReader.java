package com.ubiquity.demos.musicbrainz;


import java.io.*;

public class TextFileReader {

    private BufferedReader br;


    public TextFileReader() {
        br = null;
    }

    void open(String fileName) throws FileNotFoundException {
        br = new BufferedReader(new FileReader(fileName));
    }

    void read(ILineProcessor lineProcessor) throws IOException {
        try {
            String line;

            while ((line = br.readLine()) != null) {
                lineProcessor.processLine(line);
            }
        } finally {
            br.close();
        }
    }

}
