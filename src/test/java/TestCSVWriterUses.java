import com.lessons.CSV.CSVData;
import com.lessons.CSV.CSVField;
import com.lessons.CSV.CSVWriterReflection;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestCSVWriterUses {


    List<TestRecord> testRecords;
    String exceptedHeader = "";

    String exceptedContent = "";

    @CSVData
    record TestRecord(
            @CSVField
            String name,
            @CSVField
            Integer age,
            @CSVField
            String[] names,
            @CSVField(isCollection = true, type = Integer.class, collectionClass = List.class)
            List<Integer> amounts,
            @CSVField(isCollection = true, type = TestRecordText.class, collectionClass = List.class)
            List<TestRecordText> testRecordsText,
            @CSVField
            TestRecordText testRecordText) {
        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(name);
            stringBuilder.append(" ; ");
            stringBuilder.append(age);
            stringBuilder.append(" ; ");
            stringBuilder.append("[ ");
            for (var element : names) {
                stringBuilder.append(element);
                stringBuilder.append(" ; ");
            }
            stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), "] ; ");
            stringBuilder.append("[ ");
            for (var element : amounts) {
                stringBuilder.append(element);
                stringBuilder.append(" ; ");
            }
            stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), "] ; ");
            stringBuilder.append("[ ");
            for (var element : testRecordsText) {
                stringBuilder.append(element);
                stringBuilder.append(" ; ");
            }
            stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), "] ; { ");
            stringBuilder.append(testRecordText);
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    @CSVData
    record TestRecordText(
            @CSVField
            String text,

            @CSVField
            String text2

    ) {
        @Override
        public String toString() {
            return text + " ; "  + text2;
        }
    }


    @Before
    public void before() throws IOException {
        List<TestRecordText> descriptionList = List.of(
                new TestRecordText("SampleText", "Uses"),
                new TestRecordText("Text", "Uses"),
                new TestRecordText("Sample", "Uses")
        );
        testRecords = List.of(
                new TestRecord("Jon", 17, new String[]{"Names", "Names"}, List.of(1, 2, 4), descriptionList, new TestRecordText("1", "2")),
                new TestRecord("Victor", 19, new String[]{"Me", "Papa"}, List.of(1, 2, 5, 6, 6), descriptionList, new TestRecordText("2", "3")),
                new TestRecord("Marry", 25, new String[]{"Yours", "Mama"}, List.of(4, 5, 5, 6, 6), descriptionList, new TestRecordText("3", "4")),
                new TestRecord("Olga", 12, new String[]{"Names", "Values"}, List.of(1, 2, 6, 6), descriptionList, new TestRecordText("4", "5"))
        );
        exceptedHeader = "name ; age ; names ; amounts ; testRecordsText ; testRecordText";
        exceptedContent = testRecords.stream().map(TestRecord::toString).reduce((a,b) -> a+'\n'+b).get();
        CSVWriterReflection csvWriterReflection = new CSVWriterReflection();
        csvWriterReflection.writeToFile(testRecords, "src/test/resources/file.csv");
    }


    @Test
    public void testWriteHeader() {
        try (BufferedReader fileInputStream = new BufferedReader(new FileReader("src/test/resources/file.csv"))) {
            String actualHeader = fileInputStream.readLine();
            assertEquals(exceptedHeader, actualHeader);
        } catch (Exception ignored) {
        }
    }

    @Test
    public void testWriteContent() {
        try (BufferedReader fileInputStream = new BufferedReader(new FileReader("src/test/resources/file.csv"))) {
            String temp = fileInputStream.readLine();
            StringBuilder stringBuilder = new StringBuilder();
            while (temp != null && (temp = fileInputStream.readLine()) != null) {
                stringBuilder.append(temp);
                stringBuilder.append("\n");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
            assertEquals(exceptedContent, stringBuilder.toString());
        } catch (Exception ignored) {
        }
    }

}
