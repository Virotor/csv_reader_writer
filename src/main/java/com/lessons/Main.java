package com.lessons;

import com.lessons.CSV.CSVWriterReflection;
import com.lessons.classes.Animal;
import com.lessons.classes.LittleDog;
import com.lessons.classes.Description;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        //        PropertyConfigurator.configure(Main.class.getResourceAsStream("log4j.properties"));

        List<Description> myClasses = List.of(
                new Description("E"),
                new Description("S"),
                new Description("T")
        );
        List<Animal> animals = List.of(
                new Animal(10, "Tom", "Belarus",myClasses, myClasses.toArray(new Description[0]), new Description("E")),
                new Animal(10, "Tom", "Belarus",myClasses, myClasses.toArray(new Description[0]), new Description("E")),
                new Animal(10, "Tom", "Belarus",myClasses, myClasses.toArray(new Description[0]), new Description("E")),
                new Animal(10, "Tom", "Belarus",myClasses, myClasses.toArray(new Description[0]), new Description("E")));
        CSVWriterReflection csvWriterReflection = new CSVWriterReflection();
        csvWriterReflection.writeToFile(animals, "src/main/resources/animalRef.csv");

        List<Animal> dogs = List.of(
                new LittleDog("black", 11, "Tom", "Minsk",myClasses, myClasses.toArray(new Description[0]), new Description("E")),
                new LittleDog("white", 13, "Tobby", "Moscow",myClasses, myClasses.toArray(new Description[0]), new Description("E")),
                new LittleDog("red", 1132, "Alex", "Grodno",myClasses, myClasses.toArray(new Description[0]), new Description("E")),
                new LittleDog("grey", 124214, "Momas", "Petersburg",myClasses, myClasses.toArray(new Description[0]), new Description("E")));
        csvWriterReflection = new CSVWriterReflection();
        csvWriterReflection.writeToFile(dogs, "src/main/resources/dogRef.csv");


//        CSVReaderReflection<LittleDog> csvReaderReflection  = new CSVReaderReflection<>();
//        var result = csvReaderReflection.readCSV(LittleDog.class, "src/main/resources/dogRef.csv");
//        System.out.println(result);
    }
}