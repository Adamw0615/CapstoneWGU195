package Controller;


import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.FirstLvlDivisionDAO;
import Model.Appointment;
import Model.Country;
import Model.Customer;
import Model.FirstLvlDivision;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import DAO.AppointmentDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.time.LocalDateTime.now;

/**
 * The modifyCustomers class
 *
 * @author Adam Wright
 */

public class modifyCustomers implements Initializable {

    @FXML
    private TextField customerIDTextField;
    @FXML
    private TextField customerNameTextField;
    @FXML
    private TextField customerPhoneTextField;
    @FXML
    private TextField customerAddressTextField;
    @FXML
    private TextField customerPostalTextField;
    @FXML
    private ComboBox<FirstLvlDivision> customerDivisionCombo;
    @FXML
    private ComboBox<Country> customerCountryCombo;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;





    /**
     * Initializes the country combo field with a list of all countries.
     * This method is called when the FXML controller is initialized.
     *
     * @param url The URL location of the FXML that was given to the FXML loader
     * @param resourceBundle The resource bundle that was given to the FXML loader.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set the items of the customerCountryCombo to all countries.
        customerCountryCombo.setItems(CountryDAO.getAllCountry());

    }



    /**
     *  Action event fot the save button. This method attempts to update the database with the modified customer info.
     *  Then will be redirected back to customers tableview to display newly modified customer.
     *  If any field is blank an appropriate error message will be displayed.  Once a country is displayed
     *  The division/location will update to reflect only options underneath the selected country.
     *
     * @param actionEvent The event for the save button being clicked.
     */
    public void actionSaveButton(ActionEvent actionEvent) {

        try {
            //Get the customer ID from the text field
            int customerId = Integer.parseInt(customerIDTextField.getText());
            //Get the customer name from the text field.
            String customerName = customerNameTextField.getText();
            //Check if the customer name field is empty.
            if (customerNameTextField.getText().isEmpty()) {
                //Display an error message if the customer field is empty.
                Helper.ErrorMsg.getError(14);
                return;
            }
            //Get the customer address from the text field.
            String customerAddress = customerAddressTextField.getText();
            //Check if the customer address field is empty or blank.
            if (customerAddressTextField.getText().isEmpty() || customerAddressTextField.getText().isBlank()) {
                //Display an error message if the customer address field is empty or blank.
                Helper.ErrorMsg.getError(15);
                return;
            }
            //Get the customer postal code from the text field.
            String customerPostalCode = customerPostalTextField.getText();
            //Check if the customer postal code field is empty or blank.
            if (customerPostalTextField.getText().isEmpty() || customerPostalTextField.getText().isBlank()) {
                //Display an error message if the customer postal code field is empty or blank.
                Helper.ErrorMsg.getError(16);
                return;
            }
            //Get the customer phone number from the text field.
            String customerPhone = customerPhoneTextField.getText();
            //Check if the customer phone number field is empty or blank.
            if (customerPhoneTextField.getText().isEmpty() || customerPhoneTextField.getText().isBlank()) {
                //Display an error message if the customer phone number field is empty or blank.
                Helper.ErrorMsg.getError(25);
                return;
            }
            //Get the customer division ID from the selected division combo box.
            int customerDivisionId = customerDivisionCombo.getValue().getDivisionID();
            //Get the customer country ID from the selected country combo box.
            int countryId = customerCountryCombo.getValue().getCountryId();
            //Set the last updated field to script
            String lastUpdatedBy = "script";
            //Set the last updated timestamp to the current timestamp.
            Timestamp lastUpdated = Timestamp.valueOf(now());
            //Update the customer in the database with the modified information.
            CustomerDAO.updateCustomer(customerId, customerName, customerAddress, customerPostalCode, customerPhone, lastUpdatedBy, lastUpdated, customerDivisionId, countryId);

            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Customers.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (NumberFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Action event for the cancel button.
     * This method handles the cancel button click event.
     * When the cancel button is clicked. It redirects the user back to the main customers table view.
     *
     * @param actionEvent The event triggered by clicking the cancel button.
     * @throws IOException thrown if there is an issue with loading the customers .fxml file.
     */

    public void actionCancelButton(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Customers.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The associated/matching divison information when the country is selected .
     *
     * @param actionEvent The event to load the division combo once the country is selected.
     */

    public void actionCountryLoader(ActionEvent actionEvent) {
        //Get the selected country from the country combo box.
        Country C = customerCountryCombo.getValue();

        try {
            //Load the divisions associated with the selected country.
            customerDivisionCombo.setItems(FirstLvlDivisionDAO.displayDivision(C.getCountryId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method retrieves the selected customer data from the database and displays it in the appropriate fields.
     *
     * @param customer The customer object containing the selected customer information.
     * @throws SQLException If there is an unhandled SQL exception.
     */

    public void getCustomerInfo(Customer customer) throws SQLException {
        //Set the customer ID in the corresponding text field.
        customerIDTextField.setText(Integer.toString(customer.getCustomerId()));
        //Set the customer name in the corresponding text field.
        customerNameTextField.setText(customer.getCustomerName());
        //Set the customer address in the corresponding text field.
        customerAddressTextField.setText(customer.getCustomerAddress());
        //Set the customer postal code in the corresponding text field.
        customerPhoneTextField.setText(customer.getCustomerPhone());
        //Set the customer postal code in the corresponding text field.
        customerPostalTextField.setText(customer.getCustomerPostalCode());

        FirstLvlDivision s = FirstLvlDivisionDAO.returnDivisionLevel(customer.getCustomerDivisionId());
        customerDivisionCombo.setValue(s);
        //Retrieve the country for the customer's country ID and set it in the corresponding combo box.
        Country c = CountryDAO.returnCountry(customer.getCustomerCountryId());
        customerCountryCombo.setValue(c);
        //Get the selected country from the combo box.
        Country C = customerCountryCombo.getValue();
        //Retrieve the divisions for the selected country and set them in the corresponding combo box.
        customerDivisionCombo.setItems(FirstLvlDivisionDAO.displayDivision(C.getCountryId()));
    }

}
