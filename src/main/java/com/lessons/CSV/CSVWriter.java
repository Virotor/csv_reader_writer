package com.lessons.CSV;

import lombok.NonNull;

import java.io.FileNotFoundException;
import java.util.Collection;

public interface CSVWriter {

    public void writeToFile(@NonNull  Collection<?> data, @NonNull  String fileName) throws FileNotFoundException,RuntimeException;
}
