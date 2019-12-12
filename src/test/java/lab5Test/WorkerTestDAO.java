package lab5Test;

import lab5.ConnectionManager;
import lab5.Worker;
import lab5.WorkerDAO;
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

public class WorkerTestDAO {
    private Connection connection;
    private Worker worker;
    private Worker workerForUpdate;
    private WorkerDAO workerDAO;
    private List<Worker> list = new ArrayList<>();

    @BeforeTest
    public void before() throws SQLException {
        connection = ConnectionManager.getConnection();
        workerDAO = new WorkerDAO(connection);
        worker = new Worker.Builder()
                .setId(3)
                .setBirthDay(LocalDate.of(2000, 12, 12))
                .setFirstName("First name")
                .setLastName("Last name")
                .setSalary(2000.0)
                .build();
        workerForUpdate = new Worker.Builder()
                .setId(3)
                .setBirthDay(LocalDate.of(2000, 12, 12))
                .setFirstName("First name")
                .setLastName("Last name")
                .setSalary(4000.0)
                .build();
        Worker worker2 = new Worker.Builder()
                .setId(5)
                .setBirthDay(LocalDate.of(1999, 12, 12))
                .setFirstName("First name")
                .setLastName("Last_name")
                .setSalary(1000.0)
                .build();
        list.add(worker2);
        list.add(worker);

    }

    @AfterTest
    public void after() throws SQLException {
        //  connection.close();
    }

    @Test
    public void createTest() {
        assertEquals(workerDAO.create(worker), true);
    }

    @Test
    public void readTest() {
        assertEquals(workerDAO.read(3), worker);
    }

    @Test
    public void resultSetToObjTest() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM workers  WHERE id=3");
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next())
            assertEquals(workerDAO.resultSetToObj(resultSet), worker);
    }

    @Test
    public void findAllTest() throws SQLException {
        assertEquals(workerDAO.findAll(), list);
    }

    @Test
    public void updateTest() {
        assertEquals(workerDAO.update(workerForUpdate), true);
    }

    @Test
    public void deleteTest() {
        assertEquals(workerDAO.delete(worker), true);
    }
}
