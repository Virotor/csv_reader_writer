package com.lessons;

import com.lessons.CSV.CSVWriterReflection;
import com.lessons.CSVInterfaces.CsvContent;
import com.lessons.classes.Animal;
import com.lessons.classes.Dog;
import com.lessons.classes.LittleDog;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        //        PropertyConfigurator.configure(Main.class.getResourceAsStream("log4j.properties"));


        List<Animal> animals = List.of(
                new Animal(10, "Tom", "Belarus"),
                new Animal(10, "Tom", "Belarus"),
                new Animal(10, "Tom", "Belarus"),
                new Animal(10, "Tom", "Belarus"));
        CSVWriterReflection csvWriterReflection = new CSVWriterReflection();
        csvWriterReflection.writeToFile(animals, "src/main/resources/animalRef.csv");

        List<Animal> dogs = List.of(
                new LittleDog("black", 11, "Tom", "Minsk"),
                new LittleDog("white", 13, "Tobby", "Moscow"),
                new LittleDog("red", 1132, "Alex", "Grodno"),
                new LittleDog("grey", 124214, "Momas", "Petersburg"));
        csvWriterReflection = new CSVWriterReflection();
        csvWriterReflection.writeToFile(dogs, "src/main/resources/dogRef.csv");
    }
}