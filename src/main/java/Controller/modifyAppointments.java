package Controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * The modifyAppointments class
 *
 * @author Adam Wright
 */



public class modifyAppointments implements Initializable {
    @FXML
    private TextField apptIDTextField;
    @FXML
    private TextField apptTitleTextField;
    @FXML
    private TextField apptDescriptionTextField;
    @FXML
    private TextField apptLocationTextField;
    @FXML
    private TextField apptTypeTextField;
    @FXML
    private ComboBox<Contact> contactCombo;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private DatePicker startDateSelecter;
    @FXML
    private ComboBox<LocalTime> startTimeCombo;
    @FXML
    private DatePicker endDateSelecter ;
    @FXML
    private ComboBox<LocalTime> endTimeCombo;
    @FXML
    private ComboBox<User> userCombo;
    @FXML
    private ComboBox<Customer> customerCombo;

    /**
     * Initializes the combo boxes for contacts customers, and users with their respective data.
     * Sets the visible row count for each combo box.
     *
     * @param url The URL of the resource.
     * @param resourceBundle The resource bundle.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Get all contacts and set them as items in the contact combo box.
        ObservableList<Contact> contactList = ContactDAO.getAllContacts();
        contactCombo.setItems(contactList);
        contactCombo.setVisibleRowCount(10);
    //Get all customers and set them as items in the customer combo box.
        ObservableList<Customer> customerList = CustomerDAO.getCustomerList();
        customerCombo.setItems(customerList);
        customerCombo.setVisibleRowCount(10);
// Get all users and set them as items in the user combo box.
        ObservableList<User> userList = UserDAO.getUserList();
        userCombo.setItems(userList);
        userCombo.setVisibleRowCount(10);
    }

    /**
     * The action for save button that wil get values from each field  and update the appointment.
     *
     * @param actionEvent The event for save button
     * @throws IOException Addresses unhandled exceptions.
     */

    public void actionSaveButton(ActionEvent actionEvent) throws IOException {
        //Get appointment details from text fields
        int apptId = Integer.parseInt(apptIDTextField.getText());
        String apptTitle = apptTitleTextField.getText();
        String apptDescription = apptDescriptionTextField.getText();
        String apptType = apptTypeTextField.getText();
        String apptLocation = apptLocationTextField.getText();

        // Handling null pointer exception and display error message.
        Contact contact = contactCombo.getValue();
        if (contact == null) {
            Helper.ErrorMsg.getError(24);
        }
        int apptContact = contactCombo.getValue().getContactId();


        // Handling null pointer exception and alert message
        LocalDate startPicker = startDateSelecter.getValue();
        if (startPicker == null) {
            Helper.ErrorMsg.getError(18);
            return;
        }

        // Handling null pointer exception and alert message
        LocalTime startTime = startTimeCombo.getValue();
        if (startTime == null) {
            Helper.ErrorMsg.getError(19);
            return;
        }
        LocalDateTime apptStart = LocalDateTime.of(startDateSelecter.getValue(), startTimeCombo.getValue());

        // Handling null pointer exception and alert message
        LocalDate endPicker = endDateSelecter.getValue();
        if (endPicker == null) {
            Helper.ErrorMsg.getError(20);
            return;
        }

        // Handling null pointer exception and alert message
        LocalTime end = endTimeCombo.getValue();
        if (end == null) {
            Helper.ErrorMsg.getError(21);
            return;
        }
        LocalDateTime apptEnd = LocalDateTime.of(endDateSelecter.getValue(), endTimeCombo.getValue());

        // Handling null pointer exception and alert message
        Customer customer = customerCombo.getValue();
        if (customer == null) {
            Helper.ErrorMsg.getError(22);
            return;
        }
        int apptCustomerId = customerCombo.getValue().getCustomerId();

        // Handling null pointer exception and alert message
        User user = userCombo.getValue();
        if (user == null) {
            Helper.ErrorMsg.getError(23);
            return;
        }
        int apptUserId = userCombo.getValue().getUserID();

        // Checking and verifying text-fields are valid and not blank or empty
        if (apptTitle.isBlank() || apptTitle.isEmpty()) {
            Helper.ErrorMsg.getError(8);
        } else if (apptDescription.isBlank() || apptDescription.isEmpty()) {
            Helper.ErrorMsg.getError(9);
        } else if (apptType.isEmpty() || apptType.isBlank()) {
            Helper.ErrorMsg.getError(10);
        } else if (apptLocation.isBlank() || apptLocation.isEmpty()) {
            Helper.ErrorMsg.getError(11);
        } else if (Appointment.businessHours(apptStart, apptEnd)) {
            return;
        } else if (Appointment.overlapCheck(apptCustomerId, apptStart, apptEnd)) {
            return;
        } else {
            AppointmentDAO.updateAppointment(apptId, apptTitle, apptDescription, apptLocation, apptType, apptStart, apptEnd, apptCustomerId, apptUserId, apptContact);
            Appointment.backToAppointments(actionEvent);
            Helper.ErrorMsg.confirmation(4);
        }
    }

    /**
     * The action event handler for the cancel button.
     *The user is redirected back to the main appointments page.
     *
     * @param actionEvent The action event triggered by the cancel button.
     * @throws IOException If there is an unhandled exception.
     */

    public void actionCancelButton(ActionEvent actionEvent) throws IOException {
        //Call the backToAppointments method from the Appointment class.
        //The user is redirected back to the main appointments page.
        Appointment.backToAppointments(actionEvent);
    }

    /**
     * Sets the appointment information in the form fields.
     *
     * @param appointment The appointment object containg the information to be displayed.
     * @throws SQLException If there is an unhandled exception.
     */

    public void getAppointmentInfo(Appointment appointment) throws SQLException {
        //Set the start and end time combo boxes wth avaliable times.
        startTimeCombo.setItems(Appointment.getTimes());
        endTimeCombo.setItems(Appointment.getTimes());
        //Set the appointment ID text field with the appointment ID.
        apptIDTextField.setText(Integer.toString(appointment.getAppointmentId()));
        //Set the appointment title text field with the appointment title.
        apptTitleTextField.setText(appointment.getAppointmentTitle());
        //Set the appointment description text field with the appointment description.
        apptDescriptionTextField.setText(appointment.getAppointmentDescription());
        //Set the appointment location text field with the appointment location.
        apptLocationTextField.setText(appointment.getAppointmentLocation());
        //Set the appointment type text field with the appointment type.
        apptTypeTextField.setText(appointment.getAppointmentType());
        //Set the start date picker with appointment start date.
        startDateSelecter.setValue(appointment.getAppointmentStart().toLocalDate());
        //Set the start time combo box with the appointment start time.
        startTimeCombo.setValue(appointment.getAppointmentStart().toLocalTime());
        //Set the end date picker with the appointment end date.
        endDateSelecter.setValue(appointment.getAppointmentEnd().toLocalDate());
        //Set the end time combo box with the appointment end time.
        endTimeCombo.setValue(appointment.getAppointmentEnd().toLocalTime());
        Contact d = ContactDAO.returnContactList(appointment.getAppointmentContact());
        contactCombo.setValue(d);
        Customer c = CustomerDAO.returnCustomerList(appointment.getAppointmentCustomerId());
        customerCombo.setValue(c);
        User u = UserDAO.returnUserId(appointment.getAppointmentUserId());
        userCombo.setValue(u);
    }
}



