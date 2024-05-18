package DAO;

import Helper.JDBC;
import Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The CountryDAO class which uses queries to obtain country information from
 * the database.
 *
 * @author Adam Wright
 */

public class CountryDAO {


    /**
     * Retrieves all countries from the database and them as an observable list.
     *
     * @return countryList - The list of all countries.
     */
    public static ObservableList<Country> getAllCountry() {
        //Create an observable list to store the countries.
        ObservableList<Country> countryList = FXCollections.observableArrayList();
        try {
            //SQL query to retrieve all countries.
            String sql = "SELECT Country_ID, Country FROM countries";
            //Prepare the statement and execute the query.
            PreparedStatement country = JDBC.conn.prepareStatement(sql);
            ResultSet rs = country.executeQuery();
            //Iterate over the result set and create Country Objects.
            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                //Create a new country and add it to the list.
                Country c = new Country(countryId, countryName);
                countryList.add(c);
            }
        } catch (SQLException e) {
            //If an exception occurs, throw a runtime exception.
            throw new RuntimeException(e);
        }//Return the list of countries.
        return countryList;
    }

    /**
     * Retrieves the country information from the database based on provided country ID.
     *
     * @param countryId The ID of the country to retrieve.
     * @return c - A Country object containing the country ID and name.
     * @throws RuntimeException if an SQL exception occurs. during the query.
     */

    public static Country returnCountry(int countryId) {
        try {
            String sql = "SELECT Country_ID, Country FROM countries WHERE Country_ID = ?";
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ps.setInt(1, countryId);
            ps.execute();

            ResultSet rs = ps.getResultSet();

            rs.next();
            int searchedCountryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Country c = new Country(searchedCountryId, countryName);
            return c;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Retrieves the total number of customers per country and returns them as an observable list
     *
     * @return customerCountry - An observable list containing the country name and total number of customers.
     */
    public static ObservableList<Country> countryTotals() {
        ObservableList<Country> customerCountry = FXCollections.observableArrayList();
        try {
            String sql = "SELECT countries.Country, COUNT(customers.Customer_ID) AS Count FROM countries INNER JOIN first_level_divisions ON  countries.Country_ID = first_level_divisions.Country_ID INNER JOIN customers ON customers.Division_ID = first_level_divisions.Division_ID group by countries.Country";
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String countryMonth = rs.getString("Country");
                int countryMonthTotal = rs.getInt("Count");
                Country results = new Country(countryMonth, countryMonthTotal);
                customerCountry.add(results);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerCountry;
    }
}
