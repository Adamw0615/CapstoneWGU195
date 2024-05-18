Title: C195 - Scheduling Application
Purpose: A scheduling application created with Java for Software 2 at WGU.


Author: Adam Wright
Student ID# 003355986
Application Version 1.0
Date:5/4/2024

IDE: IntelliJ IDEA 2024.1 (Community Edition) x64
JDK version: Java 17.0.10
JavaFX version: JAVAFX.SDK: 17.0.10



Login credentials:
Username: test
Password: test

Upon launching the program, a login screen will appear. Enter your username and password, ensuring proper capitalization, and click "Login" to continue. Alternatively, click "Cancel" to exit.
Entering your username and password will trigger a login attempt. If your credentials are incorrect, you'll be notified with an alert. Otherwise, you'll see any upcoming appointments within the next 15 minutes.

The Main Menu:
There will be a notification for the appointment.
Once the notification is delivered the user will be directed to the main menu. There should be four buttons that appear on the screen.

Customer:
Selecting the "Customers" button will display a comprehensive customer list.
This list will be presented in a tabular format with detailed information for each contact.
A control panel positioned adjacent to the table provides functionalities for managing your customer database.
The "Update" function allows for modification of existing entries, while the "Add" function facilitates the creation of new customer records.
Finally, the "Delete" function offers the capability to remove customers from the system. To return to the main menu, simply click the "Back" button.

Appointment:
Upon selecting the "Appointments" button, the user will be presented with a dedicated interface. This interface defaults to a comprehensive view showcasing all existing appointments in a tabular format.
A radio button selection panel, situated prominently at the top-center of the screen, empowers users to filter the displayed appointments. Options include "All Appointments," "Next Rolling Week," and "Next Rolling Month."
Adjacent to the appointment table, on the left-hand side, resides a control panel equipped with three core functions.The "Update" function facilitates modifications to existing appointments.
 The "Add" function grants the user the ability to create new appointments within the system.  Finally, the "Delete" function provides the capability to remove selected appointments.
 To navigate back to the primary menu, the user can simply click the "Back to Menu" button.

Reports:
Selecting the "Reports" button will navigate the user to a dedicated report list interface.
This interface utilizes a tabbed structure, presenting three distinct reports for user exploration.
 Each tab corresponds to a specific report type: Appointment Totals, Contact Schedules, and Customer Totals by Country.
 The Appointment Totals report leverages a tabular format to showcase various appointment types along with their corresponding aggregate counts.
 Additionally, a breakdown by month is presented on the right-hand side of this tab, offering further granularity. The Contact Schedules report employs a combobox as the primary selection mechanism.
 Users can leverage this combobox to filter appointments displayed within a tableview. By selecting a specific contact from the combobox, the tableview will dynamically update to reflect only appointments associated with the chosen contact.

Report A3F:
The Customer Total By Country tab will show the number of Customers per country. To navigate back to the main menu and explore other functionalities, simply click the "Back to Menu" button.

MySQL Connector Driver Version: mysql-connector-java-8.0.25
