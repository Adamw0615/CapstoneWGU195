package DAO;

import Helper.JDBC;
import Model.FirstLvlDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The First level division which uses queries to access the database.
 *
 * @author Adam Wright
 */

public class FirstLvlDivisionDAO {

    /**
     * This method retrieves all divison IDs from the first level divisions table.
     * and creates a list of FirstLVLDivision objects.
     *
     * @return divisionList - The observableList of FirstLVLDivision.
     * @throws RuntimeException if there's an SQL exception.
     */

    public static ObservableList<FirstLvlDivision> getAllDivisionId() {
        ObservableList<FirstLvlDivision> divisionList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT  * FROM first_level_divisions";
            PreparedStatement division = JDBC.conn.prepareStatement(sql);
            ResultSet rs = division.executeQuery();

            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                Timestamp Create_Date = rs.getTimestamp("Create_Date");
                LocalDateTime createDate = Create_Date.toLocalDateTime();
                String createBy = rs.getString("Created_By");
                Timestamp Last_Updated = rs.getTimestamp("Last_Update");
                LocalDateTime lastUpdated = Last_Updated.toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                FirstLvlDivision d = new FirstLvlDivision(divisionId, divisionName, countryId, createDate, createBy, lastUpdated, lastUpdatedBy);
                divisionList.add(d);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return divisionList;
    }

    /**
     * This method retrieves the divison name from the divison ID.
     *It uses a SQL query to fetch the data from the first_level_divisions table.
     * @param divisionId The ID of the divison to retrieve the name for.
     * @return The FirstLVLDivision object containing the division ID and name.
     * @throws  RuntimeException if there's an SQL exception while executing the query.
     */

    public static FirstLvlDivision returnDivisionLevel(int divisionId) {
        try {
            String sql = "SELECT Division_ID, Division FROM first_level_divisions WHERE Division_ID = ?";
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ps.setInt(1, divisionId);
            ps.execute();

            ResultSet rs = ps.getResultSet();

            rs.next();
            int searchedDivisionId = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            FirstLvlDivision s = new FirstLvlDivision(searchedDivisionId, divisionName);
            return s;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the division ID and division name from the first_level_division table.
     * fot the specified country ID.
     *
     * @param countryId The ID of the country.
     * @return An observable list of FirstLvlDivision objects containing the division ID and name.
     * @throws SQLException If there is an error executing the SQL query.
     */
    public static ObservableList<FirstLvlDivision> displayDivision(int countryId) throws SQLException {
        ObservableList<FirstLvlDivision> divisionCountryOptions = FXCollections.observableArrayList();

        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = " + countryId;
        PreparedStatement ps = JDBC.conn.prepareStatement(sql);
        ps.execute();
        ResultSet rs = ps.getResultSet();

        while (rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            countryId = rs.getInt("Country_ID");
            Timestamp Create_Date = rs.getTimestamp("Create_Date");
            LocalDateTime createDate = Create_Date.toLocalDateTime();
            String createBy = rs.getString("Created_By");
            Timestamp Last_Updated = rs.getTimestamp("Last_Update");
            LocalDateTime lastUpdated = Last_Updated.toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");

            FirstLvlDivision c = new FirstLvlDivision(divisionId, divisionName, countryId, createDate, createBy, lastUpdated, lastUpdatedBy);
            divisionCountryOptions.add(c);
        }
        return divisionCountryOptions;
    }
}


