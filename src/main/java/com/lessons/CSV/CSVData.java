package com.lessons.CSV;


import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CSVData {

    /*
    // Определяет необходимо ли включить все поля класса в файл
    //
     */
    boolean isAllField() default  false;
}
