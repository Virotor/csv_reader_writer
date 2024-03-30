package com.lessons.CSV;

import java.lang.annotation.*;
import java.lang.reflect.Type;
import java.util.Collection;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CSVField {
    /**
     *
     * name for csv name
     * default String.empty()
     *
     */
    String key() default  "";
    /**
     *
     * Class of object, необходим, чтобы ускорить получение класса у колекции
     *
     */
    Class<?> type() default  Object.class;
    /**
     *
     * Опрделеяет, явялется ли поле в классе Коллекцией
     * если false использует реализацию Object toString()
     *
     */
    boolean isCollection() default  false;
    /**
     *
     *Задаёт класс коллекции для isCollection()
     *
     */
    Class<?> collectionClass() default Collection.class;
}
