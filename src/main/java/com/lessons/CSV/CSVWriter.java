package com.lessons.CSV;

import java.util.Collection;

public interface CSVWriter {

    public void writeToFile(Collection<?> data, String fileName);
}
