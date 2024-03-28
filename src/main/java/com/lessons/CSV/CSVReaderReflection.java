package com.lessons.CSV;

import lombok.NonNull;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CSVReaderReflection<T> implements CSVReader<T> {

    @Override
    public List<T> readCSV(@NonNull Class<T> tClass, @NonNull String fileName) throws IOException, RuntimeException {
        File file = new File(fileName);
        if (!file.isFile()) {
            throw new FileNotFoundException(String.format("File with name %s not found", fileName));
        }
        if (!file.canRead()) {
            throw new IllegalArgumentException(String.format("File with name %s not available", fileName));
        }
        List<T> result = new ArrayList<>();
        try (BufferedReader fis = new BufferedReader(new FileReader(fileName))) {
            String line = fis.readLine();
            Field[] fields = getFields(line, tClass);
            while (line != null) {

                line = fis.readLine();
                if(line == null){
                    break;
                }
                result.add(this.createObject(fields, splitData(line), tClass));
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    private Field[] getFields(String header, Class<T> tClass) {
        List<Class<? super T>> classes = new ArrayList<>(10);
        classes.add(tClass);
        while (true) {
            var temp = classes.getLast().getSuperclass();
            if (Arrays.stream(temp.getAnnotations()).noneMatch(e -> e.annotationType().equals(CSVData.class))) {
                break;
            }
            classes.add(temp);
        }
        List<Field> fields = classes.stream().
                flatMap(clazz ->
                        Arrays.stream(clazz.getDeclaredFields()).
                                filter(
                                        e -> e.isAnnotationPresent(CSVField.class)))
                .toList();
        String[] names = splitData(header);
        Field[] fieldsResult = new Field[names.length];
        for (int i = 0; i < names.length; i++) {
            int finalI = i;
            Optional<Field> field = fields.stream().filter(e -> e.isAnnotationPresent(CSVField.class) && e.getAnnotation(CSVField.class).key().equals(names[finalI])
            ).findFirst();
            if (field.isPresent()) {
                fieldsResult[i] = field.get();
            } else {
                field = fields.stream().filter(e -> e.isAnnotationPresent(CSVField.class) && e.getName().equals(names[finalI])
                ).findFirst();
                if (field.isPresent()) {
                    fieldsResult[i] = field.get();
                }
            }
        }
        for(Field field : fieldsResult){
            field.setAccessible(true);
        }
        return fieldsResult;
    }

    private String[] splitData(String line) {
        return line.split(" ; ");
    }

    private T createObject(Field[] fieldsName, String[] data, Class<T> tClass) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        var constructor = tClass.getConstructor();
        T temp = constructor.newInstance();
        for (int i = 0; i < fieldsName.length; i++) {
            if (!fieldsName[i].getName().equals("-")) {
                fieldsName[i].set(temp, fieldsName[i].getType().cast(data[i]));
            }
        }
        return temp;
    }
}
