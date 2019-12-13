package lab5_6;


import javax.validation.constraints.NotNull;
import java.sql.*;
import java.time.LocalDate;

import static org.testng.AssertJUnit.assertEquals;

public class BookDAO implements DAO<Book, Integer> {

    /**
     * SQL queries for books table.
     */
    enum BookSQL {
        GET("SELECT * FROM books  WHERE books.id = (?)"),
        INSERT("INSERT INTO books (id, name, form, overdue_day, price) VALUES ((?), (?), (?), (?), (?)) RETURNING id"),
        DELETE("DELETE FROM books WHERE id = (?) RETURNING id"),
        UPDATE("UPDATE books SET price = (?) WHERE form = (?) RETURNING id"),

        DELETE_OVERDUE_MEDICINES("DELETE FROM books WHERE overdue_day<current_date");

        String QUERY;

        BookSQL(String QUERY) {
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
    public BookDAO(@NotNull final Connection connection) {
        this.connection = connection;
    }

    /**
     * Create Book in DB
     *
     * @param book for create
     * @return false if Book already exist, true if creating success
     */
    @Override
    public boolean create(Book book) {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(BookSQL.INSERT.QUERY)) {
            statement.setInt(1, book.getId());
            statement.setString(2, book.getName());
            statement.setDate(3, Date.valueOf(book.getIssueDay()));
            statement.setDate(4, Date.valueOf(book.getReleaseDay()));
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Select User by login.
     *
     * @param  id for select.
     * @return return valid entity if she exist. If entity does not exist return empty User with id = -1.
     */
    @Override
    public Book read(Integer id) {
        final Book result = new Book();
        result.setId(-1);

        try (PreparedStatement statement = connection.prepareStatement(BookSQL.GET.QUERY)) {
            statement.setInt(1, id);
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result.setId(Integer.parseInt(resultSet.getString("id")));
                result.setIssueDay(LocalDate.parse(resultSet.getDate("issue_day").toString()));
                result.setReleaseDay(LocalDate.parse(resultSet.getDate("release_day").toString()));
                result.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(Book model) throws SQLException {
        return false;
    }

    /**
     * Delete Person by id
     *
     * @param book for delete
     * @return true if book was deleted, false if book not exist
     */
    @Override
    public boolean delete(Book book) {
        boolean result = false;

        try(PreparedStatement statement = connection.prepareStatement(BookSQL.DELETE.QUERY)) {
            statement.setInt(1, book.getId());
            result = statement.executeQuery().next();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public void deleteWhereOverdue() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(BookSQL.DELETE_OVERDUE_MEDICINES.QUERY);
        statement.execute();
        statement.close();
    }

    /**
     * Convert ResultSet into Book
     *
     * @param rs ResultSet to convert
     * @return Book object
     * @throws SQLException
     */
    public Book resultSetToObj(ResultSet rs) throws SQLException {
        Book book = new Book();

        book.setId(rs.getInt("id"));
        book.setName(rs.getString("name"));
        book.setIssueDay(LocalDate.parse(rs.getDate("issue_day").toString()));
        book.setReleaseDay(LocalDate.parse(rs.getDate("release_day").toString()));
        return book;
    }

}
