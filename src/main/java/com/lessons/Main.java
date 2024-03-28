package com.lessons;

import com.lessons.CSV.CSVReaderReflection;
import com.lessons.CSV.CSVWriterReflection;
import com.lessons.CSVInterfaces.CsvContent;
import com.lessons.classes.Animal;
import com.lessons.classes.Dog;
import com.lessons.classes.LittleDog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        //        PropertyConfigurator.configure(Main.class.getResourceAsStream("log4j.properties"));

        List<Animal.MyClass> myClasses = List.of(
                new Animal.MyClass("E"),
                new Animal.MyClass("S"),
                new Animal.MyClass("T")
        );
        List<Animal> animals = List.of(
                new Animal(10, "Tom", "Belarus",myClasses),
                new Animal(10, "Tom", "Belarus",myClasses),
                new Animal(10, "Tom", "Belarus",myClasses),
                new Animal(10, "Tom", "Belarus",myClasses));
        CSVWriterReflection csvWriterReflection = new CSVWriterReflection();
        csvWriterReflection.writeToFile(animals, "src/main/resources/animalRef.csv");

        List<Animal> dogs = List.of(
                new LittleDog("black", 11, "Tom", "Minsk", myClasses),
                new LittleDog("white", 13, "Tobby", "Moscow", myClasses),
                new LittleDog("red", 1132, "Alex", "Grodno", myClasses),
                new LittleDog("grey", 124214, "Momas", "Petersburg", myClasses));
        csvWriterReflection = new CSVWriterReflection();
        csvWriterReflection.writeToFile(dogs, "src/main/resources/dogRef.csv");


        CSVReaderReflection<LittleDog> csvReaderReflection  = new CSVReaderReflection<>();
        var result = csvReaderReflection.readCSV(LittleDog.class, "src/main/resources/dogRef.csv");
        System.out.println(result);
    }
}