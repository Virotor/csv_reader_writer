package com.lessons.classes;


import com.lessons.CSV.CSVAnnotation;
import com.lessons.CSV.CSVData;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@CSVData
public class Dog extends  Animal{

    @CSVAnnotation(key = "color")
    private String color;


    public Dog(String color, int age, String name, String area){
        super(age, name, area);
        this.color = color;
    }
}
