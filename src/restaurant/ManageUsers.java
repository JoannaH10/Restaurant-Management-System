package restaurant;

import javafx.geometry.Pos; // Import for positioning components
import javafx.scene.Scene; // Import for creating scenes
import javafx.scene.control.*; // Import for controls like buttons, labels, etc.
import javafx.scene.layout.*; // Import for layout components like VBox, BorderPane, etc.
import javafx.stage.Stage; // Import for managing the window (stage)

import java.io.IOException; // Import for handling I/O exceptions
import java.util.ArrayList; // Import for working with ArrayLists

public class ManageUsers {

    // Create the Manage Users scene
    public Scene createManageUsersScene(Stage primaryStage) {
        BorderPane root = new BorderPane(); // Create a BorderPane layout for the root of the scene

        // Create the sidebar for user management options
        VBox sidebar = createUserManagementSidebar(primaryStage);

        // Add sidebar to the left of the screen
        root.setLeft(sidebar);

        // Create the main content area (buttons and input fields)
        VBox mainContent = new VBox(20); // Create a vertical box layout with 20px spacing
        mainContent.setAlignment(Pos.CENTER); // Center-align the content in the main area

        // Placeholder label, will be replaced by button-driven content
        Label placeholderLabel = new Label("Select an action from the sidebar");
        mainContent.getChildren().add(placeholderLabel); // Add placeholder label to main content

        // Set initial content in center to be a placeholder
        root.setCenter(mainContent);

        // Set the new scene for manage users with a size of 800x400
        Scene manageUsersScene = new Scene(root, 800, 800);
        return manageUsersScene; // Return the newly created scene
    }

    // Create the sidebar for Manage Users (Add, Edit, Remove, Back)
    private VBox createUserManagementSidebar(Stage primaryStage) {
        VBox sidebar = new VBox(20); // Create a vertical box with 20px spacing for sidebar
        sidebar.setAlignment(Pos.TOP_CENTER); // Align sidebar content to the top center
        sidebar.setStyle("-fx-background-color: #333;"); // Set dark background color for sidebar
        sidebar.setPrefWidth(200);  // Set fixed width for sidebar

        // Add "Manage Users" label at the top of the sidebar
        Label dashboardTitle = new Label("Manage Users");
        dashboardTitle.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px;");

        // Create buttons for each action in the sidebar
        Button addUserButton = createUserManagementButton("Add User", primaryStage);
        Button editUserButton = createUserManagementButton("Edit User", primaryStage);
        Button removeUserButton = createUserManagementButton("Remove User", primaryStage);
        Button backButton = createUserManagementButton("Back", primaryStage);

        // Add title and buttons to the sidebar
        sidebar.getChildren().addAll(
                dashboardTitle,
                addUserButton,
                editUserButton,
                removeUserButton,
                backButton
        );

        return sidebar; // Return the sidebar containing the buttons and title
    }

    // Helper method to create a styled button for the user management sidebar
    private Button createUserManagementButton(String buttonText, Stage primaryStage) {
        Button button = new Button(buttonText); // Create a new button with the provided text
        button.setStyle("-fx-background-color: #555; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px; -fx-pref-width: 180px;"); // Style the button

        // Set actions based on button click
        button.setOnAction(e -> {
            if (buttonText.equals("Add User")) {
                showUserForm(primaryStage, "Add User"); // Show the form for adding a user
            } else if (buttonText.equals("Edit User")) {
                showUserForm(primaryStage, "Edit User"); // Show the form for editing a user
            } else if (buttonText.equals("Remove User")) {
                showUserForm(primaryStage, "Remove User"); // Show the form for removing a user
            } else if (buttonText.equals("Back")) {
                AdminDashboardScene adminDashboard = new AdminDashboardScene();
                primaryStage.setScene(adminDashboard.createAdminDashboardScene(primaryStage)); // Go back to admin dashboard
            }
        });

        return button; // Return the created button
    }

