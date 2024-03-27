package com.lessons.classes;


import com.lessons.CSV.CSVAnnotation;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dog extends  Animal{

    @CSVAnnotation(key = "color")
    private String color;

    @Builder
    public Dog(String color, int age, String name, String area){
        super(age, name, area);
        this.color = color;
    }
}
