package lab4Test;

import lab4.Book;
import lab4.CountBook;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class CountBookT {
    Book book;

    @BeforeTest
    public void before() {
        book = new Book.BuilderB()
                .setId(1)
                .setName("Name")
                .setIssueDay(LocalDate.of(2015, 4, 25))
                .setReleaseDay(LocalDate.of(2014, 1, 10))
                .build();
    }


    @Test(expectedExceptions = IllegalStateException.class)
    public void countBookBuilderNegativeTest() {
        CountBook countBook = new CountBook.BuilderCB()
                .setId(-1)
                .setBook(book)
                .setCount(-3)
                .build();
    }

    @Test
    public void countBookBuilderTest() {
        CountBook countBook = new CountBook.BuilderCB()
                .setId(2)
                .setBook(book)
                .setCount(300)
                .build();
    }

}
