package com.lessons.classes;


import com.lessons.CSV.CSVField;
import com.lessons.CSV.CSVData;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dog extends  Animal{

    @CSVField()
    private String color;


    public Dog(String color, int age, String name, String area, List<Animal.MyClass> myClasses){
        super(age, name, area, myClasses);
        this.color = color;
    }
}
