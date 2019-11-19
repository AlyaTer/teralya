package lab0;

import java.util.Objects;

public class CountBook {
    private Book book;
    private Integer count;

    public CountBook() {
    }

    public CountBook(Book book, Integer count) {
        this.book = book;
        if (count < 0)
            throw new RuntimeException("Wrong input!");
        this.count = count;

    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        if (count < 0)
            throw new RuntimeException("Wrong input!");
        this.count = count;
    }
}

