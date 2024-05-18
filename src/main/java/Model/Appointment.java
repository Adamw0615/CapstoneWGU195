package Model;

/**
 * The Model class for Appointment
 *
 * @author Adam Wright
 */

import DAO.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


public class Appointment {

    private int appointmentId  ;
    private String appointmentTitle;
    private String appointmentDescription;
    private int appointmentContact;
    private String appointmentType;
    private LocalDateTime appointmentStart;
    public long appointmentStart1;
    private LocalDateTime appointmentEnd;
    private int appointmentCustomerId;
    private int appointmentUserId;
    private String appointmentLocation;
    private int appointmentTypeTotal;
    private String appointmentContactName;

    /**
     * The Constructor is overloaded for Appointments to fetch data from the database.
     *
     * @param appointmentId         id of the appointment
     * @param appointmentTitle      Title of the appointment
     * @param appointmentDescription The description of the appointment
     * @param appointmentContact    ID of the associated appointment contact.
     * @param appointmentType       The type of the appointment
     * @param appointmentStart       Start date/Date time of the appointment
     * @param appointmentEnd         End date/Date time of the appointment
     * @param appointmentCustomerId  ID of the associated customer for the appointment
     * @param appointmentUserId      ID of the associated user for the appointment
     * @param appointmentLocation   The location of the appointment.
     */

    public Appointment(int appointmentId, String appointmentTitle, String appointmentDescription, int appointmentContact,
                       String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int appointmentCustomerId, int appointmentUserId, String appointmentLocation) {
        this.appointmentId = appointmentId;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentContact = appointmentContact;
        this.appointmentType = appointmentType;
        this.appointmentStart = appointmentStart;
        this.appointmentEnd = appointmentEnd;
        this.appointmentCustomerId = appointmentCustomerId;
        this.appointmentUserId = appointmentUserId;
        this.appointmentLocation = appointmentLocation;

    }

    /**
     * This constructor initializes the appointment object with the given appointment type and total for each type.
     * It retrieves the appointment types and totals from the database and displays them on the report screen.
     *
     * @param appointmentType    The type of  appointment
     * @param appointmentTypeTotal The total number of appointments of that type
     */
    public Appointment(String appointmentType, int appointmentTypeTotal) {
        this.appointmentType = appointmentType;
        this.appointmentTypeTotal = appointmentTypeTotal;
    }

    /**
     *The constructor is overloaded to create an instance of the Appointment class with the provided parameters. It
     * fetches information from the database and initializes the appointment object with the specified attributes.
     *
     * @param appointmentId          The id of the appointment
     * @param appointmentTitle       Title of the appointment
     * @param appointmentDescription The description of the appointment
     * @param appointmentContact     Associated appointment contact
     * @param appointmentContactName Associated appointment contact name
     * @param appointmentType        The type of the appointment
     * @param appointmentStart       The appointment start date/time
     * @param appointmentEnd         The appointment end date/time
     * @param appointmentCustomerId  The associated customer id
     * @param appointmentUserId      The associated user id
     * @param appointmentLocation    The location of the appointment.
     */
    public Appointment(int appointmentId, String appointmentTitle, String appointmentDescription, int appointmentContact, String appointmentContactName, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int appointmentCustomerId, int appointmentUserId, String appointmentLocation) {
        this.appointmentId = appointmentId;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentContact = appointmentContact;
        this.appointmentContactName = appointmentContactName;
        this.appointmentType = appointmentType;
        this.appointmentStart = appointmentStart;
        this.appointmentEnd = appointmentEnd;
        this.appointmentCustomerId = appointmentCustomerId;
        this.appointmentUserId = appointmentUserId;
        this.appointmentLocation = appointmentLocation;
    }

    public Appointment() {

    }

    /**
     * The getter for appointment ID.
     *
     * @return The appointment ID.
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * The setter for appointment ID.
     *
     * @param apptId The appointment id to set.
     */
    public void setAppointmentId(int apptId) {
        this.appointmentId = apptId;
    }

    /**
     * The getter for appointment title
     *
     * @return The title of the appointment
     */
    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    /**
     * The setter for appointment title
     *
     * @param appointmentTitle The new title for the appointment.
     */
    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    /**
     * The getter for appointment description
     *
     * @return The description of the appointment.
     */
    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    /**
     * The setter for appointment description
     *
     * @param appointmentDescription The new description for the appointment.
     */
    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    /**
     * The getter for appointment contact
     *
     * @return The contact ID
     */
    public int getAppointmentContact() {
        return appointmentContact;
    }

    /**
     * The setter for appointment contact
     *
     * @param appointmentContact The ID of the appointment contact.
     */
    public void setAppointmentContact(int appointmentContact) {
        this.appointmentContact = appointmentContact;
    }

