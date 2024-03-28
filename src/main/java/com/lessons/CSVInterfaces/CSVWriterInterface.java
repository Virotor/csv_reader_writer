package com.lessons.CSVInterfaces;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;


@Getter
@Setter
@Slf4j
public class CSVWriterInterface {

    public void writeToFile(Collection<CsvContent> data, String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.isFile()) {
            throw new FileNotFoundException(String.format("File with name %s not found", fileName));
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            for (var element : data) {
                byte[] buffer = element.toCSVFile().getBytes();
                fileOutputStream.write(buffer, 0, buffer.length);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}