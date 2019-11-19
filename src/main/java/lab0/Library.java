package lab0;

import java.util.*;

public class Library {
    public static final Integer MAXNAMELENGTH = 20;
    private String name;
    private List<CountBook> countBook = new ArrayList<>();
    private Worker librarian;

    public Library() {

    }

    /**
     *
     * @param name Library name
     * @param countBook list of all medicines and their count
     * @param librarian person who works at this pharmacy
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
        if (name.length() > MAXNAMELENGTH)
            throw new RuntimeException("Wrong input!");
        this.name = name;
    }

    public List<CountBook> getCountMedicines() {
        return countBook;
    }
/*
    public void setCountMedicines(List<CountMedicine> countMedicines) {
        this.countMedicines = countMedicines;
    }
*/
    public Worker getPharmacist() {
        return librarian;
    }

    public void setPharmacist(Worker pharmacist) {
        this.librarian = librarian;
    }


}