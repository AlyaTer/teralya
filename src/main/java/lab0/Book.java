package lab0;


import java.time.LocalDate;
import java.util.Objects;

public class Book implements Comparable<Book> {
    public static final Integer MAX_NAME_LENGTH = 20;
    private String name;
    private LocalDate releaseDay;
    private LocalDate issueDay;
    private LocalDate returnDay;
    private Integer count;

    public Book() {
    }

    public Book(String name, String form, Double price, LocalDate overdueDay) {
        this.name = name;
        this.issueDay = issueDay;
        this.returnDay = returnDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() > MAX_NAME_LENGTH)
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

    /*   public Double getPrice() {
                    return price;
                }

                public void setPrice(Double price) {
                    if (price <= 0)
                        throw new RuntimeException("Wrong input!");
                    this.price = price;
                }
            */


    @Override
    public int compareTo(Book o) {
        if(this.releaseDay.isAfter(o.releaseDay))
            return 1;
        else return -1;
    }
}
