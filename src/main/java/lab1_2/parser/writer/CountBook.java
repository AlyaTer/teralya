package lab1_2.parser.writer;

public class CountBook {


    private Integer id;

    private Book book;
    private Integer count;
    private Library library;

    public CountBook() {
    }

    public CountBook(Book book, Integer count) {
        this.book = book;
        if (count < 0)
            throw new RuntimeException("Wrong input!");
        this.count = count;

    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static class BuilderCB {
        CountBook countBk;

        public BuilderCB() {
            countBk = new CountBook();
        }

        public BuilderCB setId(Integer id) {
            countBk.id = id;
            return this;
        }

        public BuilderCB setBook(Book med) {
            countBk.book = med;
            return this;
        }
        public BuilderCB setLibrary(Library library) {
            countBk.library = library;
            return this;
        }

        public BuilderCB setCount(Integer count) {
            countBk.count=count;
            return  this;
        }

        public CountBook build() {
            return countBk;
        }
    }


}

