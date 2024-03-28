package com.lessons.classes;


import com.lessons.CSV.CSVData;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class LittleDog extends  Dog {
    @Builder
    public LittleDog(String color, int age, String name, String area, List<MyClass> myClasses){
        super(color,age, name, area,myClasses);
    }
}
