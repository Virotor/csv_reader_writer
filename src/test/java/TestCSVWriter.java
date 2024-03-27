import com.lessons.CSV.CSVWriterReflection;
import com.lessons.classes.Animal;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class TestCSVWriter {

    List<Animal> animals;

    @Before
    public void before() {
        animals = List.of(new Animal(123, "Brown", "Minsk"),
                new Animal(123, "Jessika", "Moscow"),
                new Animal(1220, "Tom", "SpPetersburg"),
                new Animal(10124, "Alex", "Grodno"),
                new Animal(10141, "", "Russian"),
                new Animal(10124, "Victor", "Belarus"),
                new Animal(10124, "Olga", "Vitebsk"));
    }

    @After
    public void after() {

    }

    @Test(expected = NullPointerException.class)
    public void testNullPointerCollectionCSVWriter() throws FileNotFoundException {
        new CSVWriterReflection().writeToFile(null, "file.csv");
    }

    @Test(expected = NullPointerException.class)
    public void testNullPointerFileNameCSVWriter() throws FileNotFoundException {
        new CSVWriterReflection().writeToFile(animals, null);
    }

    @Test
    public void testFileNotFoundException() {
        assertThrows(FileNotFoundException.class, () -> new CSVWriterReflection().writeToFile(animals, "file1.csv"));
    }
}
