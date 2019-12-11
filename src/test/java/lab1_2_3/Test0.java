package lab1_2_3;

import lab5Test.Book;
import lab5Test.CountBook;
import lab5Test.Library;
import lab5Test.Worker;
import service.BookService;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

public class Test0 {
    private static final String LINESTRING = "there are more than twenty characters";
    List<Book> arrayBook = new ArrayList<>();
    List<CountBook> count_bk = new ArrayList<>();
    Library library = new Library();

    private BookService bookService;

    @BeforeTest
    public void before() {
        arrayBook.add(new Book("1984", LocalDate.of(2019, 12, 1), LocalDate.of(2019, 12, 6)));
        arrayBook.add(new Book("Майстер та Маргарита", LocalDate.of(2019, 4, 19), LocalDate.of(2019, 6, 19)));
        arrayBook.add(new Book("Три Товариша", LocalDate.of(2020, 1, 2), LocalDate.of(2020, 1, 2)));
        arrayBook.add(new Book("Маленький принц", LocalDate.now(), LocalDate.of(2020, 3, 2)));
        arrayBook.add(new Book("Квіти для Елджерона", LocalDate.of(2019, 6, 8), LocalDate.of(2019, 7, 15)));
        for (int i = 0; i < arrayBook.size(); i++)
            count_bk.add(new CountBook(arrayBook.get(i), (i + 4)));


        Worker worker = new Worker.Builder().build();
        library = new Library.BuilderL().setName("Library #1")
                .setWorker(worker)
                .setCountBooks(count_bk)
                .build();
    }

    @BeforeTest
    public void  createBookService(){
        bookService = new BookService(library);
    }

    @Test
    public void personBuilderTest() {
        Worker worker = new Worker.Builder()
                .setFirstName("Alex")
                .setLastName("Mod")
                .setBirthDay(LocalDate.now())
                .setSalary(3000.0)
                .build();
    }

    @Test
    public void bookBuilderTest() {
        Book book = new Book.BuilderB()
                .setName("Alladin")
                .setIssueDay(LocalDate.now())
                .setreleaseDay(LocalDate.now())
                .build();
    }

    @Test
    public void countBookBuilderTest() {
        CountBook countBook = new CountBook.BuilderCB()
                .setCount(100)
                .setBook(null)
                .build();
    }

    @Test
    public void libraryBuilderTest() {
        Library library = new Library.BuilderL()
                .setName("CHNU")
                .setCountBooks(null)
                .setWorker(null)
                .build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void negativeBuilderFirstNameTest() {
        new Worker.Builder().setFirstName(LINESTRING).build();
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void negativeSetNameMedTest(){
        new Book().setName(LINESTRING);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void negativeSetCountTest(){
        new CountBook().setCount(-300);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void negativeSetFirstNameTest(){
        Worker person = new Worker.Builder().build();
        person.setFirstName(LINESTRING);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void negativeSetLastNameTest(){
        Worker person = new Worker.Builder().build();
        person.setLastName(LINESTRING);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void negativeSetSalaryNameTest(){
        Worker person = new Worker.Builder().build();
        person.setSalary(-100.00);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void negativeSetNamePharmacyTest(){
        new Library().setName(LINESTRING);
    }

    @Test
    public void findBookByNameTest(){
        String name = "1984";

        bookService.findBooksByName(name);

        assertEquals(name,arrayBook.get(0).getName());
    }

    @Test
    public void sortBookByCountTest(){
        List<CountBook> sortedBooks = bookService.getSortedByCount();

        assertEquals(library.getBooks(),sortedBooks);
    }
}
