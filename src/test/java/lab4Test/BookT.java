package lab4Test;

import lab4.Book;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class BookT {
    @Test(expectedExceptions = IllegalStateException.class)
    public void bookBuilderTest() {
        Book book = new Book.BuilderB()
                .setId(1)
                .setName("Name")
                .setIssueDay(LocalDate.of(2019, 1, 11))
                .setReleaseDay(LocalDate.of(2019, 1, 11))
                .build();
    }

}
