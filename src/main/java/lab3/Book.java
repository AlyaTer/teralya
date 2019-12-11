package lab3;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

//public class Book implements Comparable<Book> {
  //  @JsonDeserialize(builder = Book.Builder.class)

public class Book implements Serializable {


    public static final Integer MAXNAMELENGTH = 20;

    private Integer id;
    private String name;
    private LocalDate releaseDay;
    private LocalDate issueDay;

    private LocalDate returnDay;
    private Integer count;


    public Book() {
    }

    public Book(String name, LocalDate issueDay, LocalDate returnDay) {
        this.name = name;
        this.issueDay = issueDay;
        this.returnDay = returnDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() > MAXNAMELENGTH)
            throw new RuntimeException("Wrong input!");
        this.name = name;
    }

    public LocalDate getReleaseDay() {
        return releaseDay;
    }

    public void setReleaseDay(LocalDate releaseDay) {
        this.releaseDay = releaseDay;
    }

    public LocalDate getIssueDay() {
        return issueDay;
    }

    public void setIssueDay(LocalDate issueDay) {
        this.issueDay = issueDay;
    }

    public LocalDate getReturnDay() {
        return returnDay;
    }

    public void setReturnDay(LocalDate returnDay) {
        this.returnDay = returnDay;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }


    public static class BuilderB {
        Book book;

        public BuilderB() {
            book = new Book();
        }

        public BuilderB setName(String name) throws IllegalArgumentException {
            if (name.length() > MAXNAMELENGTH)
                throw new IllegalArgumentException("FirstName length must be less than " + MAXNAMELENGTH.toString());
            book.name = name;
            return this;
        }

        public BuilderB setId(Integer id) {
            book.id = id;
            return this;
        }

        public BuilderB setIssueDay(LocalDate date) {
            book.issueDay = date;
            return this;
        }
        public BuilderB setReleaseDay(LocalDate date) {
            book.releaseDay = date;
            return this;
        }

        public BuilderB setreleaseDay(LocalDate date) {
            if (date.isBefore(LocalDate.now()))
                throw new IllegalArgumentException("Wrong input!");
            book.issueDay = date;
            return this;
        }
    }
}

