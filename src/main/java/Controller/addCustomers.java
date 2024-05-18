package Controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.FirstLvlDivisionDAO;
import Model.Country;
import Model.FirstLvlDivision;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The CustomerAdd class
 *
 * @author Adam Wright
 */

public class addCustomers implements Initializable {
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

    /**
     * The action event for the save button. This will save the customer information into the database
     * and then redirect upon successful save back to main Customers screen.
     *
     * @param actionEvent The event for the save button.
     * @throws IOException Responsible for unhandled exceptions.
     */

    public void actionSaveButton(ActionEvent actionEvent) throws IOException {
        try {
            //Check if the customers name is empty or blank.
            if (customerNameTextField.getText().isEmpty() || customerNameTextField.getText().isBlank()) {
                Helper.ErrorMsg.getError(14);
                //Check if the customers phone number is empty or blank.
            } else if (customerPhoneTextField.getText().isBlank() || customerPhoneTextField.getText().isEmpty()) {
                Helper.ErrorMsg.getError(25);
                //Check if the customers address is empty or blank.
            } else if (customerAddressTextField.getText().isEmpty() || customerAddressTextField.getText().isBlank()) {
                Helper.ErrorMsg.getError(15);
                //Check if the customers postal code is empty or blank.
            } else if (customerPostalTextField.getText().isBlank() || customerPostalTextField.getText().isEmpty()) {
                Helper.ErrorMsg.getError(16);
                //Check if the customers country is selected.
            } else if (customerDivisionCombo.getValue() == null) {
                Country country = customerCountryCombo.getValue();
                if (country == null) {
                    Helper.ErrorMsg.getError(17);
                }
            } else {
                //If all validations pass, add the customer to the database.
                String customerName = customerNameTextField.getText();
                String customerAddress = customerAddressTextField.getText();
                String customerPostalCode = customerPostalTextField.getText();
                String customerPhone = customerPhoneTextField.getText();
                FirstLvlDivision divId = customerDivisionCombo.getValue();
                LocalDateTime createdDate = LocalDateTime.now();
                LocalDateTime lastUpdated = LocalDateTime.now();
                int divisionId = divId.getDivisionID();
                CustomerDAO.addCustomer(customerName, customerAddress, customerPostalCode, customerPhone, createdDate, lastUpdated, divisionId);
                Helper.ErrorMsg.confirmation(3);
                backToCustomers(actionEvent);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Action event for cancel button.
     * Redirects back to the main customers screen.
     *
     * @param actionEvent event for cancel button
     * @throws IOException in case of an unhandled exception.
     */

    public void actionCancelButton(ActionEvent actionEvent) throws IOException {
        backToCustomers(actionEvent);
    }

    /**
     * Initializes the country combo with all countries
     *
     * @param url The URL initializes the combo box.
     * @param resourceBundle The ResourceBundle associated with the combo box.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Setting items in the customerCountryCombo
        customerCountryCombo.setItems(CountryDAO.getAllCountry());
    }

    /**
     * Method to load  the associated divisions for the selected country.
     *
     * @param actionEvent The event triggering the loading of division combo upon country selection.
     */
    public void actionCountryLoader(ActionEvent actionEvent) {
        //Retrieve the selected country.
        Country C = customerCountryCombo.getValue();
        try {
            customerDivisionCombo.setItems(FirstLvlDivisionDAO.displayDivision(C.getCountryId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Redirects back to the main customer screen.
     *
     * @param actionEvent The event that triggers the redirection.
     * @throws IOException In case of an unhandled exception.
     */

    public void backToCustomers(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Customers.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}

