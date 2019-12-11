package lab5;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibraryDAO implements DAO<lab5.Library, Integer> {

    /**
     * SQL queries for library table.
     */
    enum LibrarySQL {
        GET("SELECT * FROM library  WHERE library.id = (?)"),
        INSERT("INSERT INTO library (id, name, worker_id) VALUES ((?), (?), (?)) RETURNING id"),
        DELETE("DELETE FROM library WHERE id = (?) RETURNING id"),
        UPDATE("UPDATE library SET name = (?) WHERE id = (?) RETURNING id"),

        GET_COUNT_BOOK_LIST("SELECT * FROM count_books WHERE library_id=(?)"),
        GET_RELEASE_BOOKS("SELECT books.* " +
                "FROM count_books JOIN books ON count_books .book_id=books .id "+
                "JOIN library ON count_books .library_id=library.id "+
                "WHERE library.id=(?) AND books .release_day<current_date");

        String QUERY;

        LibrarySQL(String QUERY) {
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
    public LibraryDAO(final Connection connection) {
        this.connection= connection;
    }

    /**
     * Create Library in DB
     *
     * @param library for create
     * @return false if item already exist, true if creating success
     * @throws SQLException
     */
    @Override
    public boolean create(lab5.Library library) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(LibrarySQL.INSERT.QUERY);
        statement.setInt(1, library.getId());
        statement.setString(2, library.getName());
        statement.setInt(3, library.getWorker().getId());
        return statement.executeQuery().next();
    }

    /**
     * Select Library by id
     *
     * @param id for select
     * @return return valid entity if she exist. If entity does not exist return empty object with id = -1.
     * @throws SQLException
     */
    @Override
    public CountBook read(Integer id) throws SQLException {
        lab5.Library result = new lab5.Library();
        result.setId(-1);
        PreparedStatement statement = connection.prepareStatement(LibrarySQL.GET.QUERY);
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()) {
            result.setId(resultSet.getInt("id"));
            result.setWorker(new WorkerDAO(connection).read(resultSet.getInt("librarian_id")));
            result.setCountBooks(getListCountBook(result));
            result.setName(resultSet.getString("name"));
        }
        return result;
    }

    /**
     * Update Library by id
     *
     * @param library new state
     * @return true if success, false if fail
     * @throws SQLException
     */
    @Override
    public boolean update(lab5.Library library) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(LibrarySQL.UPDATE.QUERY);
        statement.setString(1, library.getName());
        statement.setInt(2, library.getId());

        return statement.executeQuery().next();
    }

    /**
     * Delete Library dy id
     *
     * @param library for delete
     * @return true if success, false if item not exist
     * @throws SQLException
     */
    @Override
    public boolean delete(lab5.Library library) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(LibrarySQL.DELETE.QUERY);
        statement.setInt(1, library.getId());

        return statement.executeQuery().next();
    }

    @Override
    public boolean update(lab5Test.CountBook countBook) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(lab5Test.CountBook countBook) throws SQLException {
        return false;
    }

    /**
     * Get list of Book and their count into Library
     *
     * @param library Library
     * @return list of CountBook
     * @throws SQLException
     */
    public List<lab5.CountBook> getListCountBook(lab5.Library library) throws SQLException {
        List<lab5.CountBook> list = new ArrayList<>();

        try( PreparedStatement statement = connection.prepareStatement(LibrarySQL.GET_COUNT_BOOK_LIST.QUERY)) {
            statement.setInt(1, library.getId());
            ResultSet resultSet = statement.executeQuery();

            do {
              //  CountBook countBook = new CountBookDAO(connection).resultSetToObj(resultSet);
                lab5.CountBook countBook = new lab5.CountBookDAO(connection).resultSetToObj(resultSet);
                list.add(countBook);
            } while (!resultSet.isLast());
        }catch (Exception e) {
            e.getStackTrace();
        }
        list.forEach(System.out::println);
        return list;
    }

    /**
     * Get List of released books in library
     *
     * @param library Library
     * @return list of released Medicines
     * @throws SQLException
     */
    private List<lab5.Book> getListOfOverdueMedicines(lab5.Library library) throws SQLException {
        List<lab5.Book> list = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(LibrarySQL.GET_RELEASE_BOOKS.QUERY)) {
            statement.setInt(1, library.getId());
            ResultSet resultSet = statement.executeQuery();

            do {
                Book book = new BookDAO(connection).resultSetToObj(resultSet);
                list.add(book);
            } while (!resultSet.isLast());

            return list;
        }
    }

    /**
     * Convert ResultSer into Library Object
     *
     * @param rs ResultSet to convert
     * @return Library object
     * @throws SQLException
     */
    @Override
    public lab5.Library resultSetToObj(ResultSet rs) throws SQLException {
        lab5.Library library = new Library();

        if(rs.next()) {
            library.setId(rs.getInt("id"));
            library.setName(rs.getString("name"));
            library.setCountBooks(getListCountBook(library));
            library.setWorker(new WorkerDAO(connection).read(rs.getInt("librarian_id")));
        }

        return library;
    }

}