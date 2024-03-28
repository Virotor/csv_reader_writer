package com.lessons.CSV;

import lombok.NonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

public interface CSVWriter {


    /**
     *
     * @param data  данные которые необходимо записать в файл (@NonNull)
     * @param fileName  название файла (@NonNull)
     * @throws IOException  если файла не существует, будет выброшенно исключение
     * @throws RuntimeException  ошибка вызванные в результате работы с ReflectionAPI*
     */
    void writeToFile(@NonNull Collection<?> data, @NonNull String fileName) throws IOException, RuntimeException;
}
