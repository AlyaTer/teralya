package lab5_6;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LibraryDAO implements DAO<Library, Integer> {

    /**
     * SQL queries for library table.
     */
    enum LibrarySQL {
        GET("SELECT * FROM library  WHERE library.id = (?)"),
        INSERT("INSERT INTO library (id, name, worker_id) VALUES ((?), (?), (?)) RETURNING id"),
        DELETE("DELETE FROM library WHERE id = (?) RETURNING id"),
        UPDATE("UPDATE library SET name = (?) WHERE id = (?) RETURNING id"),

        GET_COUNT_BOOK_LIST("SELECT * FROM count_books WHERE library_id=(?)"),
        GET_OVERDUE_BOOKS("SELECT books.* " +
                "FROM count_books JOIN books ON count_books.book_id=books.id "+
                "JOIN library ON count_books.library_id=library.id "+
                "WHERE library.id=(?) AND books.issue_day<current_date"),
        DELETE_ISSUE_BOOKS_IN_LIBRARY("Delete From books "+
                "WHERE books IN(SELECT books " +
                "FROM count_books JOIN books ON count_books.medicine_id=books.id "+
                "JOIN library ON count_books.library_id=library.id "+
                "WHERE library.id=(?) AND books.overdue_day<current_date)"),
        GET_ALL("SELECT * FROM library");

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
    public boolean create(Library library) throws SQLException {
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
    public Library read(Integer id) throws SQLException {
        Library result = new Library();
        result.setId(-1);
        PreparedStatement statement = connection.prepareStatement(LibrarySQL.GET.QUERY);
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()) {
            result.setId(resultSet.getInt("id"));
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
    public boolean update(Library library) throws SQLException {
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
    public boolean delete(Library library) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(LibrarySQL.DELETE.QUERY);
        statement.setInt(1, library.getId());

        return statement.executeQuery().next();
    }


    /**
     * Get list of Book and their count into Library
     *
     * @param library Library
     * @return list of CountBook
     * @throws SQLException
     */
    public List<CountBook> getListCountBook(Library library) throws SQLException {
        List<CountBook> list = new ArrayList<>();

        try( PreparedStatement statement = connection.prepareStatement(LibrarySQL.GET_COUNT_BOOK_LIST.QUERY)) {
            statement.setInt(1, library.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                CountBook countBook = new CountBookDAO(connection).resultSetToObj(resultSet);
                list.add(countBook);
            }
        }catch (Exception e) {
            e.getStackTrace();
        }
        //list.forEach(System.out::println);
        return list;
    }

    /**
     * Get List of overdue books in library
     *
     * @param library Library
     * @return list of overdue Books
     * @throws SQLException
     */
    private List<Book> getListOfOverdueBooks(Library library) throws SQLException {
        List<Book> list = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(LibrarySQL.GET_OVERDUE_BOOKS.QUERY)) {
            statement.setInt(1, library.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Book medicine = new BookDAO(connection).resultSetToObj(resultSet);
                list.add(medicine);
            }

            return list;
        }
    }

    /**
     * Delete all overdue Books in Library
     *
     * @param library Library
     * @throws SQLException
     */
    public void deleteOverdueBooks(Library library) throws SQLException {
        List<Book> medicineList = getListOfOverdueBooks(library);

        for (Book medicine: medicineList) {
            new BookDAO(connection).delete(medicine);
        }
    }

    public void deleteById(Integer id) {
        PreparedStatement statement = null;
        try {
            statement = Objects.requireNonNull(connection).prepareStatement(LibrarySQL.DELETE.QUERY);
            statement.setLong(1,id);
            statement.execute();
        }catch (SQLException e){
            e.getStackTrace();
        }
    }

    /**
     * Delete all overdue Books in Library by SQL
     *
     * @param library Library
     * @throws SQLException
     */
    public boolean deleteOverdueBooksInLibrary(Library library) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(LibrarySQL.DELETE_ISSUE_BOOKS_IN_LIBRARY.QUERY)) {
            statement.setInt(1,library.getId());
            return statement.execute();
        }
    }


    /**
     * Convert ResultSer into Library Object
     *
     * @param rs ResultSet to convert
     * @return Library object
     * @throws SQLException
     */
    public Library resultSetToObj(ResultSet rs) throws SQLException {
        Library library = new Library();

        library.setId(rs.getInt("id"));
        library.setName(rs.getString("name"));
        library.setCountBooks(getListCountBook(library));
//
        return library;
    }

    public List<Library> findAll() throws SQLException {
        List<Library> list = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(LibrarySQL.GET_ALL.QUERY)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                Library p = resultSetToObj(resultSet);
                list.add(p);
            }
            return list;
        }
    }

}