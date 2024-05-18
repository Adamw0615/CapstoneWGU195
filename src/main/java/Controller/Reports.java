package Controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CountryDAO;
import Model.Appointment;
import Model.Contact;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The Report Class
 *
 * @author Adam Wright
 */

public class Reports implements Initializable {
    public TableView apptTypeTable;
    public TableView apptMonthTable;
    public TableView countryTable;
    @FXML
    private TableColumn<Appointment,String> apptTotalType;
    @FXML
    private TableColumn<Appointment,Integer> apptTypeTotal;
    @FXML
    private TableColumn<Appointment,String> apptMonth;
    @FXML
    private TableColumn<Appointment,Integer> apptMonthTotal;
    @FXML
    private  TableColumn<Appointment,Integer> apptId;
    @FXML
    private TableColumn<Appointment,String> apptTitle;
    @FXML
    private TableColumn<Appointment,String> apptDescription;
    @FXML
    private TableColumn<Appointment,String> apptContact;
    @FXML
    private TableColumn<Appointment,String> apptType;
    @FXML
    private TableColumn<Appointment,Timestamp> apptStart;
    @FXML
    private TableColumn<Appointment,Timestamp> apptEnd;
    @FXML
    private TableColumn<Appointment,Integer> apptCustId;
    @FXML
    private TableColumn<Appointment,Integer> apptUserId;
    @FXML
    private ComboBox<Contact> contactCombo;
    @FXML
    private TableColumn apptCountry;
    @FXML
    private TableColumn apptCountryTotal;
    @FXML
    private Button bckToMenu;
    @FXML
    private TableView contactTable;

    //The observablelist that contains all the contacts from the database.
    ObservableList<Contact> contactList = ContactDAO.getAllContacts();

    /**
     *  Initializes the report tables and sets placeholders when no content is available in tables.
     *
     * @param url The URL
     * @param resourceBundle The resource bundle.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //The Contact table
        //Set the items of the contact combo box
        contactCombo.setItems(contactList);
        //Set the number of visible rows in the contact combo box
        contactCombo.setVisibleRowCount(10);
        //Set the placeholder of the contact table
        contactTable.setPlaceholder(new Label("Select a contact."));
        //Set the cell value factories for appointment table columns.
        apptId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        apptTitle.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        apptDescription.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        apptContact.setCellValueFactory(new PropertyValueFactory<>("appointmentContactName"));
        apptStart.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        apptEnd.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        apptCustId.setCellValueFactory(new PropertyValueFactory<>("appointmentCustomerId"));
        apptType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        apptUserId.setCellValueFactory(new PropertyValueFactory<>("appointmentUserId"));
        //Refresh the contact table
        contactTable.refresh();

        // Set the the items for the appointments by month table
        apptMonthTable.setItems(AppointmentDAO.getAppointmentTypeMonth());
        // Set the placeholder for the appointments by month table
        apptMonthTable.setPlaceholder(new Label("Monthly data is unavailable."));
        // Set the cell value factories for the appointments by month table columns
        apptMonth.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        apptMonthTotal.setCellValueFactory(new PropertyValueFactory<>("appointmentTypeTotal"));

        // Set the items for appointment type table
        apptTypeTable.setPlaceholder(new Label("Type data is unavailable."));
        apptTypeTable.setItems(AppointmentDAO.appointmentType());
        //Set the cell value factories for the appointment type table columns
        apptTotalType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        apptTypeTotal.setCellValueFactory(new PropertyValueFactory<>("appointmentTypeTotal"));

        // Set the items for the contacts by country table
        countryTable.setItems(CountryDAO.countryTotals());
        // Set the placeholder for the contacts by country table
        countryTable.setPlaceholder(new Label("Appointments by country data is unavailable."));
        // Set the cell value factories for the contacts by country table columns
        apptCountryTotal.setCellValueFactory(new PropertyValueFactory<>("countryMonthTotal"));
        apptCountry.setCellValueFactory(new PropertyValueFactory<>("countryMonth"));
    }

    /**
     * The method that populates the contact table when a contact is selected
     *
     * @param actionEvent The event that populates the associated appts when the contact is selected.
     * @throws SQLException Responsible for addressing unhandled SQL exceptions.
     */
    public void PopulateContact(ActionEvent actionEvent) throws SQLException {
        // Getting contact name from the combo box
        String contactName = String.valueOf(contactCombo.getValue());
        //Converting contact name to contact id to obtain associated appointments.
        int contactId = ContactDAO.returnContactId(contactName);
        //Checks if the contact has any appointments
        if (AppointmentDAO.getContactAppointment(contactId).isEmpty()) {
            //Display messgae if no appointments exist for the contact.
            contactTable.setPlaceholder(new Label(contactName + " has no appointments."));
            contactTable.refresh();
            //Clear the table items and set the placeholder message
            for (int i = 0; i < contactTable.getItems().size(); i++) {
                contactTable.getItems().clear();
                contactTable.setPlaceholder(new Label(contactName + " has no appointments."));
            }
        } else {
            //Populate the table with the associated appointments.
            contactTable.setItems(AppointmentDAO.getContactAppointment(contactId));
        }
    }

    /**
     * The action event responsible for taking the user back to the main menu.
     *
     * @param actionEvent The event that takes the user back to the main menu (bckToMenu).
     * @throws IOException Responsible for addressing unhandled IO exceptions.
     */
    public void bckToMenu(ActionEvent actionEvent) throws IOException {
        new FXMLLoader();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Menu.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}




