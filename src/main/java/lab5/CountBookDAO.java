package lab5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountBookDAO implements DAO<CountBook, Integer> {

    /**
     * SQL queries for count_medicines table.
     */
    enum CountMedicineSQL {
        GET("SELECT * FROM count_medicines  WHERE count_medicines.id = (?)"),
        INSERT("INSERT INTO count_medicines (id, medicine_id, count, pharmacy_id) VALUES ((?), (?), (?), (?)) RETURNING id"),
        DELETE("DELETE FROM count_medicines WHERE id = (?) RETURNING id"),
        UPDATE("UPDATE count_medicines SET count = (?) WHERE id = (?) RETURNING id"),

        GET_MED("SELECT * FROM medicines WHERE id = (?)");

        String QUERY;

        CountMedicineSQL(String QUERY) {
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
    public CountBookDAO(final Connection connection) {
        this.connection= connection;
    }

    /**
     * Create CountBook in DB
     *
     * @param countBook foe create
     * @return false if item already exist, true if creating success
     * @throws SQLException
     */
    @Override
    public boolean create(CountBook countBook) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(CountMedicineSQL.INSERT.QUERY)) {
            statement.setInt(1, countBook.getId());
            statement.setInt(2, countBook.getBook().getId());
            statement.setInt(3, countBook.getCount());
            return statement.executeQuery().next();
        }
    }

    /**
     * Select CountBook by id
     *
     * @param count for select
     * @return return valid entity if she exist. If entity does not exist return empty object with id = -1.
     * @throws SQLException
     */
    @Override
    public CountBook read(Integer count) throws SQLException {
        CountBook result = new CountBook();
        result.setId(-1);
        try(PreparedStatement statement = connection.prepareStatement(CountMedicineSQL.GET.QUERY)) {
            statement.setInt(1, count);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result.setId(resultSet.getInt("id"));
                result.setBook(new BookDAO(connection).read(resultSet.getInt("book_id")));
                result.setCount(resultSet.getInt("count"));
                result.setLibrary(new LibraryDAO(connection).read(resultSet.getInt("library_id")));
            }
            return result;
        }
    }



    /**
     * Update CountBook by id
     *
     * @param countBook new state
     * @return true if success, false if fail
     * @throws SQLException
     */
    @Override
    public boolean update(lab5.CountBook countBook) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(CountMedicineSQL.UPDATE.QUERY)) {
            statement.setInt(1, countBook.getCount());
            statement.setInt(2, countBook.getId());

            return statement.executeQuery().next();
        }
    }

    /**
     * Delete CountBook dy id
     *
     * @param countBook for delete
     * @return true if success, false if item not exist
     * @throws SQLException
     */
    @Override
    public boolean delete(lab5.CountBook countBook) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(CountMedicineSQL.DELETE.QUERY)) {
            statement.setInt(1, countBook.getId());

            return statement.executeQuery().next();
        }
    }


    /**
     * Convert ResultSent into CountBook
     *
     * @param rs ResultSet to convert
     * @return CountBook object
     * @throws SQLException
     */
    @Override
    public lab5.CountBook resultSetToObj(ResultSet rs) throws SQLException {
        lab5.CountBook count_book = new CountBook();

        if (rs.next()) {
            count_book.setId(rs.getInt("id"));
            count_book.setCount(rs.getInt("count"));
            count_book.setBook(new BookDAO(connection).read(rs.getInt("book_id")));
        }
        return count_book;
    }

}
