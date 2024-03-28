import com.lessons.CSV.CSVWriterReflection;
import com.lessons.classes.Animal;
import com.lessons.classes.Dog;
import com.lessons.classes.LittleDog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;


public class TestCSVWriter {

    List<Animal> animals;

    List<Dog> dogs;

    List<LittleDog> littleDogs;

    @Before
    public void before() {
        List<Animal.MyClass> myClasses = List.of(
                new Animal.MyClass("E"),
                new Animal.MyClass("S"),
                new Animal.MyClass("T")
        );

        animals = List.of(new Animal(123, "Brown", "Minsk", myClasses),
                new Animal(123, "Jessika", "Moscow", myClasses),
                new Animal(1220, "Tom", "SpPetersburg", myClasses),
                new Animal(10124, "Alex", "Grodno", myClasses),
                new Animal(10141, "", "Russian", myClasses),
                new Animal(10124, "Victor", "Belarus", myClasses),
                new Animal(10124, "Olga", "Vitebsk", myClasses));


        dogs = List.of(new Dog("black", 11, "Tom", "Minsk", myClasses),
                new Dog("white", 13, "Tobby", "Moscow", myClasses),
                new Dog("red", 1132, "Alex", "Grodno", myClasses),
                new Dog("grey", 124214, "Momas", "Petersburg", myClasses));

        littleDogs = List.of(new LittleDog("black", 11, "Tom", "Minsk", myClasses),
                new LittleDog("white", 13, "Tobby", "Moscow", myClasses),
                new LittleDog("red", 1132, "Alex", "Grodno", myClasses),
                new LittleDog("grey", 124214, "Momas", "Petersburg", myClasses));
    }

    @After
    public void after() {

    }

    @Test(expected = NullPointerException.class)
    public void testNullPointerCollectionException() throws IOException {
        new CSVWriterReflection().writeToFile(null, "file.csv");
    }

    @Test(expected = NullPointerException.class)
    public void testNullPointerFileNameException() throws IOException {
        new CSVWriterReflection().writeToFile(animals, null);
    }

    @Test
    public void testFileNotFoundException() {
        assertThrows(IOException.class,
                () -> new CSVWriterReflection().writeToFile(animals, "file1.csv")
        );
    }

    @Test
    public void testEmptyCollectionException() {
        assertThrows(IllegalArgumentException.class,
                () -> new CSVWriterReflection().writeToFile(new ArrayList<Animal>(), "src/test/resources/file.csv")
        );
    }

}
