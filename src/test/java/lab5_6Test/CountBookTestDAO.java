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

public class CountBookTestDAO {
        private Connection connection;
        private CountBook countBook;
        private CountBook countBookForUpdate;
        private CountBookDAO countBookDAO;

        @BeforeTest
        public void before() throws SQLException {
            connection = ConnectionManager.getConnection();
            countBookDAO = new CountBookDAO(connection);
            Book book = new Book.BuilderB()
                    .setId(4)
                    .setName("Name")
                    .setIssueDay(LocalDate.of(2019, 12, 12))
                    .build();
            new BookDAO(connection).create(book);
            Library pharmacy = new Library.BuilderL()
                    .setId(1)
                    .setName("Library #1")
                    .setWorker(null)
                    .build();
            countBook = new CountBook.BuilderCB()
                    .setId(5)
                    .setCount(100)
                    .setBook(book)
                    .setLibrary(pharmacy)
                    .build();
            List<CountBook> list = new ArrayList<>();
            list.add(countBook);
            pharmacy.setCountBooks(list);
            countBookForUpdate = new CountBook.BuilderCB()
                    .setId(5)
                    .setCount(150)
                    .setBook(book)
                    .setLibrary(pharmacy)
                    .build();
        }

        @AfterTest
        public void after() throws SQLException {
            connection.close();
        }

        @Test
        public void createTest() throws SQLException {
            assertEquals(countBookDAO.create(countBook), true);
        }

        @Test
        public void readTest() throws SQLException {
            assertEquals(countBookDAO.read(5), countBook);
        }

        @Test
        public void resultSetToObjTest() throws SQLException {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM count_books  WHERE id=5");
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
                assertEquals(countBookDAO.resultSetToObj(resultSet), countBook);
        }

        @Test
        public void updateTest() throws SQLException {
            assertEquals(countBookDAO.update(countBookForUpdate), true);
        }

        @Test
        public void deleteTest() throws SQLException {
            assertEquals(countBookDAO.delete(countBook), true);
        }

    
}
