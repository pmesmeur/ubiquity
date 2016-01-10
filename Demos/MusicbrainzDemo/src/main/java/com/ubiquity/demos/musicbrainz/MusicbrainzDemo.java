package com.ubiquity.demos.musicbrainz;

import com.google.common.base.Strings;
import com.ubiquity.datastorage.DataStorage;
import com.ubiquity.datastorage.kernel.exceptions.ValueOfPrimaryFieldAlreadyInsertedException;
import com.ubiquity.datastorage.kernel.interfaces.IFieldTemplate;
import com.ubiquity.datastorage.kernel.interfaces.IRecordTemplate;

import java.io.File;
import java.util.Collection;
import java.util.Map;

public class MusicbrainzDemo implements DataParser.TypeProvider {

    private final DataStorage dataStorage;
    String DIR_NAME = "Demos/MusicbrainzDemo/src/main/resources/musicbrainz";


    protected MusicbrainzDemo() {
        dataStorage = new DataStorage();
    }


    public static void main(String[] args) {
        MusicbrainzDemo musicbrainzDemo = new MusicbrainzDemo();
        musicbrainzDemo.run();
    }


    private void run() {
        final File folder = new File(DIR_NAME);
        scanFolder(folder);
        dataStorage.shutdown();
    }


    private void scanFolder(File folder) {
        for (File fileEntry : folder.listFiles()) {
            String fileName = fileEntry.getName();

            if (fileName.endsWith(".dsc")) {
                insertData(fileName);
            }
        }
    }


    private void insertData(String dscFileName) {
        DataInsertor dataInsertor = new DataInsertor();

        dataInsertor.parseAndInsertDataDescriptor(DIR_NAME + "/" + dscFileName);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String dataFileName = dscFileName.replace(".dsc", ".txt");
        dataInsertor.parseAndInsertData(DIR_NAME + "/" + dataFileName);
    }

    @Override
    public IFieldTemplate.Type getType(String registry, String identifier, String field) {
        assert!Strings.isNullOrEmpty(registry);
        assert!Strings.isNullOrEmpty(identifier);
        assert!Strings.isNullOrEmpty(field);

        Collection<IFieldTemplate> fieldTemplates = dataStorage.getRegistry(registry)
                .getRegister(identifier)
                .getDefinition().getFieldTemplates();

        for (IFieldTemplate fieldTemplate : fieldTemplates) {
            if (fieldTemplate.getIdentifier().equals(field)) {
                return fieldTemplate.getType();
            }
        }

        return null;
    }

    private class DataInsertor
            implements DataDescriptorParser.IDataInsertor, DataParser.IDataInsertor {

        private String registry;
        private String identifier;

        public DataInsertor() {
        }

        private void parseAndInsertDataDescriptor(String fileName) {
            DataDescriptorParser dataDescriptorParser = new DataDescriptorParser();
            dataDescriptorParser.parse(fileName, this);
        }

        private void parseAndInsertData(String fileName) {
            DataParser dataParser = new DataParser(registry, identifier);
            dataParser.parse(fileName, MusicbrainzDemo.this, this);
        }

        @Override
        public void insert(String registry, IRecordTemplate recordTemplate) {
            this.registry = registry;
            this.identifier = recordTemplate.getIdentifier();

            dataStorage.insertRegistry(registry);
            dataStorage.insertRegister(registry, recordTemplate);
            System.out.println("Data: {" + registry + "," + identifier + "}");
        }

        @Override
        public void insert(Map<String, Object> dataFields) {
            try {
                dataStorage.getRegistry(registry).getRegister(identifier).insert(dataFields);
            }
 catch (ValueOfPrimaryFieldAlreadyInsertedException e) {
            }
        }

    }

}
