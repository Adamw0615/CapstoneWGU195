package DAO;

import Helper.JDBC;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



/**
 * The UserDAO class is used to perform database operations related to the User table.
 *
 * @author Adam Wright
 */

public class UserDAO {


    /**
     * The SQL query to get all users and add to an observable list.
     *
     * @return userList containing all users.
     */
    public static ObservableList<User> getUserList() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM users";
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");

                User u = new User(userId, userName);
                userList.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    /**
     * Selecting and preparing the username and password from the database.
     * Checks if the provided username and password match a record in the Users table.
     *
     * @param User_name The user's username
     * @param Password The user's password.
     * @return if the username and password match a record, otherwise false.
     */

    public static boolean userLogin(String User_name, String Password) {
        try (PreparedStatement ps = JDBC.database().prepareStatement("SELECT * FROM Users WHERE  User_name = ? AND  Password = ?")) {
            ps.setString(1, User_name);
            ps.setString(2, Password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Validates the username by checking if it exist in the Users table.
     *
     * @param @Username The username to be validated.
     * @return if the username exist, false otherwise.
     */

    public static boolean usernameValidation(String User_Name) {
        try (PreparedStatement ps = JDBC.database().prepareStatement("SELECT * FROM Users WHERE BINARY User_name = ?")) {
            ps.setString(1, User_Name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Validates the password by checking if it exists in the Users table.
     *The BINARY keyword ensures case-sensitive comparison.
     *
     * @param Password The password to be validated.
     *
     * @return true if the password exist, false otherwise.
     * @throws SQLException if there is an error executing the SQL statement.
     */

    public static boolean passwordValidation(String Password)  {
        try (PreparedStatement ps = JDBC.database().prepareStatement("SELECT * FROM Users WHERE BINARY Password = ?")) {
            ps.setString(1, Password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves the user ID associated with the given username.
     *
     * @param userName The username to retrieve the user ID for.
     * @return The user ID, or 0 if the username does not exist.
     *
     * @throws SQLException if there is an error executing the SQL statement.
     */

    public static int getUserId(String userName) throws SQLException {
        int userId = 0;
        String sqlStatement = "select User_ID, User_Name from users where User_Name = '" + userName + "'";
        PreparedStatement ps = JDBC.conn.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            userId = rs.getInt("User_ID");
            userName = rs.getString("User_Name");
        }
        return userId;

    }

    /**
     * Retrieves the user based on the provided user ID
     *
     * @param userId  The user ID to retrieve the user for.
     * @return The user object containing the user details.
     */

    public static User returnUserId(int userId) {
        try {
            String sql = "SELECT User_ID, User_Name FROM users WHERE User_ID = ?";
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.execute();

            ResultSet rs = ps.getResultSet();

            rs.next();
            int searchedUserId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            User u = new User(searchedUserId, userName);
            return u;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
