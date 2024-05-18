package DAO;

import Helper.JDBC;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The SQL queries responsible for obtaining the information from the database.
 *
 * @author Adam Wright
 */

public class AppointmentDAO {

    /**
     * Retrieves an observable list of all appointments from the database.
     *
     * @return appointmentList The list of appointments.
     */

    public static ObservableList<Appointment> getAppointmentList() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        try {
            //SQL Query to retrieve all appointments from the database.
            String sql = "SELECT * FROM appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID ORDER BY appointments.Appointment_ID";
            //Prepare SQL statement
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            //Execute SQL statement and get the result set.
            ResultSet rs = ps.executeQuery();
            //Iterate over the result set and create Appointment objects.
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                int appointmentContact = rs.getInt("Contact_ID");
                String appointmentContactName = rs.getString("Contact_Name");
                String appointmentType = rs.getString("Type");
                LocalDateTime appointmentStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appointmentEnd = rs.getTimestamp("End").toLocalDateTime();
                int appointmentCustomerId = rs.getInt("Customer_ID");
                int appointmentUserId = rs.getInt("User_ID");
                String appointmentLocation = rs.getString("Location");

                //Create an Appointment object and add it to the list.
                Appointment c = new Appointment(appointmentId, appointmentTitle, appointmentDescription, appointmentContact, appointmentContactName,
                        appointmentType, appointmentStart, appointmentEnd, appointmentCustomerId, appointmentUserId, appointmentLocation);
                appointmentList.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appointmentList;
    }

