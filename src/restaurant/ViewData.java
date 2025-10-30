package restaurant;

import javafx.geometry.Pos; // Import for positioning components
import javafx.scene.Scene; // Import for creating the scene
import javafx.scene.control.Button; // Import for creating buttons
import javafx.scene.control.Label; // Import for creating labels
import javafx.scene.layout.*; // Import for different layout managers (VBox, BorderPane, etc.)
import javafx.stage.Stage; // Import for managing the window

import java.io.IOException; // Import for handling I/O exceptions
import java.util.ArrayList; // Import for working with lists

public class ViewData {

    // Method to create and return the "View Data" scene
    public Scene createViewDataScene(Stage primaryStage) {
        BorderPane root = new BorderPane(); // Create a BorderPane as the root layout

        // Title
        Label title = new Label("View Data"); // Create a label for the title
        title.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-padding: 10px;"); // Style the title label
        root.setTop(title); // Set the title at the top of the BorderPane
        BorderPane.setAlignment(title, Pos.CENTER); // Center-align the title

        // Sidebar for navigation
        VBox sidebar = new VBox(10); // Create a vertical box (VBox) for the sidebar with 10px spacing
        sidebar.setStyle("-fx-background-color: #333; -fx-padding: 10px;"); // Style the sidebar with background color and padding
        sidebar.setAlignment(Pos.TOP_CENTER); // Align the buttons at the top of the sidebar

        // Create buttons for various options
        Button usersButton = new Button("View Users");
        Button tablesButton = new Button("View Tables");
        Button menuButton = new Button("View Menu");
        Button reservationsButton = new Button("View Reservations");
        Button backButton = new Button("Back");

        // Style buttons for uniform appearance
        for (Button button : new Button[]{usersButton, tablesButton, menuButton, reservationsButton, backButton}) {
            button.setMaxWidth(Double.MAX_VALUE); // Make buttons stretch to the full width of the sidebar
            button.setStyle("-fx-background-color: #555; -fx-text-fill: white; -fx-font-size: 14px;"); // Style the buttons
        }

        // Add buttons to sidebar
        sidebar.getChildren().addAll(usersButton, tablesButton, menuButton, reservationsButton, backButton);

        // Center content area (where the data will be displayed)
        VBox contentArea = new VBox(); // Create a vertical box for the content area
        contentArea.setStyle("-fx-padding: 10px; -fx-background-color: black;"); // Style the content area with padding and black background
        contentArea.setAlignment(Pos.TOP_LEFT); // Align the content to the top left of the VBox

        // Set the sidebar on the left and content area in the center of the BorderPane
        root.setLeft(sidebar);
        root.setCenter(contentArea);

        // Button actions: set what happens when each button is clicked
        usersButton.setOnAction(e -> contentArea.getChildren().setAll(createUsersView())); // Load the Users view when clicked
        tablesButton.setOnAction(e -> contentArea.getChildren().setAll(createTablesView())); // Load the Tables view when clicked
        // menuButton.setOnAction(e -> contentArea.getChildren().setAll(createMenuView())); // Load the Menu view when clicked (currently commented out)
        reservationsButton.setOnAction(e -> contentArea.getChildren().setAll(createReservationsView())); // Load the Reservations view when clicked
        backButton.setOnAction(e -> {
            // Go back to the Admin Dashboard when the back button is clicked
            AdminDashboardScene adminDashboard = new AdminDashboardScene(); 
            primaryStage.setScene(adminDashboard.createAdminDashboardScene(primaryStage)); 
        });

        Scene scene = new Scene(root, 800, 800); // Create a new scene with the BorderPane root and set the window size to 800x600
        root.setStyle("-fx-background-color: black;"); // Set the background color of the root to black
        return scene; // Return the created scene
    }

