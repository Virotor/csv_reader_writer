package com.lessons;

import com.lessons.CSV.CSVWriterReflection;
import com.lessons.classes.Animal;
import com.lessons.CSV.CSVWriterInterface;
import com.lessons.CSV.CsvContent;
import com.lessons.classes.Dog;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//        PropertyConfigurator.configure(Main.class.getResourceAsStream("log4j.properties"));


        List<CsvContent> animals = List.of(
                new Animal(10, "Tom", "Belarus"),
                new Animal(10, "Tom", "Belarus"),
                new Animal(10, "Tom", "Belarus"),
                new Animal(10, "Tom", "Belarus"));
        CSVWriterReflection csvWriterReflection = new CSVWriterReflection();
        csvWriterReflection.writeToFile(animals, "src/main/resources/animalRef.csv");

        List<Dog> dogs = List.of(
                new Dog("red", 10, "Tom", "Belarus"),
                new Dog("red",10, "Tom", "Belarus"),
                new Dog("red",10, "Tom", "Belarus"),
                new Dog("red",10, "Tom", "Belarus"));
        csvWriterReflection = new CSVWriterReflection();
        csvWriterReflection.writeToFile(dogs, "src/main/resources/dogRef.csv");
    }
}