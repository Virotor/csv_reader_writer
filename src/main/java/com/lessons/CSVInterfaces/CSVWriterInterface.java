package com.lessons.CSVInterfaces;

import com.lessons.CSV.CSVWriter;
import lombok.Getter;
import lombok.NonNull;
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
public class CSVWriterInterface implements CSVWriter {

    public void writeToFile(@NonNull Collection<?> data, @NonNull String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if(!(data.iterator().next() instanceof  CSVContent)){
            throw  new IllegalArgumentException("Class for write not implements interface CSVContent");
        }
        if (!file.isFile()) {
            throw new FileNotFoundException(String.format("File with name %s not found", fileName));
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            for (var element : data) {
                var content = (CSVContent) element;
                byte[] buffer = content.toCSVFile().getBytes();
                fileOutputStream.write(buffer, 0, buffer.length);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