    // Method to create the Users view
    private VBox createUsersView() {
        VBox usersBox = new VBox(10); // Create a vertical box for displaying users with 10px spacing
        usersBox.setAlignment(Pos.TOP_LEFT); // Align the users list to the top left of the VBox
        usersBox.setStyle("-fx-padding: 10px;"); // Set padding around the users list

        try {
            ArrayList<User> users = User.loadUsers(); // Load the list of users from the User class
            if (users.isEmpty()) {
                usersBox.getChildren().add(new Label("No users found.")); // If no users, display a message
            } else {
                // Loop through each user and display their details
                for (User user : users) {
                    Label userLabel = new Label(
                            "Username: " + user.getUsername() +
                                    "\nName: " + user.getName() +
                                    "\nRole: " + user.getRole() +
                                    "\nPhone: " + user.getPhone()
                    );
                    userLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;"); // Style the user label text
                    usersBox.getChildren().add(userLabel); // Add the user label to the VBox
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            usersBox.getChildren().add(new Label("Error loading users: " + e.getMessage())); // Display an error message if loading fails
        }
        return usersBox; // Return the VBox containing the users list
    }

    // Method to create the Tables view
    private VBox createTablesView() {
        VBox tablesBox = new VBox(10); // Create a vertical box for displaying tables with 10px spacing
        tablesBox.setAlignment(Pos.TOP_LEFT); // Align the tables list to the top left of the VBox
        tablesBox.setStyle("-fx-padding: 10px;"); // Set padding around the tables list

        ArrayList<Table> tables = Table.loadTablesFromFile(); // Load the list of tables from the Table class
        if (tables.isEmpty()) {
            tablesBox.getChildren().add(new Label("No tables found.")); // If no tables, display a message
        } else {
            // Loop through each table and display its details
            for (Table table : tables) {
                Label tableLabel = new Label(
                        "Table ID: " + table.getTableId() +
                                "\nCategory: " + table.getCategory() +
                                "\nCapacity: " + table.getCapacity() +
                                "\nReserved: " + (table.isReserved() ? "Yes" : "No")
                );
                tableLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;"); // Style the table label text
                tablesBox.getChildren().add(tableLabel); // Add the table label to the VBox
            }
        }
        return tablesBox; // Return the VBox containing the tables list
    }

    // Method to create the Reservations view
    private VBox createReservationsView() {
        VBox reservationsBox = new VBox(10); // Create a vertical box for displaying reservations with 10px spacing
        reservationsBox.setAlignment(Pos.TOP_LEFT); // Align the reservations list to the top left of the VBox
        reservationsBox.setStyle("-fx-padding: 10px;"); // Set padding around the reservations list

        Receptionist receptionist = new Receptionist(); // Create a Receptionist object
        receptionist.loadReservations(); // Load the reservations from the Receptionist class
        ArrayList<Reservation> reservations = receptionist.getReservations(); // Get the list of reservations

        if (reservations.isEmpty()) {
            reservationsBox.getChildren().add(new Label("No reservations found.")); // If no reservations, display a message
        } else {
            // Loop through each reservation and display its details
            for (Reservation reservation : reservations) {
                Label reservationLabel = new Label(
                        "Reservation ID: " + reservation.getResID() +
                                "\nGuest Name: " + reservation.getGuestName() +
                                "\nPhone: " + reservation.getPhone() +
                                "\nDate and Time: " + reservation.getDateTime() +
                                "\nTable ID: " + reservation.getTableID() +
                                "\nTotal Payment: $" + reservation.getPayment()
                );
                reservationLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;"); // Style the reservation label text

                // Create a VBox to display ordered meals for the reservation
                VBox mealsBox = new VBox(5); // Create a VBox for displaying meals with 5px spacing
                for (Meal meal : reservation.getOrderedMeals()) { // Loop through each ordered meal
                    Label mealLabel = new Label(
                            "  Meal Name: " + meal.getName() +
                                    "\n  Category: " + meal.getCategory() +
                                    "\n  Price: $" + meal.getPrice()
                    );
                    mealLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;"); // Style the meal label text
                    mealsBox.getChildren().add(mealLabel); // Add the meal label to the VBox
                }
                reservationsBox.getChildren().addAll(reservationLabel, mealsBox); // Add the reservation label and meals to the reservations VBox
            }
        }
        return reservationsBox; // Return the VBox containing the reservations list
    }
    
}
