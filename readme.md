<a name="readme-top"></a>

<h1 align="center">WGU C195 - Scheduling Application</h1>
<h4 align="center">A scheduling application created with Java for Software 2 at WGU.</h4>

<div>
<!--Table Of Contents-->
<details>
<summary>Table Of Contents</summary>
<ol>

<details>
<summary>About</summary>

<li><a href="#The Project">The Project</a>
<ul><a href="#Built using">Built Using</a></ul>
</li>
</details>

<li><a href="#Use">Use</a>
</ol>

</details>
</div>

<!--The Project-->

## The Project
<div>
<p>This project uses an existing database. The ability to add, update,
delete appointments/customers was implemented. I no longer have access to 
the database schema because it was accessed through a virtual machine via WGU. 
</p>

<p align="left"><a href="#readme-top">[Back to top]</a></p>
</div>

## Built Using
<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/Intellij%20Idea-000?logo=intellij-idea&style=for-the-badge)
![MYSQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Windows](https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white)
</div>

<ul>
<li>IDE:IntelliJ IDEA 2024.1 [Community Edition] x64</li>
<li>JDK: 17.0.10</li>
<li>JavaFX:JAVAFX SDK 17.0.10 </li>
</ul>

<p align="left"><a href="#readme-top">[Back to top]</a></p>

<!--Use Examples-->
## Use
<p>
Login credentials:</br>
Username: test </br>
Password: test


Upon launching the program, a login screen will appear. Enter your username and password, ensuring proper capitalization, and click "Login" to continue. Alternatively, click "Cancel" to exit.</br>
Entering your username and password will trigger a login attempt. If your credentials are incorrect, you'll be notified with an alert. Otherwise, you'll see any upcoming appointments within the next 15 minutes.

<h3><b>The Main Menu:</b></br></h3>
There will be a notification for the appointment. Once the notification is delivered the user will be directed to the main menu. There should be four buttons that appear on the screen.

<h3><b>Customer<b></br></h3>
Selecting the "Customers" button will display a comprehensive customer list.
This list will be presented in a tabular format with detailed information for each contact.
A control panel positioned adjacent to the table provides functionalities for managing your customer database.
The "Update" function allows for modification of existing entries, while the "Add" function facilitates the creation of new customer records.
Finally, the "Delete" function offers the capability to remove customers from the system. To return to the main menu, simply click the "Back" button.

<h3><b>Appointment<b></br></h3>
Upon selecting the "Appointments" button, the user will be presented with a dedicated interface.
This interface defaults to a comprehensive view showcasing all existing appointments in a tabular format.
A radio button selection panel, situated prominently at the top-center of the screen, empowers users to filter the displayed appointments.
Options include "All Appointments," "Next Rolling Week," and "Next Rolling Month."
Adjacent to the appointment table, on the left-hand side, resides a control panel equipped with three core functions.
The "Update" function facilitates modifications to existing appointments.
The "Add" function grants the user the ability to create new appointments within the system.
Finally, the "Delete" function provides the capability to remove selected appointments.
To navigate back to the primary menu, the user can simply click the "Back to Menu" button.

<h3><b>Reports<b></br></h3>
Selecting the "Reports" button will navigate the user to a dedicated report list interface.
This interface utilizes a tabbed structure, presenting three distinct reports for user exploration.
Each tab corresponds to a specific report type: Appointment Totals, Contact Schedules, and Customer Totals by Country.
The Appointment Totals report leverages a tabular format to showcase various appointment types along with their corresponding aggregate counts.
Additionally, a breakdown by month is presented on the right-hand side of this tab, offering further granularity.
The Contact Schedules report employs a combobox as the primary selection mechanism.
Users can leverage this combobox to filter appointments displayed within a tableview.
By selecting a specific contact from the combobox, the tableview will dynamically update to reflect only appointments associated with the chosen contact.

<h3><b>Report A3F:</b></br></h3>
The Customer Total By Country tab will show the number of Customers per country.
To navigate back to the main menu and explore other functionalities, simply click the "Back to Menu" button.
</p>

<p align="left"><a href="#readme-top">[Back to top]</a></p>