package DAO;

import Helper.JDBC;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *The customer DAO class which uses queries to access information in database.
 *
 * @author Adam Wright
 */

public class CustomerDAO {


    /**
     * The SQL query that returns a list of all the customer information in an observable list.
     *
     * @return customerList - The observableList of Customer.
     */
    public static ObservableList<Customer> getCustomerList() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        try {
            //SQL Query to retrieve customer information.
            String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Create_Date, customers.Last_Update, customers.Created_By, customers.Last_Updated_By, customers.Postal_Code, customers.Phone, customers.Division_ID, first_level_divisions.Division, first_level_divisions.Country_ID, countries.Country FROM customers JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID JOIN countries ON countries.Country_ID = first_level_divisions.Country_ID ORDER BY customers.Customer_ID ";
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                String createdBy = rs.getString("Created_By");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerDivisionId = rs.getInt("Division_ID");
                String customerDivisionName = rs.getString("Division");
                int customerCountryId = rs.getInt("Country_ID");
                String customerCountryName = rs.getString("Country");
                Customer c = new Customer(customerName, customerAddress, customerPostalCode, customerPhone, createdBy, lastUpdatedBy, customerDivisionId, customerDivisionName, customerCountryId, customerCountryName, customerId);
                customerList.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }


    /**
     * Deletes a customer from the database based on the provided customer ID.
     *
     * @param customerId The ID of the customer to delete.
     */

    public static void deleteCustomer(int customerId) {
        try {
            //SQL query to delete a customer from the customers table.
            String sqldelete = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement deleteCust = JDBC.conn.prepareStatement(sqldelete);
            deleteCust.setInt(1, customerId);
            deleteCust.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a customer in the database with the provided information.
     *
     * @param customerId         The ID of the customer to update.
     * @param customerName       The new name of the customer.
     * @param customerAddress    The new address of the customer.
     * @param customerPostalCode The new postal code of the customer.
     * @param customerPhone      The new phone number of the customer.
     * @param lastUpdatedBy      The user who last updated the customer.
     * @param lastUpdated        The date and time of the last update.
     * @param customerDivisionId The ID of the customer's division.
     * @param countryId          The ID of the customers country.
     */
    public static void updateCustomer(int customerId, String customerName, String customerAddress, String customerPostalCode,
                                      String customerPhone, String lastUpdatedBy, Timestamp lastUpdated, int customerDivisionId, int countryId) {
        try {
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Updated_By = ?, Last_Update = ?, Division_ID = ? WHERE Customer_ID = ?";
            PreparedStatement updateCust = JDBC.conn.prepareStatement(sql);
            updateCust.setString(1, customerName);
            updateCust.setString(2, customerAddress);
            updateCust.setString(3, customerPostalCode);
            updateCust.setString(4, customerPhone);
            updateCust.setString(5, lastUpdatedBy);
            updateCust.setTimestamp(6, lastUpdated);
            updateCust.setInt(7, customerDivisionId);
            updateCust.setInt(8, customerId);
            updateCust.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * SQL Query to add new customer. Customer ID is auto-incremented by database.
     *
     * @param customerName      The name of the customer
     * @param customerAddress    The address of the customer
     * @param customerPostalCode The postal code of the customer
     * @param customerPhone      The phone number of the customer.
     * @param createdDate        The creation date of the customer.
     * @param lastUpdated         The last updated date and time of the customer.
     * @param divisionId         The ID of the customer's division.
     * @throws SQLException If there is an unhandled SQL exception.
     */

    public static void addCustomer(String customerName, String customerAddress, String customerPostalCode, String customerPhone, LocalDateTime createdDate, LocalDateTime lastUpdated, int divisionId) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Last_Update, Division_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement insertCust = JDBC.conn.prepareStatement(sql);
        insertCust.setString(1, customerName);
        insertCust.setString(2, customerAddress);
        insertCust.setString(3, customerPostalCode);
        insertCust.setString(4, customerPhone);
        insertCust.setTimestamp(5, Timestamp.valueOf(createdDate));
        insertCust.setTimestamp(6, Timestamp.valueOf(lastUpdated));
        insertCust.setInt(7, divisionId);
        insertCust.executeUpdate();
    }

    /**
     * Returns a customer object with the given customer ID and name from the database.
     *
     * @param customerId The ID of the customer to search for
     * @return The customer object with the given ID and name, or null if not found.
     * @throws SQLException If there is an unhandled SQL exception.
     */

    public static Customer returnCustomerList(int customerId) throws SQLException {
        String sql = "SELECT * FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.conn.prepareStatement(sql);
        ps.setInt(1, customerId);
        ps.execute();
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int searchedCustomerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");

            Customer c = new Customer(searchedCustomerId, customerName);
            return c;
        }
        return null;
    }

}
