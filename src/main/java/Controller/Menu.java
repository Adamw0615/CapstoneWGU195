package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

/**
 * The class that controls the main menu
 *
 * @author Adam Wright
 */

public class Menu {
    /**
     * Action event for the 'Customer' button that redirects to the customer screen.
     *
     * @param actionEvent The action event triggered by pressing the 'Customer' button.
     * @throws IOException if an error occurs during the loading process.
     */

    public void customerMenu (ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Customers.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * This method is an action event handler for the 'Appointment' button.
     * It redirects the user to the Appointments screen.
     *
     * @param actionEvent The action event triggered by pressing the 'Appointment' button.
     * @throws IOException If an error occurs during the loading process.
     */

    public void apptMenu(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Appointments.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Action event for the 'Report' button that redirects to the the Reports screen.
     *
     * @param actionEvent The action event triggered by pressing the 'Report' button
     * @throws IOException if an error occurs during the loading  process.
     */

    public void reportMenu(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Reports.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Action event for the exit button on the main menu that will close the program.
     *
     * @param actionEvent The action event triggered by pressing the exit button.
     */
    public void exitMenu(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}