    /**
     * Updates an appointment in the database with the provided details.
     *
     * @param appointmentId      The ID of the appointment to update.
     * @param appointmentTitle       The new title of the appointment.
     * @param appointmentDescription The new description of the appointment.
     * @param appointmentContact     The new associated contact ID of the appointment.
     * @param appointmentType        The new type of the appointment.
     * @param appointmentStart       The new start date/time of the appointment.
     * @param appointmentEnd         The new end date/time of the appointment.
     * @param appointmentCustomerId  The new associated customer ID of the appointment.
     * @param appointmentUserId      The new associated user ID of the appointment.
     * @param appointmentLocation   The new location of the appointment.
     */
    public static void updateAppointment(int appointmentId, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int appointmentCustomerId, int appointmentUserId, int appointmentContact) {
        try {

            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement updateAppointment = JDBC.conn.prepareStatement(sql);


            updateAppointment.setString(1, appointmentTitle);
            updateAppointment.setString(2, appointmentDescription);
            updateAppointment.setString(3, appointmentLocation);
            updateAppointment.setString(4, appointmentType);
            updateAppointment.setTimestamp(5, Timestamp.valueOf(appointmentStart));
            updateAppointment.setTimestamp(6, Timestamp.valueOf(appointmentEnd));
            updateAppointment.setInt(7, appointmentCustomerId);
            updateAppointment.setInt(8, appointmentUserId);
            updateAppointment.setInt(9, appointmentContact);
            updateAppointment.setInt(10, appointmentId);
            //Execute the SQL statement
            updateAppointment.execute();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a new appointment to the database. The appointment ID is auto-incremented by the database.
     *
     * @param appointmentTitle       The title of the appointment.
     * @param appointmentDescription The description of the appointment.
     * @param appointmentLocation    The location of the appointment.
     * @param appointmentType        The type of the appointment.
     * @param appointmentStart       The start date/time of the appointment.
     * @param appointmentEnd         The end date/time of the appointment.
     * @param appointmentCustomerId  The associated customer ID of the appointment.
     * @param appointmentUserId      The associated user ID of the appointment.
     * @param appointmentContact     The associated contact ID of the appointment.
     * @throws SQLException aif there is an error executing the SQL statement.
     */
    public static void addAppointment(String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int appointmentCustomerId, int appointmentUserId, int appointmentContact) throws SQLException {
        //SQL Query to insert a new appointment into the database.
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        //Prepare the SQL statement
        PreparedStatement insertAppoint = JDBC.conn.prepareStatement(sql);

        //Set the parameters of the SQL statement
        insertAppoint.setString(1, appointmentTitle);
        insertAppoint.setString(2, appointmentDescription);
        insertAppoint.setString(3, appointmentLocation);
        insertAppoint.setString(4, appointmentType);
        insertAppoint.setTimestamp(5, Timestamp.valueOf(appointmentStart));
        insertAppoint.setTimestamp(6, Timestamp.valueOf(appointmentEnd));
        insertAppoint.setInt(7, appointmentCustomerId);
        insertAppoint.setInt(8, appointmentUserId);
        insertAppoint.setInt(9, appointmentContact);
        insertAppoint.executeUpdate();
    }

    /**
     * Retrieves a list of weekly appointments for the next 7 days from the database.
     *
     * @return observsableList of Appointment objects representing  the weekly appointments.
     */

    public static ObservableList<Appointment> getWeeklyAppointments() {
        ObservableList<Appointment> weeklyList = FXCollections.observableArrayList();
        try {
            //SQL query to retrieve weekly appointments from the database.
            String sql = "SELECT * FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE YEARWEEK(START) = YEARWEEK(NOW()) ORDER BY appointments.Appointment_ID";
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                int appointmentContact = rs.getInt("Contact_ID");
                String appointmentContactName = rs.getString("Contact_Name");
                String appointmentType = rs.getString("Type");
                LocalDateTime appointmentStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appointmentEnd = rs.getTimestamp("End").toLocalDateTime();
                int appointmentCustomerId = rs.getInt("Customer_ID");
                int appointmentUserId = rs.getInt("User_ID");
                String appointmentLocation = rs.getString("Location");
                Appointment weekly = new Appointment(appointmentId, appointmentTitle, appointmentDescription, appointmentContact, appointmentContactName,
                        appointmentType, appointmentStart, appointmentEnd, appointmentCustomerId, appointmentUserId, appointmentLocation);
                weeklyList.add(weekly);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return weeklyList;
    }

    /**
     * This method deletes an appointment from the database on the provided appointment ID.
     *
     * @param appointmentId The ID of the appointment to be deleted.
     */
    public static void deleteAppointment(int appointmentId) {
        try {
            //SQL query to delete an appointment by its ID.
            String sqldelete = "DELETE FROM appointments WHERE Appointment_ID = ?";
            //Prepare the SQL statement
            PreparedStatement deleteAppoint = JDBC.conn.prepareStatement(sqldelete);
            //Set the appointment ID parameter
            deleteAppoint.setInt(1, appointmentId);
            //Execute the delete statement.
            deleteAppoint.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a list of monthly appointments from the database.
     *
     * @return monthlyList - The list of monthly appointments.
     */
    public static ObservableList<Appointment> getMonthlyAppointments() {
        ObservableList<Appointment> monthlyList = FXCollections.observableArrayList();
        try {
            //SQL query to select monthly appointments
            String sql = "SELECT * FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE MONTH(START) = MONTH(NOW()) ORDER BY appointments.Appointment_ID";
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                int appointmentContact = rs.getInt("Contact_ID");
                String appointmentContactName = rs.getString("Contact_Name");
                String appointmentType = rs.getString("Type");
                LocalDateTime appointmentStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appointmentEnd = rs.getTimestamp("End").toLocalDateTime();
                int appointmentCustomerId = rs.getInt("Customer_ID");
                int appointmentUserId = rs.getInt("User_ID");
                String appointmentLocation = rs.getString("Location");
                Appointment weekly = new Appointment(appointmentId, appointmentTitle, appointmentDescription, appointmentContact, appointmentContactName,
                        appointmentType, appointmentStart, appointmentEnd, appointmentCustomerId, appointmentUserId, appointmentLocation);
                monthlyList.add(weekly);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return monthlyList;
    }

    /**
     * Retrieves a list of appointments with a specific user.
     *
     * @param userId The ID of the user
     * @return an observable list of appointment objects representing the users appointments.
     */

    public static ObservableList<Appointment> getUserAppointments(int userId) {
        ObservableList<Appointment> userAppointments = FXCollections.observableArrayList();
        try {
            //SQL Query to retrieve appointments for the given user ID.
            String sqlStatement = "SELECT * FROM Appointments WHERE User_ID  = '" + userId + "'";
            PreparedStatement ps = JDBC.conn.prepareStatement(sqlStatement);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                int appointmentContact = rs.getInt("Contact_ID");
                String appointmentType = rs.getString("Type");
                LocalDateTime appointmentStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appointmentEnd = rs.getTimestamp("End").toLocalDateTime();
                int appointmentCustomerId = rs.getInt("Customer_ID");
                int appointmentUserId = rs.getInt("User_ID");
                String appointmentLocation = rs.getString("Location");
                Appointment results = new Appointment(appointmentId, appointmentTitle, appointmentDescription, appointmentContact,
                        appointmentType, appointmentStart, appointmentEnd, appointmentCustomerId, appointmentUserId, appointmentLocation);
                userAppointments.add(results);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userAppointments;
    }

    /**
     * Retrieves the appointments associated with a specific contact.
     *
     * @param contactId The ID of the contact.
     * @return An observableList of Appointment objects representing the associated appointments.
     */

    public static ObservableList<Appointment> getContactAppointment(int contactId) {
        ObservableList<Appointment> contactAppointment = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE appointments.Contact_ID  = '" + contactId + "'";
            PreparedStatement ps = JDBC.conn.prepareStatement(sqlStatement);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                int appointmentContact = rs.getInt("Contact_ID");
                String appointmentContactName = rs.getString("Contact_Name");
                String appointmentType = rs.getString("Type");
                LocalDateTime appointmentStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appointmentEnd = rs.getTimestamp("End").toLocalDateTime();
                int appointmentCustomerId = rs.getInt("Customer_ID");
                int appointmentUserId = rs.getInt("User_ID");
                String appointmentLocation = rs.getString("Location");
                Appointment results = new Appointment(appointmentId, appointmentTitle, appointmentDescription, appointmentContact, appointmentContactName,
                        appointmentType, appointmentStart, appointmentEnd, appointmentCustomerId, appointmentUserId, appointmentLocation);
                contactAppointment.add(results);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contactAppointment;
    }

    /**
     * Retrieves the count of appointment types and provides distinct results for the reports table.
     *
     * @return appointmentListType - The list of appointment types and their counts.
     */
    public static ObservableList<Appointment> appointmentType() {
        ObservableList<Appointment> appointmentListType = FXCollections.observableArrayList();
        try {
            //SQL query to count appointment types and provide distinct results for reports table.
            String sql = "SELECT Type, Count(*) AS NUM FROM appointments GROUP BY Type";
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String appointmentType = rs.getString("Type");
                int appointmentTypeTotal = rs.getInt("NUM");
                Appointment results = new Appointment(appointmentType, appointmentTypeTotal);
                appointmentListType.add(results);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentListType;
    }

    /**
     * Retrieves the count of appointments per month for the reports.
     *
     * @return appointmentTypeMonthTotal - The list of months and their appointment counts.
     */
    public static ObservableList<Appointment> getAppointmentTypeMonth() {
        ObservableList<Appointment> appointmentTypeMonthTotal = FXCollections.observableArrayList();
        try {
            //SQL query to get the month and number of appointments per month.
            String sql = "SELECT DISTINCT(MONTHNAME(Start)) AS Month, Count(*) AS NUM FROM appointments GROUP BY Month";
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String appointmentType = rs.getString("Month");
                int appointmentTypeTotal = rs.getInt("NUM");
                Appointment results = new Appointment(appointmentType, appointmentTypeTotal);
                appointmentTypeMonthTotal.add(results);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentTypeMonthTotal;
    }


}
