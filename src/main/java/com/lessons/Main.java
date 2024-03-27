package com.lessons;

import com.lessons.classes.Animal;
import com.lessons.CSV.CSVWriterInterface;
import com.lessons.CSV.CsvContent;

import java.io.FileNotFoundException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//        PropertyConfigurator.configure(Main.class.getResourceAsStream("log4j.properties"));


        List<CsvContent> animals = List.of(
                new Animal(10, "Tom", "Belarus"),
                new Animal(10, "Tom", "Belarus"),
                new Animal(10, "Tom", "Belarus"),
                new Animal(10, "Tom", "Belarus"));
        CSVWriterInterface csvWriterInterface = new CSVWriterInterface();
        csvWriterInterface.writeToFile(animals, "src/main/resources/animal.csv");
    }
}