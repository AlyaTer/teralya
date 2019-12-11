package lab5;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DataBaseStructure {

    private static final String CREATE_PERSONS = "CREATE TABLE persons (id SERIAL NOT NULL PRIMARY KEY,first_name VARCHAR(50),last_name VARCHAR(50),birthday DATE,salary DOUBLE PRECISION)";
    private static final String DROP_PERSONS = "DROP TABLE persons";

    private static final String CREATE_MEDICINES = "CREATE TABLE medicines (id SERIAL NOT NULL PRIMARY KEY,name VARCHAR(20),form VARCHAR(20),overdue_day DATE,price DOUBLE PRECISION)";
    private static final String DROP_MEDICINES = "DROP TABLE medicines";

    private static final String CREATE_COUNT_MEDICINE = "CREATE TABLE count_medicines (id SERIAL NOT NULL PRIMARY KEY,medicine_id INTEGER NOT NULL REFERENCES medicines(id) ON DELETE CASCADE,count INTEGER,pharmacy_id INTEGER NOT NULL REFERENCES medicines(id) ON DELETE CASCADE)";
    private static final String DROP_COUNT_MEDICINE = "DROP TABLE count_medicines";

    private static final String CREATE_PHARMACY = "CREATE TABLE pharmacy (id SERIAL NOT NULL PRIMARY KEY,name VARCHAR(20),pharmacist_id INTEGER NOT NULL REFERENCES persons(id))";
    private static final String DROP_PHARMACY = "DROP TABLE pharmacy";

    /**
     * Private method to crete table by string
     *
     * @param sqlString to create
     */
    private static void createTable(String sqlString) throws Exception {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlString)) {
            statement.executeUpdate();
        }
    }

    /**
     * Private method to delete table by string
     *
     * @param sqlString to delete
     */
    private static void dropTable(String sqlString) throws Exception {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlString)) {
            statement.execute();
        }
    }

    /**
     * Creating table persons in Pharmacy DB
     *
     * @throws Exception if smth going wrong
     */
    public static void createPersonsTable() throws Exception {
        createTable(CREATE_PERSONS);
    }

    /**
     * Creating medicines table
     *
     * @throws Exception
     */
    public static void createMedicinesTable() throws Exception {
        createTable(CREATE_MEDICINES);
    }

    /**
     * Creating count_medicine table
     *
     * @throws Exception
     */
    public static void createCountMedicineTable() throws Exception {
        createTable(CREATE_COUNT_MEDICINE);
    }

    /**
     * Creating pharmacy table
     *
     * @throws Exception
     */
    public static void createPharmacyTable() throws Exception {
        createTable(CREATE_PHARMACY);
    }

    /**
     * Dropping table persons in Pharmacy DB
     *
     * @throws Exception if smth going wrong
     */
    public static void dropPersonsTable() throws Exception {
        dropTable(DROP_PERSONS);
    }

    /**
     * Dropping medicines table
     *
     * @throws Exception
     */
    public static void dropMedicinesTable() throws Exception {
        dropTable(DROP_MEDICINES);
    }

    /**
     * Dropping count_medicine table
     *
     * @throws Exception
     */
    public static void dropCountMedicineTable() throws Exception {
        dropTable(DROP_COUNT_MEDICINE);
    }

    /**
     * Drop pharmacy table
     *
     * @throws Exception
     */
    public static void dropPharmacyTable() throws Exception {
        dropTable(DROP_PHARMACY);
    }
}
