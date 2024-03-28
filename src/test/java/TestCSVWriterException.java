import com.lessons.CSV.CSVWriterReflection;
import com.lessons.classes.Animal;
import com.lessons.classes.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;


public class TestCSVWriterException {

    List<Animal> animals;


    @Before
    public void before() {
        List<Description> descriptionList = List.of(
                new Description("E"),
                new Description("S"),
                new Description("T")
        );
       animals = List.of(
                new Animal(10, "Tom", "Belarus", descriptionList, descriptionList.toArray(new Description[0]), new Description("E")),
                new Animal(10, "Tom", "Belarus", descriptionList, descriptionList.toArray(new Description[0]), new Description("E")),
                new Animal(10, "Tom", "Belarus", descriptionList, descriptionList.toArray(new Description[0]), new Description("E")),
                new Animal(10, "Tom", "Belarus", descriptionList, descriptionList.toArray(new Description[0]), new Description("E")));

    }

    @After
    public void after() {

    }

    @SuppressWarnings("DataFlowIssue")
    @Test(expected = NullPointerException.class)
    public void testNullPointerCollectionException() throws IOException {
        new CSVWriterReflection().writeToFile(null, "file.csv");
    }

    @SuppressWarnings("DataFlowIssue")
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
