package Main;
import Helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;

/**
 * The scheduling application
 *
 * @author Adam Wright
 *
 * JavaDoc Location: The Javadoc is located within the project directory in a folder named Javadoc.
 */

public class main extends Application {
    /**
     * Redirects to the login screen.
     *
     * @param stage The stage to set up the login screen on.
     * @throws Exception If there is an unhandled exception.
     */

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/View/Login.fxml"))));
        stage.setTitle("Scheduling Application");
        stage.setScene(new Scene(root));
        stage.show();
        stage.centerOnScreen();
    }



    /**
     * This method initiates a connection to the database, launches the program  and closes the connection.
     *
     * @param args args command line arguments.
     * @throws SQLException If there is an unhandled exception while connecting to the database.
     */

    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();

    }

}
