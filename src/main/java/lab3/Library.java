package lab3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Library {
    public static final Integer MAX_NAME_LENGTH = 20;

    private Integer id;
    private String name;



    private List<CountBook> countBooks;
    private List<CountBook> countBook = new ArrayList<>();
    private Worker librarian;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Library() {
        countBooks = new ArrayList<>();
    }

    /**
     *
     * @param name Library name
     * @param countBook list of all medicines and their count
     * @param librarian person who works at this library
     */
    public Library(String name, List<CountBook> countBook, Worker librarian) {
        this.name = name;
        this.countBook = countBook;
        this.librarian = librarian;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() > MAX_NAME_LENGTH)
            throw new RuntimeException("Wrong input!");
        this.name = name;
    }

    public List<CountBook> getBooks() {
        return countBook;
    }

    public List<CountBook> getCountBooks() {
        return countBooks;
    }

    public void setBooks(List<CountBook> countBook) {
        this.countBook = countBook;
    }

    public Worker getWorker() {
        return librarian;
    }

    public void setWorker(Worker librarian) {
        this.librarian = librarian;
    }

    public void setCountBooks(List<CountBook> countBooks) {
        this.countBooks = countBooks;
    }


    public static class BuilderL {
        Library library;

        public BuilderL() {
            library = new Library();
        }

        public BuilderL setId(Integer id) {
            library.id=id;
            return this;
        }

        public BuilderL setName(String name) throws IllegalArgumentException {
            if (name.length() > MAX_NAME_LENGTH)
                throw new IllegalArgumentException("FirstName length must be less than " + MAX_NAME_LENGTH.toString());
            library.name = name;
            return this;
        }

        public BuilderL setCountBooks(List<CountBook> countBooks) {
            library.countBook = countBooks;
            return this;
        }

        public BuilderL setWorker(Worker librarian) {
            library.librarian = librarian;
            return this;
        }
    }
}