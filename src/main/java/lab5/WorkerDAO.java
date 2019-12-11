package lab5;

import javax.validation.constraints.NotNull;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class  WorkerDAO implements DAO<lab5.Worker, Integer> {

    /**
     * SQL queries for persons table.
     */
    enum PersonSQL {
        GET("SELECT * FROM persons  WHERE persons.id = (?)"),
        INSERT("INSERT INTO persons (id, first_name, last_name, birthday, salary) VALUES ((?), (?), (?), (?), (?)) RETURNING id"),
        DELETE("DELETE FROM persons WHERE id = (?) RETURNING id"),
        UPDATE("UPDATE persons SET salary = (?) WHERE id = (?) RETURNING id"),

        GET_ALL("SELECT * FROM persons");

        String QUERY;

        PersonSQL(String QUERY) {
            this.QUERY = QUERY;
        }
    }

    /**
     * Connection to database
     */
    private final Connection connection;

    /**
     *  Init DB connection
     *
     * @param connection of DB
     */
    public WorkerDAO(@NotNull final Connection connection) {
        this.connection = connection;
    }

    /**
     * Create Worker in DB
     *
     * @param worker for create
     * @return false if Worker already exist, true if creating success
     */
    @Override
    public boolean create(lab5.Worker worker) {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(PersonSQL.INSERT.QUERY)) {
            statement.setInt(1, worker.getId());
            statement.setString(2, worker.getFirstName());
            statement.setString(3, worker.getLastName());
            statement.setDate(4, Date.valueOf(worker.getBirthday()));
            statement.setDouble(5, worker.getSalary());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Select Worker by last_name.
     *
     * @param id for select.
     * @return return valid entity if she exist. If entity does not exist return empty Worker with id = -1.
     */
    @Override
    public CountBook read(Integer id) {
        lab5.Worker result = new lab5.Worker();
        result.setId(-1);

        try (PreparedStatement statement = connection.prepareStatement(PersonSQL.GET.QUERY)) {
            statement.setInt(1, id);
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result.setId(Integer.parseInt(resultSet.getString("id")));
                result.setBirthday(LocalDate.parse(resultSet.getDate("birthday").toString()));
                result.setSalary(resultSet.getDouble("salary"));
                result.setFirstName(resultSet.getString("first_name"));
                result.setLastName(resultSet.getString("last_name"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Update Worker`s salary by id
     *
     * @param worker new worker state
     * @return true if success, false if fail
     */
    @Override
    public boolean update(lab5.Worker worker) {
        boolean result = false;

        try(PreparedStatement statement = connection.prepareStatement(PersonSQL.UPDATE.QUERY)) {
            statement.setDouble(1, worker.getSalary());
            statement.setInt(2, worker.getId());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Delete Worker by id
     *
     * @param worker for delete
     * @return true if worker was deleted, false if worker not exist
     */
    @Override
    public boolean delete(lab5.Worker worker) {
        boolean result = false;

        try(PreparedStatement statement = connection.prepareStatement(PersonSQL.DELETE.QUERY)) {
            statement.setInt(1, worker.getId());
            result = statement.executeQuery().next();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Convert ResultSet into Worker
     *
     * @param rs ResultSet to convert
     * @return Worker object
     * @throws SQLException
     */
    public lab5.Worker resultSetToObj(ResultSet rs) throws SQLException {
        lab5.Worker worker = new lab5.Worker();

        worker.setId(rs.getInt("id"));
        worker.setFirstName(rs.getString("first_name"));
        worker.setLastName(rs.getString("last_name"));
        worker.setBirthday(rs.getDate("birthday").toLocalDate());
        worker.setSalary(rs.getDouble("salary"));

        return worker;
    }

    public List<lab5.Worker> findAll() throws SQLException {
        List<lab5.Worker> list = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(PersonSQL.GET_ALL.QUERY)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                Worker worker = resultSetToObj(resultSet);
                list.add(worker);
            }
            return list;
        }
    }

}