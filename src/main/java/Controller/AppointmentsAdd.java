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
 * The appointmentsadd class
 *
 * @author Adam Wright
 */


public class AppointmentsAdd implements Initializable {

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
        private ComboBox<Contact> addContactcombo;
        @FXML
        private Button saveButton;
        @FXML
        private Button cancelButton;
        @FXML
        private DatePicker addStartDateSelecter;
        @FXML
        private ComboBox<LocalTime> addStartTimeCombo;
        @FXML
        private DatePicker addEndDateSelecter;
        @FXML
        private ComboBox<LocalTime> addEndTimeCombo;
        @FXML
        private ComboBox<User> addUserCombo;
        @FXML
        private ComboBox<Customer> addCustomerCombo;
        private final int NodaysAdded  = 0;


/**
 *The action event for the save button that will validate the data and will display an alert if there is an error
 * regarding missing fields. It will also verify that there aren't any overlapping appointments. There will be a check
 * to verify that the appointments are during business hours.
 *
 * @param actionEvent The event for save button
 * @throws IOException addresses unhandled IO exceptions
 * @throws SQLException addresses unhandled SQL exceptions
 */

public void actionSaveButton(ActionEvent actionEvent) throws IOException, SQLException {

//Get the appointment details
    String apptTitle = apptTitleTextField.getText();
    String apptDescription = apptDescriptionTextField.getText();
    String apptmentType = apptTypeTextField.getText();

    // Get selected contact
    Contact contact = addContactcombo.getValue();
    if (contact == null) {
        Helper.ErrorMsg.getError(24);
        return;
    }
    int apptContact = contact.getContactId();

    // Get selected start date
    LocalDate startPicker = addStartDateSelecter.getValue();
    if (startPicker == null) {
        Helper.ErrorMsg.getError(18);
        return;
    }

    // Get selected start time
    LocalTime start = addStartTimeCombo.getValue();
    if (start == null) {
        Helper.ErrorMsg.getError(19);
        return;
    }
    LocalDateTime apptStart = LocalDateTime.of(addStartDateSelecter.getValue(), addStartTimeCombo.getValue());

    // Get selected end date
    LocalDate endPicker = addEndDateSelecter.getValue();
    if (endPicker == null) {
        Helper.ErrorMsg.getError(20);
        return;
    }

    // Get selected end time
    LocalTime end = addEndTimeCombo.getValue();
    if (end == null) {
        Helper.ErrorMsg.getError(21);
        return;
    }
    LocalDateTime apptEnd = LocalDateTime.of(addEndDateSelecter.getValue(), addEndTimeCombo.getValue());

    // Get selected customer
    Customer customer = addCustomerCombo.getValue();
    if (customer == null) {
        Helper.ErrorMsg.getError(22);
        return;
    }
    int apptCustomerId = addCustomerCombo.getValue().getCustomerId();

    // Get selected user
    User user = addUserCombo.getValue();
    if (user == null) {
        Helper.ErrorMsg.getError(23);
        return;
    }
    int apptUserId = addUserCombo.getValue().getUserID();
    String apptLocation = apptLocationTextField.getText();

    if (apptTitle.isBlank() || apptTitle.isEmpty()) {
        Helper.ErrorMsg.getError(8);
    } else if (apptDescription.isBlank() || apptDescription.isEmpty()) {
        Helper.ErrorMsg.getError(9);
    } else if (apptmentType.isEmpty() || apptmentType.isBlank()) {
        Helper.ErrorMsg.getError(10);
    } else if (apptLocation.isBlank() || apptLocation.isEmpty()) {
        Helper.ErrorMsg.getError(11);
    } else if (Appointment.businessHours(apptStart, apptEnd)) {
        return;
    } else if (Appointment.overlapCheck(apptCustomerId, apptStart, apptEnd)) {
        return;
    } else {
        AppointmentDAO.addAppointment(apptTitle, apptDescription, apptLocation, apptmentType, apptStart, apptEnd, apptCustomerId, apptUserId, apptContact);
        Appointment.backToAppointments(actionEvent);
        Helper.ErrorMsg.confirmation(5);
    }
}

/**
 * Adds the specific number of minutes to the given date and time.
 *
 * @param minutesToAdd the number of minutes to add.
 * @return  the updated date and time.
 */

public long plusMinutes(long minutesToAdd) {
    try {
        return minutesToAdd;
    } catch (RuntimeException e) {
        {
            throw new RuntimeException(e);
        }
    }

    }
    /**
     * Initializes the appointment form with default values and sets up event listeners for the
     * date and time pickers.
     *
     * @param url The url of the resource.
     * @param resourceBundle The resource bundle.
     */

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        apptIDTextField.setId(apptIDTextField.getId());
        addStartTimeCombo.setItems(Appointment.getTimes());
        addEndTimeCombo.setItems(Appointment.getTimes());
        ObservableList<Contact> contactList = ContactDAO.getAllContacts();
        addContactcombo.setItems(contactList);
        ObservableList<User> userList = UserDAO.getUserList();
        addUserCombo.setItems(userList);
        ObservableList<Customer> customerList = CustomerDAO.getCustomerList();
        addCustomerCombo.setItems(customerList);

        // Lambda expressions for the date picker
        addStartDateSelecter.valueProperty().addListener((ov, oldValueDate, newValueDate) -> addEndDateSelecter.setValue(newValueDate.plusDays(NodaysAdded)));
        addStartTimeCombo.valueProperty().addListener((observableValue, oldValueTime, newValueTime) -> addEndTimeCombo.setValue(newValueTime.plusMinutes(30)));
    }

/**
 * The action event for the cancel button that returns the user to the main screen.
 *
 * @param actionEvent The event for the cancel button.
 * @throws IOException If an I/O error occurs.
 */
    public void actionCancelButton (ActionEvent actionEvent) throws IOException {
        //Redirects the user back tio the main screen.
        Appointment.backToAppointments(actionEvent);
    }

}