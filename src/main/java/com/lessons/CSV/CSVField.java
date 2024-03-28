package com.lessons.CSV;

import java.lang.annotation.*;
import java.lang.reflect.Type;
import java.util.Collection;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CSVField {
    String key() default  "";
    Class<?> type() default  Object.class;
    boolean isCollection() default  false;
    Class<?> collectionClass() default Collection.class;
}
