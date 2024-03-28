package com.lessons.CSV;

import lombok.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
        List<Field> fields = getFieldsForWrite(getParamitrClass(data));
        if (fields.isEmpty()) {
            throw new IllegalArgumentException("Not field for write");
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            String header = getHeaderForWrite(fields);
            fileOutputStream.write(header.getBytes(), 0, header.length());
            for (var element : data) {
                StringBuilder result = new StringBuilder(getStringForWriteLine(element, fields));
                result.replace(result.length() - 3, result.length(), "\n");
                fileOutputStream.write(result.toString().getBytes(), 0, result.length());
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        } catch (InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Field> getFieldsForWrite(Class<?> childClass) {
        List<Class<?>> classes = new ArrayList<>(10);
        classes.add(childClass);
        while (true) {
            var temp = classes.getLast().getSuperclass();
            if (Arrays.stream(temp.getAnnotations()).noneMatch(e -> e.annotationType().equals(CSVData.class))) {
                break;
            }
            classes.add(temp);

        }
        return classes.stream().
                flatMap(clazz -> Arrays.stream(clazz.getDeclaredFields()).
                        filter(
                                e -> e.isAnnotationPresent(CSVField.class)))
                .peek(e -> e.setAccessible(true))
                .toList();
    }

    private Class<?> getParamitrClass(Collection<?> data) {
        return data.iterator().next().getClass();
    }

    private String getHeaderForWrite(List<Field> fields) {
        StringBuilder header = new StringBuilder();
        for (var field : fields) {
            String temp = field.getDeclaredAnnotation(CSVField.class).key();
            if (temp.isEmpty()) {
                header.append(field.getName());
            } else {
                header.append(temp);
            }
            header.append(" ; ");
        }
        header.replace(header.length() - 3, header.length(), "\n");
        return header.toString();
    }

    private String getStringForWriteLine(Object element, List<Field> fields) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        StringBuilder result = new StringBuilder();
        for (var field : fields) {
            var annotation = field.getAnnotation(CSVField.class);
            if (annotation.isCollection() && !field.getType().isArray()) {
                var elementList = (List<?>) field.get(element);
                var resField = getFieldsForWrite(annotation.type());
                writeSequence(result, elementList.toArray(), resField);
            } else if (field.getType().isArray()) {
                var res = getFieldsForWrite(field.getType().getComponentType());
                var temp = (Object[]) field.get(element);
                writeSequence(result, temp, res);
            } else if (field.getType().isAnnotationPresent(CSVData.class)) {
                result.append(getStringForWriteLine(field.get(element), getFieldsForWrite(field.getType())));
            } else {
                result.append(field.get(element));
            }
            result.append(" ; ");
        }
        return result.toString();
    }

    private void writeSequence(StringBuilder result, Object[] temp, List<Field> res) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        result.append("[ ");
        for (Object el : temp) {
            result.append(getStringForWriteLine(el, res));
        }
        result.deleteCharAt(result.length() - 1);
        result.deleteCharAt(result.length() - 1);
        result.deleteCharAt(result.length() - 1);
        result.append(" ]");
    }
}
