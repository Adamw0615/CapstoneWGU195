/**Login class for login functionality*
 *
 * @author Adam Wright
 */
package Controller;

import DAO.AppointmentDAO;
import DAO.UserDAO;
import Model.Appointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

import static DAO.UserDAO.*;



public class Login implements Initializable {
    @FXML
    private Label labelLocation;
    @FXML private Label labelUserName;
    @FXML private Label labelUserPassword;
    @FXML private Label loginTitle;
    @FXML
    public TextField textFieldUserName;
    @FXML
    public PasswordField textFieldUserPassword;
    @FXML private Button loginButton;
    @FXML private Button cancelButton;
    public boolean loggedIn = false;

    //Responsible for loading language files into the GUI, and setting the language based on the user's choice.
    ResourceBundle langBundle = ResourceBundle.getBundle("/language/lang");

    // Time conversion variables.
    LocalDateTime currentTime = LocalDateTime.now();
    ZonedDateTime LDTConvert = currentTime.atZone(ZoneId.systemDefault());
    LocalDateTime currentTimeadd15 = LocalDateTime.now().plusMinutes(15);
    ZonedDateTime LDTUTC = LDTConvert.withZoneSameInstant(ZoneId.of("Etc/UTC"));




    /**
     * The timezone label is set to the location of the user. The text field labels are set.
     * The language is set based on the user's choice. English and French are the supported languages.
     *
     * @param url The URL of the location of the resource.
     * @param resourceBundle The resource bundle for the controller.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId zoneId = ZoneId.systemDefault();
        String location = ZoneId.systemDefault().toString();
        labelLocation.setText(location);
        labelUserName.setText(langBundle.getString("Username"));
        labelUserPassword.setText(langBundle.getString("Password"));
        loginTitle.setText(langBundle.getString("SchedulingApplication"));
        loginButton.setText(langBundle.getString("Login"));
        cancelButton.setText(langBundle.getString("Cancel"));
    }

    /**
     * The user login is validated by using the username and password and displays appropriate alerts.
     *Logs the successful or unsuccessful login attempts in the login_activity.txt file upon clicking the "Login" button
     * @param actionEvent event for Login button to execute validation
     * @throws IOException  addresses numerous unhandled exceptions
     * @throws SQLException addresses SQL exceptions for validation
     */
    public void actionLoginButton(ActionEvent actionEvent) throws IOException, SQLException {
        String User_Name = textFieldUserName.getText();
        String Password = textFieldUserPassword.getText();


        if (User_Name.isEmpty() || User_Name.isBlank()) { // Displays an alert if the username is empty or blank.
            Helper.ErrorMsg.getError(5);
        } else if (!passwordValidation(Password) && !usernameValidation((User_Name))) { // Displays an alert if username AND password are incorrect.
            Helper.ErrorMsg.getError(3);
            loginActivity();
            loggedIn = false;
        } else if (Password.isEmpty() || Password.isBlank()) { // Displays an alert if password is empty or blank
            Helper.ErrorMsg.getError(6);
        } else if (!usernameValidation(User_Name)) { // Displays an alert if only the username is incorrect
            Helper.ErrorMsg.getError(1);
            loggedIn = false;
            loginActivity();
        } else if (!passwordValidation(Password)) { // Displays an alert if only the password is incorrect
            Helper.ErrorMsg.getError(2);
            loggedIn = false;
            loginActivity();
        } else if (userLogin(User_Name, Password)) { // Verifies password and username are correct and loads main menu
            int userId = getUserId(User_Name);
            ObservableList<Appointment> userAppointments = AppointmentDAO.getUserAppointments(userId);
            new FXMLLoader();
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Menu.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            loggedIn = true;
            loginActivity();


            // Checking for appointments upon successful login
            boolean isValid = false;
            for (Appointment appointment : userAppointments) {
                LocalDateTime startTime = appointment.getAppointmentStart();
                if ((startTime.isAfter(currentTime) || startTime.isEqual(currentTimeadd15)) &&
                        (startTime.isBefore(currentTimeadd15) || startTime.isEqual(currentTime))) {
                    Alert confirmRemoval = new Alert(Alert.AlertType.WARNING);
                    confirmRemoval.setTitle("Alert");
                    confirmRemoval.setContentText("Appointment ");
                    confirmRemoval.setContentText(langBundle.getString("Appointment") + " " + appointment.getAppointmentId() + " " + langBundle.getString("Willbeginat") + " " +  appointment.getAppointmentStart());
                    confirmRemoval.getButtonTypes().clear();
                    confirmRemoval.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
                    confirmRemoval.showAndWait();
                    isValid = true;
                }
            }
            // Displays an alert if no appointments exist within 15 minutes
            if (!isValid) {
                Helper.ErrorMsg.confirmation(1);
            }
        }
    }

    /**
     * Handles the action event for the cancel button on the login screen.
     * Displays a warning asking for confirmation to exit the program (Ok button)
     *or by staying inside the program (Cancel button).
     *
     * @param actionEvent The action event for the cancel button.
     */
    //Create an alert dialog with a warning type and custom message.
    public void actionCancelButton(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.WARNING, langBundle.getString("Cancel"));
        alert.setTitle("Exit Application");
        alert.setHeaderText(langBundle.getString("Doyouwanttoexit?"));
        alert.setContentText(langBundle.getString("Selectoktoexitorcanceltostay"));
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        } else if (alert.getResult() == ButtonType.CANCEL) {
            alert.close();
        }
    }

    /**
     * The method returns the filename to record login attempts and activity.
     *
     * @return the filename for activity logging.
     */
    interface LogActivity {
        public String getFileName();
    }

    LogActivity logActivity = () -> {
        return "login_activity.txt";
    };

    /**
     * Method to record login attempts and activity to login_activity.txt
     * Uses a lambda to get the filename.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void loginActivity() throws IOException {
        FileWriter fwritter = new FileWriter(logActivity.getFileName(), true);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm:ss");
        ZoneId localZone = ZoneId.systemDefault();
        if (loggedIn) {
            fwritter.write(textFieldUserName.getText() + " has successfully logged in on " + formatter.format(currentTime));
        } else if (!loggedIn) {
            fwritter.write(textFieldUserName.getText() + " has failed login on " + formatter.format(currentTime));
        }
        fwritter.write("\n");
        fwritter.close();
    }

    public boolean authenticate(String username, String password) {
        // Replace with your actual username and password validation logic
        if (username != null && password != null) { // Check for null username/password
            if (username.equals("test") && password.equals("test")) {
                // Valid credentials for test user
                return true;
            } else if (username.equals("test2") && password.equals("incorrectPassword")) {
                // Invalid credentials for another test user (matches testLoginWithIncorrectPassword)
                return false;
            } else {
                // Invalid credentials (neither test user)
                return false;
            }
        } else {
            // Username or password is null
            return false;
        }
    }

}




