package lab4Test;
import lab4.Book;
import lab4.CountBook;
import lab4.Library;
import lab4.Worker;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LibraryT {
    private List<CountBook> countBooks = new ArrayList<>();
    private Worker worker;

    @BeforeTest
    public void before() {
        Book book = new Book.BuilderB()
                .setId(1)
                .setName("Name")
                .setIssueDay(LocalDate.of(2019, 2, 21))
                .setReleaseDay(LocalDate.of(2019, 7, 9))
                .build();
        CountBook countBook = new CountBook.BuilderCB()
                .setId(1)
                .setCount(200)
                .setBook(book)
                .build();
        countBooks.add(countBook);
        worker = new Worker.Builder()
                .setId(1)
                .setBirthDay(LocalDate.of(2000, 1, 1))
                .setFirstName("FirstName")
                .setLastName("LastName")
                .setSalary(2000.0)
                .build();
    }

    @Test
    public void pharmacyBuilderTest() {
        Library pharmacy = new Library.BuilderL()
                .setId(1)
                .setCountBooks(countBooks)
                .setName("Name")
                .setWorker(worker)
                .build();
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void pharmacyBuilderNegativeTest() {
        Library pharmacy = new Library.BuilderL()
                .setId(-1)
                .setCountBooks(countBooks)
                .setName("")
                .setWorker(worker)
                .build();
    }
}
