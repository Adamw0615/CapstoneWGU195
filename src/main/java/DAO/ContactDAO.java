package DAO;

/**
 * The contactDAO class which is used to obtain contact information from the database using queries.
 *
 * @author Adam Wright
 */

import Helper.JDBC;
import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDAO {
    /**
     * Retrieve all contacts from the database and returns them as an observable list.
     *
     * @return contactList
     */

    public static ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Contact_ID, Contact_Name, Email FROM contacts";
            PreparedStatement contacts = JDBC.conn.prepareStatement(sql);
            ResultSet rs = contacts.executeQuery();

            while (rs.next()) {
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");
                Contact d = new Contact(contactId, contactName, contactEmail);
                contactList.add(d);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contactList;
    }

    /**
     * Retrieves contact information for a specific contact ID from the  database.
     *
     * @param contactId contact Id The ID of the contact to retrieve information for
     * @return The Contact object with the information.
     */
    public static Contact returnContactList(int contactId) {
        try {
            String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ps.setInt(1, contactId);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            rs.next();
            int searchedContactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            Contact s = new Contact(searchedContactId, contactName, contactEmail);
            return s;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the contact ID based on the contact name.
     *
     * @param contactName The name of the contact.
     * @return The contact ID.
     * @throws SQLException If there is an error executing the SQL query.
     */
    public static int returnContactId(String contactName) throws SQLException {
        int contactId = 0;
        //SQK quert to select the contact ID based on the contact name.
        String sql = "SELECT * FROM contacts WHERE Contact_Name = ?";
        //Prepare the SQL statement.
        PreparedStatement ps = JDBC.conn.prepareStatement(sql);
        //Set the contact name parameter in the SQL statement.
        ps.setString(1, contactName);
        //Execute the SQL query and get the result set.
        ResultSet rs = ps.executeQuery();
        //Iterate over the result set to get the contact ID.
        while (rs.next()) {
            contactId = rs.getInt("Contact_ID");
        }
        return contactId;
    }
}
