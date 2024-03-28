package com.lessons.classes;


import com.lessons.CSV.CSVField;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dog extends  Animal{

    @CSVField()
    private String color;


    public Dog(String color, int age, String name, String area, List<Description> descriptionList, Description[] descriptionArray, Description description){
        super(age, name, area, descriptionList, descriptionArray, description);
        this.color = color;
    }
}
