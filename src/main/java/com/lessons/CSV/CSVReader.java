package com.lessons.CSV;

import lombok.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface CSVReader<T> {

    public List<T> readCSV(@NonNull Class<T> tClass, @NonNull String fileName) throws IOException, RuntimeException;
}
