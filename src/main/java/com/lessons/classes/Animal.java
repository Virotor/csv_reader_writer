package com.lessons.classes;

import com.lessons.CSV.CsvContent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Animal implements CsvContent {

    private int age;
    private String name;
    private String area;




    @Override
    public String toCSVFile() {
        return String.format("%s;%s;%d\n", name, area, age);
    }

    @Override
    public String toCSVFileHead() {
        return "name;area;age";
    }
}
