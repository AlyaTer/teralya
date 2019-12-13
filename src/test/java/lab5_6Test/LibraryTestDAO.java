package lab5_6Test;

import lab5_6.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

public class LibraryTestDAO {
    private Connection connection;
    private Library library;
    private Library libraryForUpdate;
    private LibraryDAO libraryDAO;
    private Worker worker;
    List<CountBook> list = new ArrayList<>();

    @BeforeTest
    public void before() throws SQLException {
        connection= ConnectionManager.getConnection();
        libraryDAO = new LibraryDAO(connection);
        worker = new Worker.Builder()
                .setId(5)
                .setSalary(1000.0)
                .setLastName("Last_name")
                .setFirstName("First name")
                .setBirthDay(LocalDate.of(1999, 12, 12))
                .build();
        Book book = new Book.BuilderB()
                .setId(4)
                .setName("Name")
                .setIssueDay(LocalDate.of(2019, 10, 12))
                .setReleaseDay(LocalDate.of(2019, 10, 12))
                .build();
        Book book2 = new Book.BuilderB()
                .setId(5)
                .setName("Name2")
                .setIssueDay(LocalDate.of(2019, 10, 12))
                .setReleaseDay(LocalDate.of(2019, 10, 12))
                .build();
        library = new Library.BuilderL()
                .setId(5)
                .setName("Library #1")
                .setWorker(worker)
                .build();
        CountBook countBook1 = new CountBook.BuilderCB()
                .setId(5)
                .setCount(100)
                .setBook(book)
                .setLibrary(library)
                .build();
        //new CountBookDAO(connection).create(countBook1);
        CountBook countBook2 = new CountBook.BuilderCB()
                .setId(6)
                .setCount(150)
                .setBook(book2)
                .setLibrary(library)
                .build();
        // new CountBookDAO(connection).create(countBook2);
        list.add(countBook2);
        list.add(countBook1);
        library.setCountBooks(list);
        libraryForUpdate = new Library.BuilderL()
                .setId(5)
                .setCountBooks(list)
                .setName("Library new")
                .setWorker(worker)
                .build();
    }

    @AfterTest
    public void after() throws SQLException {
        //  new WorkerDAO(connection).delete(worker);
        connection.close();
    }

    @Test
    public void createTest() throws SQLException {
        assertEquals(libraryDAO.create(library), true);
    }

    @Test
    public void readTest() throws SQLException {
        assertEquals(libraryDAO.read(5), library);
    }

    @Test
    public void resultSetToObjTest() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM library WHERE id=5");
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next())
            assertEquals(libraryDAO.resultSetToObj(resultSet), library);
    }

    @Test
    public void updateTest() throws SQLException {
        assertEquals(libraryDAO.update(libraryForUpdate), true);
    }

    @Test
    public void getListCountBookTest() throws SQLException {
        assertEquals(libraryDAO.getListCountBook(library), list);
    }

    @Test
    public void deleteTest() throws SQLException {
        assertEquals(libraryDAO.delete(library), true);
    }

}
