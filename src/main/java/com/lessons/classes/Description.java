package com.lessons.classes;

import com.lessons.CSV.CSVData;
import com.lessons.CSV.CSVField;
import lombok.AllArgsConstructor;
import lombok.Data;

@CSVData
@Data
@AllArgsConstructor
public class Description {

    @CSVField
    private String text;

}