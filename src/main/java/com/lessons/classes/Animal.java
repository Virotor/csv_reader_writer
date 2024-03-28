package com.lessons.classes;

import com.lessons.CSV.CSVField;
import com.lessons.CSV.CSVData;
import com.lessons.CSVInterfaces.CsvContent;
import lombok.*;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@CSVData
public class Animal implements CsvContent {


    @CSVField
    private int age;
    @CSVField(key = "name", type = String.class)
    private String name;
    @CSVField(key = "area", type = String.class)
    private String area;

    @CSVField(type = Description.class, collectionClass = List.class, isCollection = true)
    private List<Description> descriptionList;


    @CSVField(key = "descriptionArray", type = Description.class)
    private Description[] descriptionArray;


    @CSVField(type = Description.class)
    private Description description;



    @Override
    public String toCSVFile() {
        return String.format("%s;%s;%d\n", name, area, age);
    }
}
