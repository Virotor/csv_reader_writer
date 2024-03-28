package com.lessons.CSV;

import lombok.NonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

public interface CSVWriter {

    void writeToFile(@NonNull Collection<?> data, @NonNull String fileName) throws IOException, RuntimeException;
}