    /**Returns the name of the appointment contact
     *
     * @return the name of the appointment contact.
     */
    public String getAppointmentContactName() {
        return appointmentContactName;
    }

    /**
     * Sets the name of the appointment contact
     *
     * @param appointmentContactName The name of the appointment contact.
     */
    public void setAppointmentContactName(String appointmentContactName) {
        this.appointmentContactName = appointmentContactName;
    }

    /**
     * The getter for the appointment type
     *
     * @return The type of the appointment as a string.
     */

    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     * The setter for the appointment type
     *
     * @param appointmentType The type of the appointment.
     */

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    /**
     * The getter method for retrieving the start date and time of the appointment.
     *
     * @return The LocalDateTime representing the start date and time of the appointment.
     */

    public LocalDateTime getAppointmentStart() {
        return appointmentStart;
    }

    /**
     * The setter for appointment start date/time
     *
     * @param appointmentStart The new start date/time for the appointment.
     */
    public void setAppointmentStart(LocalDateTime appointmentStart) {
        this.appointmentStart = appointmentStart;
    }

    /**
     * The getter for appointment end date/time
     *
     * @return the appointment end date/time
     */
    public LocalDateTime getAppointmentEnd() {
        return appointmentEnd;
    }

    /**
     * The setter for appointment end date/time
     *
     * @param appointmentEnd The new end date/time for the appointment.
     */
    public void setAppointmentEnd(LocalDateTime appointmentEnd) {
        this.appointmentEnd = appointmentEnd;
    }

    /**
     * The getter for appointment customer Id
     *
     * @return the appointment customer Id.
     */
    public int getAppointmentCustomerId() {
        return appointmentCustomerId;
    }

    /**
     * The setter for the customer ID.
     *
     * @param appointmentCustomerId The customer ID to set.
     */
    public void setAppointmentCustomerId(int appointmentCustomerId) {
        this.appointmentCustomerId = appointmentCustomerId;
    }

    /**
     * The getter for the appointment user ID.
     *
     * @return The user ID associated with the appointment.
     */
    public int getAppointmentUserId() {
        return appointmentUserId;
    }

    /**
     * The setter for the appointment user ID.
     *
     * @param appointmentUserId The appointment user ID to set.
     */
    public void setAppointmentUserId(int appointmentUserId) {
        this.appointmentUserId = appointmentUserId;
    }

    /**
     * The getter for appointment location
     *
     * @return The location of the appointment.
     */
    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    /**
     * The setter for appointment location
     *
     * @param appointmentLocation The location of the appointment.
     */
    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

    /**
     * The getter for appointment type total.
     *
     * @return The total count of appointmet types.
     */

    public int getAppointmentTypeTotal() {
        return appointmentTypeTotal;
    }

    /**
     * The setter for appointment type total
     *
     * @param appointmentTypeTotal The total count of appointment types.
     */
    public void setAppointmentTypeTotal(int appointmentTypeTotal) {
        this.appointmentTypeTotal = appointmentTypeTotal;
    }


    /**
     * Checks if an appointment for a selected customer overlaps with any existing appointments for that customer.
     *
     * @param customerId      The ID of the customer
     * @param appointmentStart The start date/time of the appointment
     * @param appointmentEnd   The end date/time of the appointment
     * @return true if there is an overlap, false if there is no overlap.
     */

