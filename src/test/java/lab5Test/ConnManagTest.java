package lab5Test;

import lab5.ConnectionManager;
import org.testng.annotations.Test;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnManagTest {
    @Test
    public void getConnectionTest() throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        connection.close();
    }
}
