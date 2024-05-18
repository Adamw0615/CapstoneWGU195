package Controller;

import DAO.AppointmentDAO;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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

/** The appointments class.
 *
 * @author Adam Wright
 * */


public class Appointments implements Initializable {
    @FXML
    private ToggleGroup apptView;
    @FXML
    private TableView<Appointment> apptTable;
    @FXML
    private TableColumn<Appointment, Integer> apptIdCol;
    @FXML
    private TableColumn<Appointment, String> apptTitleCol;
    @FXML
    private TableColumn<Appointment, String> apptDescriptionCol;
    @FXML
    private TableColumn<Appointment, String> apptLocationCol;
    @FXML
    private TableColumn<Appointment, Integer> apptContactCol;
    @FXML
    private TableColumn<Appointment, String> apptTypeCol;
    @FXML
    private TableColumn<Appointment, Timestamp> apptDateCol;
    @FXML
    private TableColumn<Appointment, Timestamp> apptEndDateCol;
    @FXML
    private TableColumn<Appointment, Integer> apptCustIdCol;
    @FXML
    private TableColumn<Appointment, Integer> apptUserIdCol;
    @FXML
    private TextField searchFilter;

    ObservableList<Appointment> ApptList = FXCollections.observableArrayList();
    ObservableList<Appointment> AllApptSearch = FXCollections.observableArrayList();
    ObservableList<Appointment> WeeklyApptSearch = FXCollections.observableArrayList();
    ObservableList<Appointment> MonthlyApptSearch = FXCollections.observableArrayList();

    /**
     *The action event for the delete button. If no appointment is selected, the user will be presented with an error,
     * and the user will be informed.
     *
     * @param actionEvent The event for delete button
     */
    public void actionApptDelete(ActionEvent actionEvent) {
        Appointment selectedAppointment = apptTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Helper.ErrorMsg.getError(12);
        } else {
            Alert confirmRemoval = new Alert(Alert.AlertType.WARNING);
            confirmRemoval.setTitle("Alert");
            confirmRemoval.setContentText("Would you like to remove the selected appointment?");
            confirmRemoval.getButtonTypes().clear();
            confirmRemoval.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
            confirmRemoval.showAndWait();
            if (confirmRemoval.getResult() == ButtonType.OK) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Alert");
                confirmation.setContentText("Appointment ID# " + apptTable.getSelectionModel().getSelectedItem().getAppointmentId() + " for " + apptTable.getSelectionModel().getSelectedItem().getAppointmentType() + " has been cancelled.");
                confirmation.getButtonTypes().clear();
                confirmation.getButtonTypes().addAll(ButtonType.OK);
                confirmation.showAndWait();

                AppointmentDAO.deleteAppointment(apptTable.getSelectionModel().getSelectedItem().getAppointmentId());
                ApptList = AppointmentDAO.getAppointmentList();
                apptTable.setItems(ApptList);
                apptTable.refresh();
                searchField();

            } else if (confirmRemoval.getResult() == ButtonType.CANCEL) {
                confirmRemoval.close();
            }
        }
    }

    /**
     * Action event for add button on appointment screen. When pressed - the user will be redirected to the add appointments
     * screen.
     *
     * @param actionEvent event for add button
     * @throws IOException addresses unhandled exception
     */
    public void actionApptAdd(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/addAppointments.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        searchField();
    }

    /**
     * The action evebt for the update button on the appointment screen.
     * If no appointment is selectedm the user will be presented with an error message.
     * Once an appointment is selected, the user will be redirected to the modify appointment screen.
     * @param actionEvent The event for update button
     * @throws IOException  If there is an unhandled exception
     * @throws SQLException If there is an unhandled SQL exception.
     */

    public void actionApptUpdate(ActionEvent actionEvent) throws IOException, SQLException {
        if (apptTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/modifyAppointments.fxml"));
            loader.load();
            modifyAppointments MCController = loader.getController();
            MCController.getAppointmentInfo(apptTable.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
            searchField();
        } else {
            Helper.ErrorMsg.getError(12);
        }
    }

    /**
     * Initializes the appointment table data and sets up the column for display.
     *
     * @param url The URL of the resource
     * @param resourceBundle The resouce bundle for the application.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set the items in the table to the list of appointments.
        apptTable.setItems(AppointmentDAO.getAppointmentList());

        //Set up the cell value factories for each column.
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("appointmentContactName"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        apptDateCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        apptEndDateCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        apptCustIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentCustomerId"));
        apptUserIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentUserId"));
        searchField();
    }

    /**
     * The action event for button to return the user back to the main menu
     * This method loads the Menu.fxml file and displays it as the main menu.
     *
     * @param actionEvent The action event triggere by the bckToMenu button.
     * @throws IOException in case of unhandled exceptions during file loading.
     */
    public void bckTomenu(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Menu.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Sets the items in the appointment table to all appointments.
     *
     * @param actionEvent The event that triggers the action.
     *
     */
    public void allAppts(ActionEvent actionEvent) {
        //Set the items in the appointment table to all appointments.
        apptTable.setItems(AppointmentDAO.getAppointmentList());
    }

    /**
     * Set the items in the appointment table to the list of appointments for the current month.
     *
     * @param actionEvent The event that triggers the action.
     */
    public void monthlyAppts(ActionEvent actionEvent) {
        searchFilter.setText("");
        apptTable.setItems(AppointmentDAO.getMonthlyAppointments());
        apptTable.setPlaceholder(new Label("Currently, no appointments exist within the next month."));
    }

    /**
     * Sets the items in the appointment table to the list of appointments for the next week.
     * if there aren't appointments, sets a placeholder indicating that.
     *
     * @param actionEvent The event that triggers the action.
     */

    public void weeklyAppts(ActionEvent actionEvent) {
        searchFilter.setText("");
        apptTable.setItems(AppointmentDAO.getWeeklyAppointments());
        apptTable.setPlaceholder(new Label("Currently, no appointments exist within the next week."));

    }
    public void searchField (){
        AllApptSearch.addAll(AppointmentDAO.getAppointmentList());
        FilteredList<Appointment> datafilter = new FilteredList<>(AllApptSearch, b -> true);
        searchFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            datafilter.setPredicate(appointment -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filterLowerCase = newValue.toLowerCase();

                if (appointment.getAppointmentTitle().toLowerCase().contains(filterLowerCase)) {
                    return true;
                } else if (appointment.getAppointmentDescription().toLowerCase().contains(filterLowerCase)) {
                    return true;
                } else if (appointment.getAppointmentLocation().toLowerCase().contains(filterLowerCase)) {
                    return true;
                } else if (appointment.getAppointmentType().toLowerCase().contains(filterLowerCase)) {
                    return true;
                } else if (String.valueOf(appointment.getAppointmentId()).contains(filterLowerCase)) {
                    return true;
                } else if (String.valueOf(appointment.getAppointmentUserId()).contains(filterLowerCase))
                    return true;
                else
                    return false;
            });
        });

        SortedList<Appointment> dataSorted = new SortedList<>(datafilter);
        dataSorted.comparatorProperty().bind(apptTable.comparatorProperty());
        apptTable.setItems(dataSorted);
        }
    }




