package com.lessons.CSV;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;


import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;



@Slf4j
public class CSVWriterReflection implements CSVWriter {



    @Override
    public void writeToFile(@NonNull Collection<?> data, @NonNull String fileName)
            throws IOException,
            IllegalArgumentException, NullPointerException {
        if (!(new File(fileName).isFile())) {
            log.error(String.format("File with name %s not found", fileName));
            throw new FileNotFoundException(String.format("File with name %s not found", fileName));
        }
        if (data.isEmpty()) {
            log.error("Empty collection. Can't write empty collection on file");
            throw new IllegalArgumentException("Empty collection");
        }
        List<Field> fields = getFieldsForWrite(getParamitrClass(data));
        if (fields.isEmpty()) {
            log.error(String.format("In class %s not found fields for write", getParamitrClass(data)));
            throw new InvalidClassException("This object don't have field for write in CSV file");
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            String header = getHeaderForWrite(fields);
            fileOutputStream.write(header.getBytes(), 0, header.length());
            for (var element : data) {
                StringBuilder result = new StringBuilder(getStringForWriteLine(element, fields));
                result.replace(result.lastIndexOf(" ; "), result.length(), "\n");
                fileOutputStream.write(result.toString().getBytes(), 0, result.length());
            }
        } catch (IllegalAccessException e) {
            log.error(String.format("In class %s not access to get field", getParamitrClass(data)));
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    /**
     *
     * @param childClass класс, который явялется потомком в иерархии наследования
     * @return Возвращает поля классов, которые помечены @CSVField всех классов в иерархии наследования
     */
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

    /**
     *
     * @param fields Список полей классов в иерархии наследования
     * @return Возвращает строку заголовка для CSV файла
     */
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

    /**
     *
     * @param element Object для записи в файл
     * @param fields Поля класса Object для записи в файл
     * @return Возвращает строку, которая является представлением Object в классе
     * @throws IllegalAccessException Ошибка доступа к полю
     */
    private String getStringForWriteLine(Object element, List<Field> fields) throws IllegalAccessException {
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
                result.append("{ ");
                result.append(getStringForWriteLine(field.get(element), getFieldsForWrite(field.getType())));
                result.replace(result.lastIndexOf(";"), result.length(),"} ; ");
            } else {
                result.append(field.get(element));
                result.append(" ; ");
            }

        }
        return result.toString();
    }

    /**
     *
     * @param result StringBuilder для создания строки в файл
     * @param temp Массив объектов
     * @param fields Массив полей для записи
     * @throws IllegalAccessException Ошибка доступа к полю класса
     */
    private void writeSequence(StringBuilder result, Object[] temp, List<Field> fields) throws IllegalAccessException {
        result.append("[ ");
        for (Object el : temp) {
            if (fields.isEmpty()) {
                result.append(el.toString());
                result.append(" ; ");
            } else {
                result.append(getStringForWriteLine(el, fields));
            }

        }
        result.replace(result.lastIndexOf(";"), result.length(), "] ; ");
    }
}
