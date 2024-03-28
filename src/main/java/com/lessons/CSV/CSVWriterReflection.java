package com.lessons.CSV;

import lombok.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CSVWriterReflection implements CSVWriter {
    @Override
    public void writeToFile(@NonNull Collection<?> data, @NonNull String fileName)
            throws IOException,
            RuntimeException {
        if (!(new File(fileName).isFile())) {
            throw new FileNotFoundException(String.format("File with name %s not found", fileName));
        }
        if (data.isEmpty()) {
            throw new IllegalArgumentException("Empty collection");
        }
        List<Field> fields = getFieldsForWrite(data);
        if (fields.isEmpty()) {
            throw new IllegalArgumentException("Not field for write");
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            String header = getHeaderForWrite(fields);
            fileOutputStream.write(header.getBytes(), 0, header.length());
            for (var element : data) {
                String result = getStringForWriteLine(element, fields);
                fileOutputStream.write(result.getBytes(), 0, result.length());
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private List<Field> getFieldsForWrite(Collection<?> data) {
        var topLevelClass = getParamitrClass(data);
        List<Class<?>> classes = new ArrayList<>(10);
        classes.add(topLevelClass);
        while (true) {
            var temp = classes.getLast().getSuperclass();
            if (Arrays.stream(temp.getAnnotations()).noneMatch(e -> e.annotationType().equals(CSVData.class))) {
                break;
            }
            classes.add(temp);

        }
        return classes.stream().
                flatMap(clazz ->
                        Arrays.stream(clazz.getDeclaredFields()).
                                filter(
                                        e -> e.isAnnotationPresent(CSVField.class)))
                .toList();
    }

    private Class<?> getParamitrClass(Collection<?> data) {
        return data.iterator().next().getClass();
    }

    private String getHeaderForWrite(List<Field> fields) {
        StringBuilder header = new StringBuilder();
        for (var field : fields) {
            field.setAccessible(true);
            String temp = field.getDeclaredAnnotation(CSVField.class).key();
            if(temp.isEmpty()){
                header.append(field.getName());
            }else{
                header.append(temp);
            }
            header.append(" ; ");
        }
        header.replace(header.length() - 3, header.length(), "\n");
        return header.toString();
    }

    private String getStringForWriteLine(Object element, List<Field> fields) throws IllegalAccessException {
        StringBuilder result = new StringBuilder();
        for (var field : fields) {
            result.append(field.get(element).toString());
            result.append(" ; ");
        }
        result.replace(result.length() - 3, result.length(), "\n");
        return result.toString();
    }
}
