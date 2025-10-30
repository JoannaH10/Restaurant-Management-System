package restaurant;

import javafx.geometry.Pos; // Import to set alignment for components
import javafx.scene.Scene; // Import to create a scene for the application
import javafx.scene.control.*; // Import to use controls like buttons, text fields, combo boxes, etc.
import javafx.scene.layout.*; // Import for different layout managers like VBox, HBox, BorderPane
import javafx.stage.Stage; // Import to manage the primary stage of the application
import java.util.ArrayList; // Import for using dynamic arrays

public class ManageMenu {

    // Method to create the Manage Menu scene with a sidebar and main content
    public Scene createManageMenuScene(Stage primaryStage) {
        BorderPane root = new BorderPane(); // Create a BorderPane to hold the sidebar and main content

        // Create the sidebar with menu management options (Add, Edit, Remove, Back)
        VBox sidebar = createMenuManagementSidebar(primaryStage);

        // Set the sidebar to the left side of the screen
        root.setLeft(sidebar);

        // Create a VBox for the main content area, with a 20px spacing between items
        VBox mainContent = new VBox(20);
        mainContent.setAlignment(Pos.CENTER); // Center the items in the main content area

        // Add a placeholder label in the center
        Label placeholderLabel = new Label("Select an action from the sidebar");
        mainContent.getChildren().add(placeholderLabel); // Add the placeholder label to the main content

        // Set the initial content to be the placeholder label
        root.setCenter(mainContent);

        // Create a scene with the root layout (BorderPane) and set its size to 800x400
        Scene manageMenuScene = new Scene(root, 800, 800);
        return manageMenuScene; // Return the newly created scene
    }

    // Create the sidebar for the Manage Menu screen with buttons for actions
    private VBox createMenuManagementSidebar(Stage primaryStage) {
        VBox sidebar = new VBox(20); // Create a vertical box with 20px spacing between items
        sidebar.setAlignment(Pos.TOP_CENTER); // Align the items at the top center
        sidebar.setStyle("-fx-background-color: #333;");  // Set a dark background color for the sidebar
        sidebar.setPrefWidth(200);  // Set the sidebar's width to 200px

        // Create a label for the title at the top of the sidebar
        Label dashboardTitle = new Label("Manage Menu");
        dashboardTitle.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px;");

        // Create buttons for each menu management action (Add, Edit, Remove, Back)
        Button addMenuItemButton = createMenuManagementButton("Add Menu Item", primaryStage);
        Button editMenuItemButton = createMenuManagementButton("Edit Menu Item", primaryStage);
        Button removeMenuItemButton = createMenuManagementButton("Remove Menu Item", primaryStage);
        Button backButton = createMenuManagementButton("Back", primaryStage);

        // Add the title label and buttons to the sidebar layout
        sidebar.getChildren().addAll(
                dashboardTitle,
                addMenuItemButton,
                editMenuItemButton,
                removeMenuItemButton,
                backButton
        );

        return sidebar; // Return the completed sidebar layout
    }

    // Helper method to create a button with specific text and an action
    private Button createMenuManagementButton(String buttonText, Stage primaryStage) {
        Button button = new Button(buttonText); // Create a new button with the specified text
        button.setStyle("-fx-background-color: #555; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px; -fx-pref-width: 180px;");

        // Define the action when the button is clicked
        button.setOnAction(e -> {
            if (buttonText.equals("Add Menu Item")) {
                // Show the form for adding a menu item
                showMenuForm(primaryStage, "Add Menu Item");
            } else if (buttonText.equals("Edit Menu Item")) {
                // Show the form for editing a menu item
                showMenuForm(primaryStage, "Edit Menu Item");
            } else if (buttonText.equals("Remove Menu Item")) {
                // Show the form for removing a menu item
                showMenuForm(primaryStage, "Remove Menu Item");
            } else if (buttonText.equals("Back")) {
                // Go back to the admin dashboard scene
                AdminDashboardScene adminDashboard = new AdminDashboardScene();
                primaryStage.setScene(adminDashboard.createAdminDashboardScene(primaryStage));
            }
        });

        return button; // Return the created button
    }

