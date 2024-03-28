package com.lessons.CSV;


import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CSVData {
    boolean isAllField() default  false;
}