    public static boolean overlapCheck(int customerId, LocalDateTime appointmentStart, LocalDateTime appointmentEnd) {
        //Get list of appointments
        ObservableList<Appointment> appointmentList = AppointmentDAO.getAppointmentList();
        LocalDateTime checkApptStart;
        LocalDateTime checkApptEnd;
        //Iterate over appointments
        for (Appointment a : appointmentList) {
            //Get the start and end times of current appointment.
            checkApptStart = a.getAppointmentStart();
            checkApptEnd = a.getAppointmentEnd();
            //Check if the current appointment is for a different customer.
            if (customerId != a.getAppointmentCustomerId()) {
                continue;
                //Check for overlap with the start time.
            } else if (checkApptStart.isEqual(appointmentStart) || checkApptEnd.isEqual(appointmentEnd)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("ERROR: New appointments cannot start or end at the same time as existing customer appointments");
                alert.showAndWait();
                return true;
            } else if (appointmentStart.isAfter(checkApptStart) && (appointmentStart.isBefore(checkApptEnd))) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("ERROR: New appointments cannot start or end at the same time as existing customer appointments");
                alert.showAndWait();
                return true;
            } else if (appointmentEnd.isAfter(checkApptStart) && appointmentEnd.isBefore(checkApptEnd)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("ERROR: New appointment end time cannot fall within the duration of an existing customer appointment");
                alert.showAndWait();
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the given appointment falls within the business hours in the Eastern time zone.
     *
     * @param appointmentStart The start time of the appointment.
     * @param appointmentEnd   The end time of the appointment.
     * @return true if the appointment falls outside of business hours, false otherwise.
     */

    public static boolean businessHours(LocalDateTime appointmentStart, LocalDateTime appointmentEnd) {
        //Get the current time zone and Eastern time zone.
        ZoneId localZone = ZoneId.systemDefault();
        ZoneId estZone = ZoneId.of("America/New_York");

        //Convert appointment start and end times to Eastern Timezone.
        LocalDateTime appStartEST = appointmentStart.atZone(localZone).withZoneSameInstant(estZone).toLocalDateTime();
        LocalDateTime appEndEST = appointmentEnd.atZone(localZone).withZoneSameInstant(estZone).toLocalDateTime();
        //Set the business hours in the Eastern time zone.
        LocalDateTime businessStartEST = appStartEST.withHour(8).withMinute(0);
        LocalDateTime businessEndEST = appEndEST.withHour(22).withMinute(0);

        //Check if appointment falls outside of business hours.
        if (appStartEST.isBefore(businessStartEST) || appEndEST.isAfter(businessEndEST)) {
            //Get the local start and end times.
            LocalTime localStart = Appointment.localStart();
            LocalTime localEnd = Appointment.localEnd();
            //Show an error alert with local start and end times.
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("");
            alert1.setHeaderText("Not within business hours");
            alert1.setContentText(String.format("Appointment is not within business hours. 8:00AM to 10:00PM EST\n" +
                    "Please schedule between " + localStart.format(DateTimeFormatter.ofPattern("hh:mm")) + " - " + localEnd.format(DateTimeFormatter.ofPattern("hh:mm")) + "PM local time."));
            alert1.getDialogPane().setMinHeight(250);
            alert1.getDialogPane().setMinWidth(400);
            alert1.showAndWait();
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method calculates the local start time based on the Eastern time zone
     * for users to schedyle appointmenys within valid local time.
     *
     * @return businessStartLocal - The local start time witin business hours.
     */
    public static LocalTime localStart() {
        //Define the opening business time as 8:00AM.
        LocalTime openingBusinessTime = LocalTime.of(8, 0);
        //Define Eastern time zone.
        ZoneId easternZone = ZoneId.of("America/New_York");
        //Get the default time zone of the system.
        ZoneId localZone = ZoneId.systemDefault();

        //Create a LocalDateTime object for the current date and the opening business time.
        LocalDateTime businessEastern = LocalDateTime.of(LocalDate.now(), openingBusinessTime);
        LocalDateTime businessLocal = businessEastern.atZone(easternZone).withZoneSameInstant(localZone).toLocalDateTime();
        //Convert the Eastern Time zone to the local time zone.
        LocalTime businessStartLocal = businessLocal.toLocalTime();

        return businessStartLocal;
    }

    /**
     * This method calculates the local end time based on the eastern time zone.
     *for users to schedule appointments within valid local time.
     *
     * @return The local end time within business hours.
     */

    public static LocalTime localEnd() {
        //Define the closing business time as 10:00PM.
        LocalTime closingBusinessTime = LocalTime.of(22, 0);
        //Define Eastern time zone.
        ZoneId easternZone = ZoneId.of("America/New_York");
        //Get the default time zone of the system.
        ZoneId localZone = ZoneId.systemDefault();
        //Create a LocalDateTime object for the current date and the closing business time.
        LocalDateTime businessEndDT = LocalDateTime.of(LocalDate.now(), closingBusinessTime);
        //Convert the Eastern Time zone to the local time zone.
        LocalDateTime businessLocalDT = businessEndDT.atZone(easternZone).withZoneSameInstant(localZone).toLocalDateTime();
        //Get the local end time within business hours.
        LocalTime businessEndLocal = businessLocalDT.toLocalTime();

        return businessEndLocal;
    }

    /**
     * The method that generates and adds the times for an appointment with interval of 30 minutes to the observable list.
     *
     * @return appointmentTimeList - an observable list of LocalTime objects.
     */
    public static ObservableList<LocalTime> getTimes() {
        //Create an observable list to hold the appointment times.
        ObservableList<LocalTime> appointmentTimeList = FXCollections.observableArrayList();
        LocalTime start = LocalTime.of(1, 00);
        LocalTime end = LocalTime.MIDNIGHT.minusHours(1);

        while (start.isBefore(end.plusSeconds(2))) {

            appointmentTimeList.add(start);
            start = start.plusMinutes(30);

        }
        return appointmentTimeList;
    }


    /**
     * The action event responsible for returning to the main appointments screen.
     *
     * @param actionEvent The evemt for returning to the appointment screen.
     *
     * @throws IOException Responsible for addressing unhandled exceptions.
     */
    public static void backToAppointments(ActionEvent actionEvent) throws IOException {
        new FXMLLoader();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(Appointment.class.getResource("/View/Appointments.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
