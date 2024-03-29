package lab5_6Test;

import lab5_6.Book;
import lab5_6.BookDAO;
import lab5_6.ConnectionManager;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.testng.AssertJUnit.assertEquals;


public class BookTestDAO {
    private BookDAO bookDAO;
    private Book book;
    private Connection connection;

    @BeforeTest
    public void before() throws SQLException {
        connection = ConnectionManager.getConnection();
        bookDAO = new BookDAO(connection);
        book = new Book.BuilderB()
                .setId(4)
                .setName("Name")
                .setIssueDay(LocalDate.of(2019, 10, 3))
                .setReleaseDay(LocalDate.of(2019, 3, 10))
                .build();
        Book bookForUpdate = new Book.BuilderB()
                .setId(4)
                .setName("Name")
                .setIssueDay(LocalDate.of(2019, 5, 20))
                .setReleaseDay(LocalDate.of(2019, 11, 28))
                .build();
    }

    @AfterTest
    public void after() throws SQLException {
        connection.close();
    }

    @Test
    public void createTest() {
        assertEquals(bookDAO.create(book), true);
    }

    @Test
    public void readTest() {
        assertEquals(bookDAO.read(4), book);
    }

    @Test
    public void resultSetToObjTest() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM books  WHERE id=4");
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next())
            assertEquals(bookDAO.resultSetToObj(resultSet), book);
    }


    @Test
    public void deleteTest() {
        assertEquals(bookDAO.delete(book), true);
    }

}