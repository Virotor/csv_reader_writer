import com.lessons.CSV.CSVWriterReflection;
import com.lessons.classes.Animal;
import com.lessons.classes.Dog;
import com.lessons.classes.LittleDog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertThrows;


public class TestCSVWriter {

    List<Animal> animals;

    List<Dog> dogs;

    List<LittleDog> littleDogs;

    @Before
    public void before() {
        animals = List.of(new Animal(123, "Brown", "Minsk"),
                new Animal(123, "Jessika", "Moscow"),
                new Animal(1220, "Tom", "SpPetersburg"),
                new Animal(10124, "Alex", "Grodno"),
                new Animal(10141, "", "Russian"),
                new Animal(10124, "Victor", "Belarus"),
                new Animal(10124, "Olga", "Vitebsk"));


        dogs = List.of(  new Dog("black", 11, "Tom", "Minsk"),
                new Dog("white", 13, "Tobby", "Moscow"),
                new Dog("red", 1132, "Alex", "Grodno"),
                new Dog("grey", 124214, "Momas", "Petersburg"));

        littleDogs = List.of(  new LittleDog("black", 11, "Tom", "Minsk"),
                new LittleDog("white", 13, "Tobby", "Moscow"),
                new LittleDog("red", 1132, "Alex", "Grodno"),
                new LittleDog("grey", 124214, "Momas", "Petersburg"));
    }

    @After
    public void after() {

    }

    @Test(expected = NullPointerException.class)
    public void testNullPointerCollectionCSVWriter() throws IOException {
        new CSVWriterReflection().writeToFile(null, "file.csv");
    }

    @Test(expected = NullPointerException.class)
    public void testNullPointerFileNameCSVWriter() throws IOException {
        new CSVWriterReflection().writeToFile(animals, null);
    }

    @Test
    public void testFileNotFoundException() {
        assertThrows(IOException.class, () -> new CSVWriterReflection().writeToFile(animals, "file1.csv"));
    }
}
