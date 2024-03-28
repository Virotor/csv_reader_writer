package com.lessons.classes;


import com.lessons.CSV.CSVData;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@CSVData
public class LittleDog extends  Dog {
    @Builder
    public LittleDog(String color, int age, String name, String area){
        super(color,age, name, area);
    }
}
