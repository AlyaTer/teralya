package lab5;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

public class Worker extends Book {
    public static final Integer MAXFIRSTNAMELENGTH = 20;
    public static final Double MINSALARY = 1000.00;

    @NotNull(message = " field can`t be null")
    private Integer id;

    @Size(min = 1, max=20)
    private String firstName;

    @Size(min = 1, max=20)
    private String lastName;

    private LocalDate birthday;
    private Double salary;

    // private to disallow creating Worker from another classes
    // can be created using Builder
    Worker() {
    }

    public void setFirstName(String firstName) {
        if (firstName.length() > MAXFIRSTNAMELENGTH)
            throw new RuntimeException("Wrong input!");
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Double getSalary() {
        return salary;
    }

    public void setLastName(String lastName) {
        if (lastName.length() > MAXFIRSTNAMELENGTH)
            throw new RuntimeException("Wrong input!");
        this.lastName = lastName;
    }

    public void setSalary(Double salary) {
        if (salary < MINSALARY)
            throw new RuntimeException("Wrong input!");
        this.salary = salary;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker worker = (Worker) o;
        return Objects.equals(firstName, worker.firstName) &&
                Objects.equals(lastName, worker.lastName) &&
                Objects.equals(birthday, worker.birthday) &&
                Objects.equals(salary, worker.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, birthday);
    }

    @Override
    public String toString() {
        return "Worker{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", salary=" + salary +
                '}';
    }

    public static class Builder {
        Worker worker;

        public Builder() {
            worker = new Worker();
        }

        /**
         * Sets firstName for person
         * @param firstName String
         * @return instance of this builder
         * @throws IllegalArgumentException if length of firstName > MAXFIRSTNAMELENGTH
         */
        public Builder setFirstName(String firstName) throws IllegalArgumentException {
            if (firstName.length() > MAXFIRSTNAMELENGTH)
                throw new IllegalArgumentException("FirstName length must be less than " + MAXFIRSTNAMELENGTH.toString());
            worker.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            worker.lastName = lastName;
            return this;
        }

        public Builder setBirthDay(LocalDate birthDay) {
            worker.birthday = birthDay;
            return this;
        }

        public Builder setId(Integer id) {
            worker.id=id;
            return this;
        }

        /**
         * Sets salary for person
         * @param salary Double must be less than MAXSALARY
         * @return instance of this builder
         */
        public Builder setSalary(Double salary) {
            if (salary < MINSALARY)
                worker.salary = MINSALARY;
            else
                worker.salary = salary;
            return this;
        }

        /**
         * Call it after setting all parameters
         * @return instance of class Worker
         */
        public Worker build() {
            return worker;
        }
    }
}