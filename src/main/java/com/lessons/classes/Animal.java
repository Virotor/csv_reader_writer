package com.lessons.classes;

import com.lessons.CSV.CSVAnnotation;
import com.lessons.CSV.CSVData;
import com.lessons.CSVInterfaces.CsvContent;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@CSVData
public class Animal implements CsvContent {

    @CSVAnnotation(key = "age")
    private int age;
    @CSVAnnotation(key = "name")
    private String name;
    @CSVAnnotation(key = "area")
    private String area;




    @Override
    public String toCSVFile() {
        return String.format("%s;%s;%d\n", name, area, age);
    }
}
