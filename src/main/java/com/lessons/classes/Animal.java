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


    private int age;
    @CSVField(key = "name")
    private String name;
    @CSVField(key = "area")
    private String area;


    private List<MyClass> myClass;

    @CSVData
    @Data
    @AllArgsConstructor
    public static class MyClass{
        private String text;
        @Override
        public  String toString(){
            return text;
        }
    }


    @Override
    public String toCSVFile() {
        return String.format("%s;%s;%d\n", name, area, age);
    }
}
