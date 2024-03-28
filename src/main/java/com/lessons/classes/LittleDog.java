package com.lessons.classes;


import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class LittleDog extends  Dog {
    @Builder
    public LittleDog(String color, int age, String name, String area, List<Description> descriptionList, Description[] descriptionArray, Description description){
        super(color, age, name, area, descriptionList, descriptionArray, description);
    }
}
