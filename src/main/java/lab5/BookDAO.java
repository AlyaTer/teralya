package lab5;

import lab5Test.Book;
import lab5Test.CountBook;

import javax.validation.constraints.NotNull;
import java.sql.*;
import java.time.LocalDate;

public class BookDAO implements DAO<lab5Test.Book, Integer> {

    /**
     * SQL queries for books table.
     */
    enum PersonSQL {
        GET("SELECT * FROM books  WHERE books.id = (?)"),
        INSERT("INSERT INTO books (id, name, form, overdue_day, price) VALUES ((?), (?), (?), (?), (?)) RETURNING id"),
        DELETE("DELETE FROM books WHERE id = (?) RETURNING id"),
        UPDATE("UPDATE books SET price = (?) WHERE form = (?) RETURNING id");

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
    public boolean create(lab5Test.Book book) {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(PersonSQL.INSERT.QUERY)) {
            statement.setInt(1, book.getId());
            statement.setString(2, book.getName());
            statement.setDate(4, Date.valueOf(book.getIssueDay()));
            statement.setDate(5, Date.valueOf(book.getReleaseDay()));
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
    public lab5.CountBook read(Integer id) {
        final lab5Test.Book result = new lab5Test.Book();
        result.setId(-1);

        try (PreparedStatement statement = connection.prepareStatement(PersonSQL.GET.QUERY)) {
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

    /**
     * Update Book`s price by id
     *
     * @param book new book state
     * @return true if success, false if fail
     */
    @Override
    public boolean update(lab5Test.Book book) {
        boolean result = false;

        try(PreparedStatement statement = connection.prepareStatement(PersonSQL.UPDATE.QUERY)) {
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Delete Person by id
     *
     * @param book for delete
     * @return true if book was deleted, false if book not exist
     */
    @Override
    public boolean delete(lab5Test.Book book) {
        boolean result = false;

        try(PreparedStatement statement = connection.prepareStatement(PersonSQL.DELETE.QUERY)) {
            statement.setInt(1, book.getId());
            result = statement.executeQuery().next();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(CountBook countBook) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(CountBook countBook) throws SQLException {
        return false;
    }

    /**
     * Convert ResultSet into Book
     *
     * @param rs ResultSet to convert
     * @return Book object
     * @throws SQLException
     */
    @Override
    public lab5Test.Book resultSetToObj(ResultSet rs) throws SQLException {
        lab5Test.Book book = new Book();

        if(rs.next()) {
            book.setId(rs.getInt("id"));
            book.setName(rs.getString("name"));
            book.setIssueDay(LocalDate.parse(rs.getDate("issue_day").toString()));
            book.setReleaseDay(LocalDate.parse(rs.getDate("release_day").toString()));
        }
        return book;
    }

}
