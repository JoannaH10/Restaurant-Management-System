# Java Restaurant Management System

This is a comprehensive, console-based management application built with **Java** to simulate and handle the core operational needs of a restaurant, demonstrating strong **Object-Oriented Programming (OOP)** and file management skills.

---

## Key Features

* **Multi-Role User Hierarchy:** Implements three distinct user roles (Admin, Receptionist, Guest) with separate authentication and dedicated functionalities.
* **Data Persistence:** Utilizes **Random Access File I/O** for robust, persistent storage of all system data (Users, Tables, Menus, and Reservations).
* **Management Tools:** Provides full administrative control over Table Management (add/edit/remove) and Menu Management across five different meal categories.
* **Reporting & Analytics:** Integrated analytical features track and report the **Most Reserved Table** and **Most Ordered Meal** using Java HashMaps.
* **Reservation System:** Enables the Receptionist to create, modify, and cancel table reservations and add meals to orders.

---

## Technical Stack & OOP Architecture

| Category | Technologies & Concepts Demonstrated |
| :--- | :--- |
| **Core Language** | Java (JDK) |
| **Architecture** | **Object-Oriented Design (OOP)** principles (<br> Encapsulation, Inheritance, Abstraction) |
| **Data Handling** | **Random Access File I/O** (for persistence), <br> Java **ArrayList** and **HashMap** |
| **GUI** | **JavaFX** (Used for application interface) |
| **Classes Demonstrated** | \texttt{Admin}, \texttt{Receptionist}, \texttt{Guest}, \texttt{User}, <br> \texttt{Table}, \texttt{Menu}, \texttt{Reservation} |

---

## Local Setup (For Developers)

To run this project locally, you will need a Java Development Kit (JDK) and an IDE like Eclipse or NetBeans.

1.  **Clone the repository:**
    ```bash
    git clone [(https://github.com/JoannaH10/Restaurant-Management-System.git)]
    cd [Restaurant]
    ```

2.  **Run the Main Class:** Compile and run the main method located in the `Restaurant.java` class file.

3.  **Authentication:** The system will prompt you to log in as one of the pre-defined roles to access the management menus.
