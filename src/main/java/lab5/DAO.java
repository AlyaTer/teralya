package lab5;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @param <T> entity related with database
 * @param <Key> key on which we will get the object
 */
public interface DAO<T, Key> {
    boolean create(T model) throws SQLException;
    T read(Key key) throws SQLException;
    boolean update(T model) throws SQLException;
    boolean delete(T model) throws SQLException;

    T resultSetToObj(ResultSet rs) throws SQLException;
}