# Maven artifact для Java, который позволяет записывать Коллекции объектов в CSV файлы

1. [Способы работы](#title1)
2. [Известные проблемы](#title2)
3. [Описание интерфейсов](#title3)
   - [CSVWriter](#title3-1)
   - [СSVContent](#title3-2)
4. [Описание аннотаций](#title4)
   - [@CSVData](#title4-1)
   - [@CSVField](#title4-2)
5. [Примеры использования](#title5)  
   -[Примеры использования аннотаций](#title5-1)  
   -[Примеры использования CSVWriterReflection](#title5-2)  

## <a id="title1"> Способы взаимодействия </a>

Для записи в файл используется два варианта
- Реализация интерфейса `CSVContent`.  Метод `toCSVFile()` возвращает строку для записи в CSV-файле
- Механизм рефлексии и использование аннотаций `@CSVData` и `@CSVField`

## <a id="title2"> Известные проблемы </a>

Проблемы, которые есть на данный момент в классе:
- Запись Коллекций, которые не унаследованы от `Collection`
- Запись в файл _параметризированных _ объектов
- Запись в файл **пустых коллекций** (Выкидывается `Exception`, хотя по логике работы, должна происходить запись заголовка)
- Запись в файл полей, которые помечены как `@CSVField`, но не имеют перегруженного метода `toString()` для корректного строкового предоставления
## <a id ="title3"> Описание интерфейсов </a>
Далее описаны интерфесы, которые используются для работы
### <a id ="title3-1"> CSVWriter </a>
Определяет интерфейс, который является общим для всех классов, которые работают с CSV-файлами
```java
   /**
     *
     * @param data  данные которые необходимо записать в файл (@NonNull)
     * @param fileName  название файла (@NonNull)
     * @throws IOException  если файла не существует, будет выброшенно исключение
     * @throws RuntimeException  ошибка вызванные в результате работы с ReflectionAPI*
     */
    void writeToFile(@NonNull Collection<?> data, @NonNull String fileName) throws IOException, RuntimeException;
```
### <a id ="title3-2"> СSVContent </a>
Определяет интерфейс, который определяет классы с методом для записи в CSV-файл
```java
   /**
    *
    * @return Строковое представление класса для записи в CSVFile
    */
   String toCSVFile();
```
## <a id ="title4"> Описание аннотаций </a>
Аннотации для записи в файл
### <a id ="title4-1"> @CSVData </a>
```java
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CSVData {

   /**
    *
    * @return Необходимо ли включать все поля класса в файл (Не используется)
    */
   boolean isAllField() default  false;
}
```
### <a id ="title4-2"> @CSVField </a>
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CSVField {
    /**
     *
     * name for csv name
     * default String.empty()
     *
     */
    String key() default  "";
    /**
     *
     * Class of object, необходим, чтобы ускорить получение класса у колекции
     *
     */
    Class<?> type() default  Object.class;
    /**
     *
     * Опрделеяет, явялется ли поле в классе Коллекцией
     * если false использует реализацию Object toString()
     *
     */
    boolean isCollection() default  false;
    /**
     *
     *Задаёт класс коллекции для isCollection()
     *
     */
    Class<?> collectionClass() default Collection.class;
}
```
## <a id="title5"> Примеры использования </a>

### <a id="title5-1"> Примеры использования аннотаций </a>

```java  
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@CSVData
public class Animal implements CSVContent {
    @CSVField
    private int age;
    @CSVField(key = "name", type = String.class)
    private String name;
    @CSVField(key = "area", type = String.class)
    private String area;
    @CSVField(type = Description.class, collectionClass = List.class, isCollection = true)
    private List<Description> descriptionList;
    @CSVField(key = "descriptionArray", type = Description.class)
    private Description[] descriptionArray;
    @CSVField(type = Description.class)
    private Description description;
    @Override
    public String toCSVFile() {
        return String.format("%s;%s;%d\n", name, area, age);
    }
}
```
```java
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dog extends  Animal{

    @CSVField()
    private String color;


    public Dog(String color, int age, String name, String area, List<Description> descriptionList, Description[] descriptionArray, Description description){
        super(age, name, area, descriptionList, descriptionArray, description);
        this.color = color;
    }
}
```
```java
@Getter
@Setter
@AllArgsConstructor
public class LittleDog extends  Dog {
    @Builder
    public LittleDog(String color, int age, String name, String area, List<Description> descriptionList, Description[] descriptionArray, Description description){
        super(color, age, name, area, descriptionList, descriptionArray, description);
    }
}

```
### <a id="title5-1"> Примеры использования CSVWriterReflection </a>

```java  
 List<Description> myClasses = List.of(
        new Description("E"),
        new Description("S"),
        new Description("T")
);
List<Animal> littleDogs = List.of(
        new LittleDog("black", 11, "Tom", "Minsk",myClasses, myClasses.toArray(new Description[0]), new Description("E")),
        new LittleDog("white", 13, "Tobby", "Moscow",myClasses, myClasses.toArray(new Description[0]), new Description("E")),
        new LittleDog("red", 1132, "Alex", "Grodno",myClasses, myClasses.toArray(new Description[0]), new Description("E")),
        new LittleDog("grey", 124214, "Momas", "Petersburg",myClasses, myClasses.toArray(new Description[0]), new Description("E"))
        );
CSVWriter csvWriter = new CSVWriterReflection(); 
csvWriter.writeToFile(littleDogs, "file.csv");  
```
Результат вывода

```
color ; age ; name ; area ; descriptionList ; descriptionArray ; description
black ; 11 ; Tom ; Minsk ; [ E ; S ; T ] ; [ E ; S ; T ] ; { E }
white ; 13 ; Tobby ; Moscow ; [ E ; S ; T ] ; [ E ; S ; T ] ; { E }
red ; 1132 ; Alex ; Grodno ; [ E ; S ; T ] ; [ E ; S ; T ] ; { E }
grey ; 124214 ; Momas ; Petersburg ; [ E ; S ; T ] ; [ E ; S ; T ] ; { E }
```