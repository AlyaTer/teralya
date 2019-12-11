package lab3.service;

import lab3.Book;
import lab3.CountBook;
import lab3.Library;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookService {
    private Library library;

    public BookService(Library library) {
        this.library= library;
    }

    /**
     * @return list of all book in the library
     */
    public List<Book> getBooks() {
        List<Book> result = new ArrayList<>();
        library.getBooks().forEach(e -> result.add(e.getBook()));
        return result;
    }

    /**
     * @return list of sorted by name
     */
    public List<CountBook> getSortedByCount() {
        List<CountBook> listBook = new ArrayList<>();
        listBook.stream().sorted(Comparator.comparing(CountBook::getCount,  Comparator.nullsLast(Comparator.reverseOrder())));

        listBook.addAll(library.getBooks());
        return listBook;
    }

    /**
     * Search book by name
     */
    public List<CountBook> findBooksByName(String name) {

        return library.getBooks().stream()
                .filter(a->a.getBook().getName().equals(name))
                .collect(Collectors.toList());
    }

}
