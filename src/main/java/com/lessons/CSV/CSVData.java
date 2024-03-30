package com.lessons.CSV;


import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CSVData {

    /**
     *
     * @return Необходимо ли включать все поля класса в файл (Не используется)
     */
    boolean isAllField() default  false;
}