    // Show the user form (Add, Edit, Remove)
    private void showUserForm(Stage primaryStage, String action) {
        VBox formContent = new VBox(20); // Create a vertical box for the form with 20px spacing
        formContent.setAlignment(Pos.CENTER); // Center-align the form content
        formContent.setStyle("-fx-background-color: black;"); // Set background color for the form

        // Form fields for user management
        TextField usernameField = new TextField(); // Create a text field for the username
        usernameField.setPromptText("Username"); // Set the prompt text for the username field

        TextField roleField = new TextField(); // Create a text field for the role
        roleField.setPromptText("Role (admin, guest, receptionist)"); // Set the prompt text for the role field

        TextField passwordField = new TextField(); // Create a text field for the password
        passwordField.setPromptText("Password"); // Set the prompt text for the password field

        TextField nameField = new TextField(); // Create a text field for the name
        nameField.setPromptText("Name"); // Set the prompt text for the name field

        TextField phoneField = new TextField(); // Create a text field for the phone number
        phoneField.setPromptText("Phone"); // Set the prompt text for the phone number field

        // Create buttons
        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");
        Button backButton = new Button("Back");

        // Style the input fields and buttons
        styleButton(submitButton); // Style the submit button
        styleButton(cancelButton); // Style the cancel button
        styleButton(backButton); // Style the back button

        // Submit action
        submitButton.setOnAction(e -> {
            String username = usernameField.getText(); // Get the username from the input field
            String role = roleField.getText(); // Get the role from the input field
            String password = passwordField.getText(); // Get the password from the input field
            String name = nameField.getText(); // Get the name from the input field
            String phone = phoneField.getText(); // Get the phone number from the input field

            switch (action) {
                case "Add User":
                    manageUsers(1, username, role, password, name, phone); // Add a user
                    showAlert("Success", "User added successfully!"); // Show success alert
                    break;
                case "Edit User":
                    manageUsers(2, username, role, password, name, phone); // Edit a user
                    showAlert("Success", "User edited successfully!"); // Show success alert
                    break;
                case "Remove User":
                    manageUsers(3, username, null, null, null, null); // Remove a user
                    showAlert("Success", "User removed successfully!"); // Show success alert
                    break;
            }
        });

        // Cancel action
        cancelButton.setOnAction(e -> {
            // Clear all fields when cancel is pressed
            usernameField.clear();
            roleField.clear();
            passwordField.clear();
            nameField.clear();
            phoneField.clear();
        });

        // Back action: Go back to the Manage Users Scene
        backButton.setOnAction(e -> {
            primaryStage.setScene(createManageUsersScene(primaryStage)); // Return to Manage Users scene
        });

        // Add the form fields and buttons to the layout
        HBox buttonsBox = new HBox(20, submitButton, cancelButton, backButton); // Create a horizontal box for the buttons
        buttonsBox.setAlignment(Pos.CENTER); // Center-align the buttons

        formContent.getChildren().addAll(
                usernameField, roleField, passwordField, nameField, phoneField, buttonsBox // Add form fields and buttons to form content
        );

        // Set the new content to the root's center area
        primaryStage.getScene().setRoot(formContent);
    }

    // Helper method to show an Alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Create an information alert
        alert.setTitle(title); // Set the title of the alert
        alert.setHeaderText(null); // Set no header for the alert
        alert.setContentText(message); // Set the content message of the alert
        alert.showAndWait(); // Show the alert and wait for the user to close it
    }

    // Method to handle user management actions (Add, Edit, Remove)
    private void manageUsers(int action, String username, String role, String password, String name, String phone) {
        ArrayList<User> users; // Declare a list to hold users
        try {
            users = User.loadUsers(); // Load the list of users from file
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading users: " + e.getMessage()); // Handle loading errors
            return;
        }

        switch (action) {
            case 1: // Add User
                switch (role.toLowerCase()) {
                    case "admin":
                        users.add(new Admin(username, password, name, role, phone)); // Add an admin user
                        break;
                    case "guest":
                        users.add(new Guest(username, password, name, role, phone)); // Add a guest user
                        break;
                    case "receptionist":
                        users.add(new Receptionist(username, password, name, role, phone)); // Add a receptionist user
                        break;
                    default:
                        System.out.println("Invalid role."); // Handle invalid roles
                        return;
                }
                break;
            case 2: // Edit User
                for (User user : users) {
                    if (user.getUsername().equals(username)) {
                        user.setPassword(password); // Set new password for the user
                        user.setName(name); // Set new name for the user
                        user.setPhone(phone); // Set new phone number for the user
                        user.setRole(role); // Set new role for the user
                        break;
                    }
                }
                break;
            case 3: // Remove User
                users.removeIf(user -> user.getUsername().equals(username)); // Remove user by username
                break;
            default:
                System.out.println("Invalid action."); // Handle invalid actions
                return;
        }

        try {
            User.saveUsers(users); // Save the updated list of users
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage()); // Handle saving errors
        }
    }

    // Helper method to style a button
    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #555; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px;"); // Style the button
    }
    
}
