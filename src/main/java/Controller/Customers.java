package Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import Model.Appointment;
import Model.Country;
import Model.Customer;
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
import java.util.Objects;
import java.util.ResourceBundle;



public class Customers implements Initializable {

    public TableColumn<Country, Integer> customerCountryColumn;
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;
    @FXML
    private TableColumn<Customer, String> customerNameColumn;
    @FXML
    private TableColumn<Customer, String> customerAddressColumn;
    @FXML
    private TableColumn<Customer, String> customerPostalColumn;
    @FXML
    private TableColumn<Customer, String> customerPhoneColumn;
    @FXML
    private TableColumn<Customer, Integer> customerFirstColumn;
    @FXML
    private Button customerDeleteLabel;
    @FXML
    private Button customerAddLabel;
    @FXML
    private Button customerUpdateLabel;

    // The Observable list that has all the customers from the database.
    ObservableList<Customer> CustomerList = CustomerDAO.getCustomerList();

    /**
     * Action event for delete button on the customer screen.
     * If no customer is selected an error message will be generated.
     * If a valid customer is selected and has no attached appointments a warning message to confirm removal will be generated.
     * If appointments are found for a customer, an alert will be generated to confirm both removal of customer and associated appointments.
     * @param actionEvent The event for when delete button on customer screen is pressed
     */

    public void actionCustomerDelete(ActionEvent actionEvent) {
        int count = 0;
        ObservableList<Appointment> appointmentList = AppointmentDAO.getAppointmentList();
        // Get the selected customer
        Customer customer = customerTable.getSelectionModel().getSelectedItem();

        // If no customer is selected, display an error message and return.
        if (customer == null) {
            Helper.ErrorMsg.getError(7);
            return;
        }
        //Get the selected customer ID
        int selectedCustomer = customerTable.getSelectionModel().getSelectedItem().getCustomerId();
        // Counts the number of associated appointments for the selected customer
        for (Appointment appointment : appointmentList) {
            int appointmentCustId = appointment.getAppointmentCustomerId();
            if (appointmentCustId == selectedCustomer) {
                count++;
            }
        }
        // Id there are associated appointments, display an alert asking for confirmation to delete both the appointments and customer.
        if (count > 0) {
            Alert associatedAppointment = new Alert(Alert.AlertType.WARNING);
            associatedAppointment.setTitle("Alert");
            associatedAppointment.setHeaderText("Alert: " + count + " associated appointment(s).");
            associatedAppointment.setContentText("There are " + count + " associated appointment(s) for the selected customer.\n\n" +
                    "Please select OK to delete the associated appointments and customer.\n\n" +
                    "Otherwise, please press cancel to return to the main screen.");
            associatedAppointment.getButtonTypes().clear();
            associatedAppointment.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
            associatedAppointment.getDialogPane().setMinHeight(250);
            associatedAppointment.getDialogPane().setMinWidth(400);
            associatedAppointment.showAndWait();
            if (associatedAppointment.getResult() == ButtonType.OK) {
                for (Appointment appointment : appointmentList) {
                    if (appointment.getAppointmentCustomerId() == selectedCustomer)
                        AppointmentDAO.deleteAppointment(appointment.getAppointmentId());
                }
                CustomerDAO.deleteCustomer(customerTable.getSelectionModel().getSelectedItem().getCustomerId());
                Helper.ErrorMsg.confirmation(2);
                CustomerList = CustomerDAO.getCustomerList();
                customerTable.setItems(CustomerList);
                customerTable.refresh();
            } else if (associatedAppointment.getResult() == ButtonType.CANCEL) {
                associatedAppointment.close();
            }
        }
        // If there are no associated appointments for the selected user - an alert will be generated asking to confirm removal of the selected customer
        if (count == 0) {
            Alert confirmRemoval = new Alert(Alert.AlertType.WARNING);
            confirmRemoval.setTitle("Alert");
            confirmRemoval.setContentText("Remove selected customer?\n" +
                    "Press OK to remove.\nCancel to return to screen.");
            confirmRemoval.getButtonTypes().clear();
            confirmRemoval.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
            confirmRemoval.showAndWait();
            if (confirmRemoval.getResult() == ButtonType.OK) {
                CustomerDAO.deleteCustomer(customerTable.getSelectionModel().getSelectedItem().getCustomerId());
                Helper.ErrorMsg.confirmation(2);
                CustomerList = CustomerDAO.getCustomerList();
                customerTable.setItems(CustomerList);
                customerTable.refresh();
            } else if (confirmRemoval.getResult() == ButtonType.CANCEL) {
                confirmRemoval.close();
            }
        }
    }


    /**
     * This method is an action event handler for the add button in the customer view.
     * It redirects the user to the addCustomers file
     *
     * @param actionEvent The event triggered by the add button click.
     * @throws IOException If there is an issue loading the addCustomers.fxml file
     */
    public void actionCustomerAdd(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/addCustomers.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * The Action event handler for updating a customer.
     * If no customer is selected, it redirects to the modifyCustomer file to modify the customer.
     * if no customer is selected, it displays an error message.
     *
     * @param actionEvent The event triggered by the update button click.
     * @throws IOException  If there is an issue loading the modifyCustomer.fxml file
     * @throws SQLException If there is an issue with the SQL connection.
     */

    public void actionCustomerUpdate(ActionEvent actionEvent) throws IOException, SQLException {
        if (customerTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/modifyCustomers.fxml"));
            loader.load();
            modifyCustomers MCController = loader.getController();
            MCController.getCustomerInfo(customerTable.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.centerOnScreen();
            stage.show();
        } else {
            Helper.ErrorMsg.getError(7);
        }
    }

    /**
     * Initializes customer information on the customer screen.
     *
     * @param url The location used to resolve relative paths for the root object, or null
     * if the location is not known.
     * @param resourceBundle A resource bundle that contains locale-specific objects.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTable.setItems(CustomerDAO.getCustomerList());
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerPostalColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        customerFirstColumn.setCellValueFactory(new PropertyValueFactory<>("customerDivisionName"));
        customerCountryColumn.setCellValueFactory(new PropertyValueFactory<>("customerCountryName"));
    }

    /**
     *This method is an action event handler for the back to menu button in the customer view.
     *It loads the Menu.fxml file and displays it in a new window.
     * @param actionEvent The event triggered by the back to menu button click.
     * @throws IOException If there is an issue loading the Menu.fxml file.
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





