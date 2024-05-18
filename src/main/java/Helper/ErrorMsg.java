package Helper;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The ErrorMsg class.
 *
 * @author Adam Wright
 */

public class ErrorMsg implements Initializable {

// The resource bundle for localization
    static ResourceBundle langBundle = ResourceBundle.getBundle("/language/lang");
    /**
     * Alerts with alert type error or specific information based on the error case number.
     *
     * @param whichError The associated case number.
     */
    public static void getError(int whichError) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        switch (whichError) {

            // Incorrect Username error message alert.
            case 1:
                alert.setTitle(langBundle.getString("UsernameError"));
                alert.setContentText(langBundle.getString("incorrectUsername"));
                alert.showAndWait();
                break;

            // Incorrect Password error message alert
            case 2:
                alert.setTitle(langBundle.getString("PasswordError"));
                alert.setContentText(langBundle.getString("wrongPassword"));
                alert.showAndWait();
                break;

            // Incorrect Username and Password alert
            case 3:
                alert.setTitle(langBundle.getString("UserPassError"));
                alert.setContentText(langBundle.getString("bothIncorrect"));
                alert.showAndWait();
                break;

            // Deletion of customer confirmation alert
            case 4:
                alert.setTitle("Confirm Deletion");
                alert.setContentText("Please confirm the deletion of this customer.");
                alert.showAndWait();
                break;

            // Username is blank error message alert
            case 5:
                alert.setTitle(langBundle.getString("BlankUserNameError"));
                alert.setContentText(langBundle.getString("UserNameBlank"));
                alert.showAndWait();
                break;

            // Password is blank error message alert
            case 6:
                alert.setTitle(langBundle.getString("PassWordErrorBlank"));
                alert.setContentText(langBundle.getString("PassWordBlank"));
                alert.showAndWait();
                break;

            // No customer is selected alert
            case 7:
                alert.setTitle("No Selection Detected");
                alert.setContentText("A customer must be selected.");
                alert.showAndWait();
                break;

            // Title text-field is blank alert
            case 8:
                alert.setTitle("The title is blank");
                alert.setContentText("A title must be entered.");
                alert.showAndWait();
                break;

            // Description text-field is blank alert
            case 9:
                alert.setTitle("The description is blank");
                alert.setContentText("A description must be entered.");
                alert.showAndWait();
                break;

            // Type text-field is blank alert
            case 10:
                alert.setTitle("The type is blank");
                alert.setContentText("A type must be entered.");
                alert.showAndWait();
                break;

            // Location text-field is blank alert
            case 11:
                alert.setTitle("The location is blank");
                alert.setContentText("A location must be entered.");
                alert.showAndWait();
                break;

            // No appointment was selected alert
            case 12:
                alert.setTitle("No Appointment Selected");
                alert.setContentText("An appointment must be selected.");
                alert.showAndWait();
                break;

            // Overlapping appointments alert
            case 13:
                alert.setTitle("There is an appointment overlap");
                alert.setContentText("");
                alert.showAndWait();
                break;

            // Customer Name is blank alert
            case 14:
                alert.setTitle("No Customer Name is detected");
                alert.setContentText("The Customer Name must be entered.\nPlease enter a valid name.");
                alert.showAndWait();
                break;

            // Customer address is blank alert
            case 15:
                alert.setTitle("No Customer address is detected");
                alert.setContentText("The Customer address must be entered.\nPlease enter a valid address.");
                alert.showAndWait();
                break;

            // Customer Postal code is blank alert
            case 16:
                alert.setTitle("No Customer postal code is detected");
                alert.setContentText("The Customer postal code must be entered.\nPlease enter a valid postal code.");
                alert.showAndWait();
                break;

            // Division/Country Field is blank alert
            case 17:
                alert.setTitle("Division/Country Field");
                alert.setContentText("Please select a valid division/country.");
                alert.showAndWait();
                break;

            // Start date picker is empty alert
            case 18:
                alert.setTitle("A valid start date is required");
                alert.setContentText("No start date was selected. Please select a valid start date.");
                alert.showAndWait();
                break;

            // Start time combo is empty alert
            case 19:
                alert.setTitle("A valid start time is required");
                alert.setContentText("No start time was selected. Please select a valid start time.");
                alert.showAndWait();
                break;

            // End date picker is empty alert
            case 20:
                alert.setTitle("A valid end date is required");
                alert.setContentText("No end date was selected. Please select a valid end date.");
                alert.showAndWait();
                break;

            // End time combo is empty alert
            case 21:
                alert.setTitle("A valid end time is required");
                alert.setContentText("No end time was selected. Please select a valid end time.");
                alert.showAndWait();
                break;

            // Customer combo is empty alert
            case 22:
                alert.setTitle("A valid customer is required");
                alert.setContentText("No customer was selected. Please select a valid customer.");
                alert.showAndWait();
                break;

            // User combo is empty alert
            case 23:
                alert.setTitle("A valid user is required");
                alert.setContentText("No user was selected. Please select a valid user.");
                alert.showAndWait();
                break;

            // Contact combo is empty alert
            case 24:
                alert.setTitle("A valid contact is required");
                alert.setContentText("No contact was selected. Please select a valid contact.");
                alert.showAndWait();
                break;

            // Phone number field is empty alert
            case 25:
                alert.setTitle("Error: Phone number");
                alert.setContentText("A valid phone number must be entered.");
                alert.showAndWait();
                break;
            // Default that throws an error
            default:
                throw new IllegalStateException("Unexpected value: " + whichError);
        }
    }

    /**
     * Displays a confirmation alert based on given case number.
     *
     * @param confirm The associated case number.
     */
    public static void confirmation(int confirm) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        switch (confirm) {

            // No Appointments in the next 15 minutes alert.
            case 1:
                alert.setTitle(langBundle.getString("Alert"));
                alert.setContentText(langBundle.getString("noApptwithin15Mins."));
                alert.showAndWait();
                break;

            // Customer has successfully been removed alert
            case 2:
                alert.setTitle("The customer has been removed");
                alert.setHeaderText("Success");
                alert.setContentText("Customer has been successfully removed. ");
                alert.showAndWait();
                break;

            // Customer has been successfully added alert
            case 3:
                alert.setTitle("Customer has been added.");
                alert.setHeaderText("Success");
                alert.setContentText("Customer has been successfully added. ");
                alert.showAndWait();
                break;

            // Appointment has been modified alert
            case 4:
                alert.setTitle("Appointment has been modified");
                alert.setHeaderText("Success");
                alert.setContentText("Appointment has been successfully modified. ");
                alert.showAndWait();
                break;

            // Appointment successfully added alert
            case 5:
                alert.setTitle("Appointment has been added");
                alert.setHeaderText("Success");
                alert.setContentText("Appointment has been successfully added. ");
                alert.showAndWait();
                break;
        }
    }

    /**
     * This method is called automatically by JavaFx to initialize the controller.
     * It is responsible for setting up any necessary resources or event handlers.
     *
     * @param url The URL location of the FXML file.
     * @param resourceBundle The resource bundle for the FXML file used to localize the controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}
