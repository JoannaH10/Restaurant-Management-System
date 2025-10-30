package restaurant;

import javafx.geometry.Pos; // Import for positioning the components
import javafx.scene.Scene; // Import for creating the scene
import javafx.scene.control.Button; // Import for creating a button
import javafx.scene.control.Label; // Import for creating a label
import javafx.scene.layout.VBox; // Import for vertical layout container
import javafx.stage.Stage; // Import for managing the window

public class ViewReports {

    // Method to create and return the scene for viewing reports
    public Scene createViewReportsScene(Stage primaryStage) {
        VBox root = new VBox(20); // Create a VBox container with 20px spacing between components
        root.setAlignment(Pos.CENTER); // Center-align the components in the VBox

        // Title for the reports page
        Label titleLabel = new Label("Reports"); // Create a label for the title
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;"); // Style the title label with font size and bold text

        // Report labels
        Label mostOrderedMealLabel = new Label("Most Ordered Meal: " + Receptionist.mostOrderedMeal()); 
        // Create a label to display the most ordered meal and fetch the data from Receptionist class
        mostOrderedMealLabel.setStyle("-fx-font-size: 16px;"); // Style the label with font size

        Label mostReservedTableLabel = new Label("Most Reserved Table: " + Receptionist.mostReservedTable());
        // Create a label to display the most reserved table and fetch the data from Receptionist class
        mostReservedTableLabel.setStyle("-fx-font-size: 16px;"); // Style the label with font size

        // Back button to return to the admin dashboard
        Button backButton = new Button("Back"); // Create a button labeled "Back"
        backButton.setStyle("-fx-background-color: #555; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px;");
        // Style the button with background color, text color, font size, and padding
        backButton.setOnAction(e -> {
            // Set the action for the back button to return to the admin dashboard scene
            AdminDashboardScene adminDashboard = new AdminDashboardScene(); 
            // Create a new AdminDashboardScene object
            primaryStage.setScene(adminDashboard.createAdminDashboardScene(primaryStage)); 
            // Set the scene to the admin dashboard scene
        });

        // Add components to the layout
        root.getChildren().addAll(titleLabel, mostOrderedMealLabel, mostReservedTableLabel, backButton);
        // Add the title, report labels, and back button to the VBox container

        // Return the scene
        return new Scene(root, 800, 800); // Create and return a new scene with the VBox layout and set size to 800x400
    }
    
}