    // Method to show the form for adding, editing, or removing a menu item
    private void showMenuForm(Stage primaryStage, String action) {
        VBox formContent = new VBox(20); // Create a VBox with 20px spacing between form elements
        formContent.setAlignment(Pos.CENTER); // Align form content to the center
        formContent.setStyle("-fx-background-color: black;"); // Set a black background for the form

        // Create input fields for menu item details
        TextField nameField = new TextField();
        nameField.setPromptText("Menu Item Name"); // Set placeholder text for the name field

        TextField priceField = new TextField();
        priceField.setPromptText("Price"); // Set placeholder text for the price field

        // Create a combo box for selecting the category
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("Breakfast", "Lunch", "Dinner", "Drinks", "Appetizers"); // Add categories to the combo box
        categoryComboBox.setValue("Breakfast"); // Set default category to "Breakfast"

        // Create buttons for form actions
        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");
        Button backButton = new Button("Back");

        // Style the buttons
        styleButton(submitButton);
        styleButton(cancelButton);
        styleButton(backButton);

        // Define the action when the submit button is clicked
        submitButton.setOnAction(e -> {
            try {
                String name = nameField.getText(); // Get the entered name
                double price = Double.parseDouble(priceField.getText()); // Parse the entered price as a double
                String category = categoryComboBox.getValue(); // Get the selected category

                // Perform actions based on the form's purpose (Add, Edit, Remove)
                switch (action) {
                    case "Add Menu Item":
                        manageMenu(1, new StringBuffer(name), price, category); // Call the manageMenu method to add an item
                        showAlert("Success", "Menu item added successfully!");
                        break;
                    case "Edit Menu Item":
                        manageMenu(2, new StringBuffer(name), price, category); // Edit an existing menu item
                        showAlert("Success", "Menu item edited successfully!");
                        break;
                    case "Remove Menu Item":
                        manageMenu(3, new StringBuffer(name), 0, category); // Remove the menu item
                        showAlert("Success", "Menu item removed successfully!");
                        break;
                }
            } catch (NumberFormatException ex) {
                // Show an alert if there was a number format error (e.g., invalid price)
                showAlert("Error", "Please enter valid values.");
            }
        });

        // Define the cancel button action to clear the form
        cancelButton.setOnAction(e -> {
            nameField.clear(); // Clear the name field
            priceField.clear(); // Clear the price field
            categoryComboBox.setValue("Breakfast"); // Reset the category to "Breakfast"
        });

        // Define the back button action to return to the Manage Menu scene
        backButton.setOnAction(e -> {
            primaryStage.setScene(createManageMenuScene(primaryStage)); // Return to the Manage Menu scene
        });

        // Create a horizontal box for the buttons and set their alignment to center
        HBox buttonsBox = new HBox(20, submitButton, cancelButton, backButton);
        buttonsBox.setAlignment(Pos.CENTER);

        // Add the input fields and buttons to the form content
        formContent.getChildren().addAll(
                nameField, priceField, categoryComboBox, buttonsBox
        );

        // Set the new form content as the root of the current scene
        primaryStage.getScene().setRoot(formContent);
    }

    // Helper method to show an alert with a title and message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Create an information alert
        alert.setTitle(title); // Set the alert's title
        alert.setHeaderText(null); // Remove the header text
        alert.setContentText(message); // Set the content message of the alert
        alert.showAndWait(); // Show the alert and wait for the user to close it
    }

    // Method to handle adding, editing, or removing a menu item based on the action
    private void manageMenu(int action, StringBuffer name, double price, String category) {
        Menu menu = new Menu(); // Create a new Menu object
        ArrayList<Meal> meals = new ArrayList<>(); // Create an empty list for meals

        // Get the appropriate list of meals based on the category
        switch (category.toLowerCase()) {
            case "breakfast":
                meals = menu.getBreakfast();
                break;
            case "lunch":
                meals = menu.getLunch();
                break;
            case "dinner":
                meals = menu.getDinner();
                break;
            case "drinks":
                meals = menu.getDrinks();
                break;
            case "appetizers":
                meals = menu.getAppetizers();
                break;
            default:
                showAlert("Error", "Invalid category.");
                return;
        }

        // Perform actions based on the value of the 'action' parameter
        switch (action) {
            case 1: // Add Menu Item
                meals.add(new Meal(name, category, price)); // Add a new meal to the list
                break;
            case 2: // Edit Menu Item
                for (Meal meal : meals) {
                    if (meal.getName().toString().equals(name.toString())) {
                        meal.setPrice(price); // Update the price of the meal
                        meal.setCategory(category); // Update the category of the meal
                        break;
                    }
                }
                break;
            case 3: // Remove Menu Item
                meals.removeIf(meal -> meal.getName().toString().equals(name.toString())); // Remove the meal
                break;
            default:
                System.out.println("Invalid action."); // Handle invalid action
                return;
        }

        // Write the updated list of meals back to the file
        menu.writeToRandomAccessFile(category.toLowerCase() + ".dat", meals);
    }

    // Helper method to style buttons
    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #555; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px;"); // Apply a consistent style to buttons
    }
    
}
