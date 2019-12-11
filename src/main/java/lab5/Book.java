package lab5;


import lab2.parser.writer.adapter.LocaleDateAdapterXml;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

//public class Book implements Comparable<Book> {
  //  @JsonDeserialize(builder = Book.Builder.class)

public class Book implements Serializable {


    public static final Integer MAXNAMELENGTH = 20;

    @NotNull(message = "The field can`t be null")
    @PositiveOrZero
    private Integer id;

    @XmlJavaTypeAdapter(LocaleDateAdapterXml.class)

    @XmlElement
    @NotNull(message = "Name can't be null!")
    @Size(min = 3, max = 10, message = "{Size.name}")
    private String name;

    @XmlElement
    @NotNull(message = "The field can`t be null")
    @PositiveOrZero
    private LocalDate releaseDay;

    @NotNull(message = "The field can`t be null")
    @PositiveOrZero
    private LocalDate issueDay;

    @NotNull(message = "The field can`t be null")
    @PositiveOrZero
    private LocalDate returnDay;

    @NotNull(message = "The field can`t be null")
    @PositiveOrZero
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

    @NotNull
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

        public Book build() throws IllegalStateException {

            try {
                Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
                //Book book = new Book();
                Set<ConstraintViolation<Book>> constraintViolations = validator.validate(book);

                if (constraintViolations.isEmpty())
                    return book;
                else {
                    Set<String> exceptions = new HashSet<>();
                    for (ConstraintViolation constraintViolation : constraintViolations) {
                        String fieldName = constraintViolation.getPropertyPath().toString().toUpperCase();
                        exceptions.add(fieldName + " " + constraintViolation.getMessage());
                    }
                    exceptions.forEach(System.out::println);
                    throw new IllegalStateException(exceptions.toString() + " ");
                }
            } catch (IllegalStateException e) {
                throw new IllegalStateException(e.getMessage());

            }
        }
    }
}

