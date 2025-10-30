package restaurant;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginScene {

    // Authenticate user by checking credentials from Users.dat
    public static Boolean authenticateUser(String username, String password) throws IOException, ClassNotFoundException {
        File userFile = new File("Users.dat"); // Create file object for the user data file
        if (!userFile.exists() || userFile.length() == 0) { // Check if the file exists and is not empty
            throw new IOException("User file does not exist or is empty."); // Throw error if file is missing or empty
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userFile))) { // Read user data from the file
            ArrayList<User> users = (ArrayList<User>) ois.readObject(); // Deserialize user data into an ArrayList
            for (User user : users) { // Loop through each user
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) { // Check if credentials match
                    return true; // Return true if credentials match
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new IOException("Error loading users from file: " + e.getMessage()); // Handle errors in reading the file or class
        }
        return false; // Return false if no matching user is found
    }

   public Scene LoginScene(Stage primaryStage, Scene firstScene) {
    StackPane root = new StackPane(); // Create root pane for the scene

    // Reuse the shared background setup method
    BackgroundUtils.setupBackground(root, 0, -200, 600, 600); // Set the background for the scene

    // Create the login label
    Label loginLabel = new Label("Login"); // Create label for login header
    loginLabel.setStyle("-fx-text-fill: white; -fx-font-family: 'Times New Roman'; -fx-font-size: 24px; -fx-padding: 10px;"); // Style label
    loginLabel.setTranslateY(-10); // Adjust vertical position of the label

    // Create username label and text field
    Label usernameLabel = new Label("Username:"); // Label for the username input
    usernameLabel.setStyle("-fx-text-fill: white; -fx-font-family: 'Times New Roman'; -fx-font-size: 16px;"); // Style the label
    TextField usernameField = new TextField(); // Create a text field for the username input
    
    usernameField.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-family: 'Times New Roman'; -fx-font-size: 16px; -fx-padding: 10px;"); // Style the username field
    usernameField.setMaxWidth(200); // Set the maximum width for the username field

    // Place the label and text field in an HBox
    HBox usernameContainer = new HBox(10); // Create a horizontal box with 10px spacing
    usernameContainer.setAlignment(Pos.CENTER); // Center align the username container
    usernameContainer.getChildren().addAll(usernameLabel, usernameField); // Add the label and text field to the container
    usernameContainer.setTranslateY(-20); // Adjust vertical position of the username container

    // Create password label and text field
    Label passwordLabel = new Label("Password:"); // Label for the password input
    passwordLabel.setStyle("-fx-text-fill: white; -fx-font-family: 'Times New Roman'; -fx-font-size: 16px;"); // Style the password label
    PasswordField passwordField = new PasswordField(); // Create a password field for the password input
   
    passwordField.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-family: 'Times New Roman'; -fx-font-size: 16px; -fx-padding: 10px;"); // Style the password field
    passwordField.setMaxWidth(200); // Set the maximum width for the password field

    // Place the label and text field in an HBox
    HBox passwordContainer = new HBox(10); // Create a horizontal box with 10px spacing
    passwordContainer.setAlignment(Pos.CENTER); // Center align the password container
    passwordContainer.getChildren().addAll(passwordLabel, passwordField); // Add the label and password field to the container
    passwordContainer.setTranslateY(0); // Adjust vertical position of the password container

    // Create the submit button
    Button submitButton = new Button("Submit"); // Create the submit button
    submitButton.setStyle("-fx-background-color: black; -fx-border-color: white; -fx-border-width: 1; -fx-text-fill: white; -fx-font-family: 'Times New Roman'; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 15px;"); // Style the submit button
    submitButton.setPrefWidth(120); // Set the preferred width for the submit button
    submitButton.setPrefHeight(40); // Set the preferred height for the submit button

    // Create the back button
    Button backButton = new Button("Back"); // Create the back button
    backButton.setStyle("-fx-background-color: black; -fx-border-color: white; -fx-border-width: 1; -fx-text-fill: white; -fx-font-family: 'Times New Roman'; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 15px;"); // Style the back button
    backButton.setPrefWidth(120); // Set the preferred width for the back button
    backButton.setPrefHeight(40); // Set the preferred height for the back button
    backButton.setOnAction(e -> primaryStage.setScene(firstScene)); // Set the action for back button to switch to the first scene

    // Create an HBox to hold the Submit and Back buttons side by side
    HBox buttonContainer = new HBox(20); // Create horizontal box with 20px spacing between buttons
    buttonContainer.setAlignment(Pos.CENTER); // Center align the button container
    buttonContainer.getChildren().addAll(submitButton, backButton); // Add both buttons to the container
    buttonContainer.setTranslateY(20); // Adjust vertical position of the button container

    // Add all components to the root stack pane
    VBox loginContainer = new VBox(10); // Create a vertical box with 10px spacing between elements
    loginContainer.setAlignment(Pos.CENTER); // Center align the login container
    loginContainer.getChildren().addAll(loginLabel, usernameContainer, passwordContainer, buttonContainer); // Add all elements to the container
    loginContainer.setTranslateY(60); // Adjust vertical position of the login container

    root.getChildren().add(loginContainer); // Add the login container to the root stack pane

    // Action when the submit button is pressed
    submitButton.setOnAction(e -> {
        String username = usernameField.getText(); // Get username from the text field
        String password = passwordField.getText(); // Get password from the password field

        try {
            // Authenticate the user
            boolean isAuthenticated = authenticateUser(username, password); // Check if the username and password are valid

            // If authenticated, determine the user role (only admin for now)
            if (isAuthenticated) {
                // Assuming only admin role for now
                if ("admin".equals(username)) { // Check if the authenticated user is an admin
                    AdminDashboardScene adminScene = new AdminDashboardScene(); // Create an admin dashboard scene
                    Scene adminDashboard = adminScene.createAdminDashboardScene(primaryStage); // Create the admin dashboard scene
                    primaryStage.setScene(adminDashboard);  // Switch to the admin dashboard scene
                }
            } else {
                // Create error label and place it below the password field
                Label errorLabel = new Label("Invalid credentials. Please try again."); // Create error message
                errorLabel.setStyle("-fx-text-fill: red;"); // Style the error message
                VBox.setMargin(errorLabel, new Insets(10, 100, 10, 100)); // Add margin below the password field
                
                // Add error label below the password container
                loginContainer.getChildren().add(errorLabel); // Add the error message to the container
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace(); // Print stack trace if an error occurs during authentication
        }
    });

    // Return the scene without setting its fixed size
    Scene loginScene = new Scene(root,800,800, Color.BLACK); // Create the login scene with a black background
    return loginScene; // Return the created login scene
   }
   
}
