package restaurant;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AdminDashboardScene {

    // Creates the admin dashboard scene
    public Scene createAdminDashboardScene(Stage primaryStage) {
        BorderPane root = new BorderPane(); // Create a BorderPane layout to structure the UI

        // Create and set the main sidebar (left side of the screen)
        VBox mainSidebar = createAdminSidebar(primaryStage, root);
        root.setLeft(mainSidebar); // Set the main sidebar to the left of the BorderPane

        // Placeholder for the center content of the dashboard
        VBox mainContent = new VBox(20); // Create a vertical box layout with 20px spacing between elements
        mainContent.setAlignment(Pos.CENTER); // Align the content to the center
        Label placeholderLabel = new Label("Select an option from the sidebar"); // Placeholder label to prompt user
        mainContent.getChildren().add(placeholderLabel); // Add the label to the main content
        root.setCenter(mainContent); // Set the main content in the center of the BorderPane

        return new Scene(root, 800, 800, Color.BLACK); // Return a new Scene with black background and size 800x800
    }

    // Creates the admin sidebar with navigation buttons
    private VBox createAdminSidebar(Stage primaryStage, BorderPane root) {
        VBox sidebar = new VBox(20); // Create a vertical box layout with 20px spacing between elements
        sidebar.setAlignment(Pos.TOP_CENTER); // Align content in the sidebar to the top center
        sidebar.setStyle("-fx-background-color: #333;"); // Set the sidebar's background color to dark gray
        sidebar.setPrefWidth(200); // Set the sidebar's preferred width to 200px

        // Title for the sidebar
        Label dashboardTitle = new Label("Admin Dashboard");
        dashboardTitle.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px;");

        // Buttons for different sections of the admin dashboard
        Button manageTablesButton = createAdminSidebarButton("Manage Tables", primaryStage, root);
        Button manageMenusButton = createAdminSidebarButton("Manage Menus", primaryStage, root);
        Button manageUsersButton = createAdminSidebarButton("Manage Users", primaryStage, root);
        Button viewReportsButton = createAdminSidebarButton("View Reports", primaryStage, root);
        Button viewDataButton = createAdminSidebarButton("View Data", primaryStage, root);

        // Add the title and buttons to the sidebar
        sidebar.getChildren().addAll(
                dashboardTitle, // Add the dashboard title to the sidebar
                manageTablesButton, // Add the "Manage Tables" button
                manageMenusButton, // Add the "Manage Menus" button
                manageUsersButton, // Add the "Manage Users" button
                viewReportsButton, // Add the "View Reports" button
                viewDataButton // Add the "View Data" button
        );

        return sidebar; // Return the sidebar to be used in the root layout
    }

    // Creates a button for the admin sidebar
    private Button createAdminSidebarButton(String buttonText, Stage primaryStage, BorderPane root) {
        Button button = new Button(buttonText); // Create a new button with the given text
        button.setStyle("-fx-background-color: #555; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px; -fx-pref-width: 180px;"); // Style the button with colors and padding

        // Set an action when the button is clicked
        button.setOnAction(e -> {
            if (buttonText.equals("Manage Tables")) {
                openSecondSidebar(primaryStage, root, createSidebarForManageTables(root));
                // Open sidebar for managing tables
            } else if (buttonText.equals("Manage Menus")) {
                openSecondSidebar(primaryStage, root, createSidebarForManageMenus()); // Open sidebar for managing menus
            } else if (buttonText.equals("Manage Users")) {
                openSecondSidebar(primaryStage, root, createSidebarForManageUsers()); // Open sidebar for managing users
            } else if (buttonText.equals("View Reports")) {
                openSecondSidebar(primaryStage, root, createSidebarForViewReports()); // Open sidebar for viewing reports
            } else if (buttonText.equals("View Data")) {
                openSecondSidebar(primaryStage, root, createSidebarForViewData()); // Open sidebar for viewing data
            }
        });

        return button; // Return the button
    }
    

    // Open a second sidebar next to the main sidebar
    private void openSecondSidebar(Stage primaryStage, BorderPane root, VBox secondSidebar) {
        VBox mainSidebar = createAdminSidebar(primaryStage, root); // Create the main sidebar again for consistency

        HBox combinedSidebar = new HBox(); // Create a horizontal box layout to combine both sidebars
        combinedSidebar.setSpacing(0); // Set no space between the two sidebars
        combinedSidebar.getChildren().addAll(mainSidebar, secondSidebar); // Add both sidebars to the horizontal box

        root.setLeft(combinedSidebar); // Set the combined sidebars on the left of the root layout
    }

    // Method to close the second sidebar
    private void closeSecondSidebar(VBox secondSidebar) {
        BorderPane root = (BorderPane) secondSidebar.getParent().getParent(); // Access the root layout
        HBox combinedSidebar = (HBox) root.getLeft(); // Get the combined sidebars
        combinedSidebar.getChildren().remove(secondSidebar); // Remove the second sidebar
    }

    // Sidebar for managing tables
private VBox createSidebarForManageTables(BorderPane root) {
    VBox sidebar = new VBox(20); // Create a vertical box layout with 20px spacing
    sidebar.setAlignment(Pos.TOP_CENTER); // Align content at the top center
    sidebar.setStyle("-fx-background-color: #444;"); // Set a background color for the sidebar
    sidebar.setPrefWidth(200); // Set the sidebar's preferred width to 200px

    Label title = new Label("   Manage Tables"); // Label for the "Manage Tables" section
    title.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px; -fx-pref-width: 180px;");

    // Buttons for managing tables
    Button addTableButton = new Button("Add Table");
    Button editTableButton = new Button("Edit Table");
    Button removeTableButton = new Button("Remove Table");

    // Apply common button styling
    styleSidebarButton(addTableButton);
    styleSidebarButton(editTableButton);
    styleSidebarButton(removeTableButton);

    // Button actions to display forms beside the sidebar
    addTableButton.setOnAction(e -> displayForm("Add", root));
    editTableButton.setOnAction(e -> displayForm("Edit", root));
    removeTableButton.setOnAction(e -> displayForm("Remove", root));

    // Close button
    Button closeButton = new Button("Close");
    closeButton.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 180px;");
    closeButton.setOnAction(e -> closeSecondSidebar(sidebar));

    sidebar.getChildren().addAll(title, addTableButton, editTableButton, removeTableButton, closeButton); // Add buttons to sidebar
    return sidebar; // Return the completed sidebar
}

// Method to display the forms beside the second sidebar
private void displayForm(String actionType, BorderPane root) {
    VBox formContainer = new VBox(20); // Create a vertical layout for the form
    formContainer.setAlignment(Pos.TOP_CENTER); // Align content to the top center
    formContainer.setStyle("-fx-background-color: #eee; -fx-padding: 20px;"); // Style the form container
    formContainer.setPrefWidth(400); // Set the preferred width of the form

    // Title for the form
    Label formTitle = new Label(actionType + " Table");
    formTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

    // Add fields based on the action type
    Label tableNameLabel = new Label("Table Name:");
    TextField tableNameField = new TextField();

    Label tableCapacityLabel = new Label("Capacity:");
    TextField tableCapacityField = new TextField();

    Button submitButton = new Button(actionType);
    submitButton.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px;");
    submitButton.setOnAction(e -> {
        // Handle the form submission logic here
        System.out.println(actionType + " Table: " + tableNameField.getText() + ", Capacity: " + tableCapacityField.getText());
    });

    // Add form elements to the container
    formContainer.getChildren().addAll(formTitle, tableNameLabel, tableNameField, tableCapacityLabel, tableCapacityField, submitButton);

    // Inject the form into the center of the BorderPane
    root.setCenter(formContainer);
}
    // Sidebar for managing menus
    private VBox createSidebarForManageMenus() {
        VBox sidebar = new VBox(20); // Create a vertical box layout with 20px spacing
        sidebar.setAlignment(Pos.TOP_CENTER); // Align content at the top center
        sidebar.setStyle("-fx-background-color: #444;"); // Set a background color for the sidebar
        sidebar.setPrefWidth(200); // Set the sidebar's preferred width to 200px

        Label title = new Label("   Manage Menus"); // Label for the "Manage Menus" section
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px; -fx-pref-width: 180px;");

        // Buttons for managing menu items
        Button addMenuItemButton = new Button("Add Menu Item");
        Button editMenuItemButton = new Button("Edit Menu Item");
        Button removeMenuItemButton = new Button("Remove Menu Item");

        // Apply common button styling
        styleSidebarButton(addMenuItemButton);
        styleSidebarButton(editMenuItemButton);
        styleSidebarButton(removeMenuItemButton);

        // Close button
        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 180px;");
        closeButton.setOnAction(e -> closeSecondSidebar(sidebar));

        sidebar.getChildren().addAll(title, addMenuItemButton, editMenuItemButton, removeMenuItemButton, closeButton); // Add buttons to sidebar
        return sidebar; // Return the completed sidebar
    }

    // Sidebar for managing users
    private VBox createSidebarForManageUsers() {
        VBox sidebar = new VBox(20); // Create a vertical box layout with 20px spacing
        sidebar.setAlignment(Pos.TOP_CENTER); // Align content at the top center
        sidebar.setStyle("-fx-background-color: #444;"); // Set a background color for the sidebar
        sidebar.setPrefWidth(200); // Set the sidebar's preferred width to 200px

        Label title = new Label("   Manage Users"); // Label for the "Manage Users" section
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px; -fx-pref-width: 180px;");

        // Buttons for managing users
        Button addUserButton = new Button("Add User");
        Button editUserButton = new Button("Edit User");
        Button removeUserButton = new Button("Remove User");

        // Apply common button styling
        styleSidebarButton(addUserButton);
        styleSidebarButton(editUserButton);
        styleSidebarButton(removeUserButton);

        // Close button
        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 180px;");
        closeButton.setOnAction(e -> closeSecondSidebar(sidebar));

        sidebar.getChildren().addAll(title, addUserButton, editUserButton, removeUserButton, closeButton); // Add buttons to sidebar
        return sidebar; // Return the completed sidebar
    }

    // Sidebar for viewing reports
    private VBox createSidebarForViewReports() {
        VBox sidebar = new VBox(20); // Create a vertical box layout with 20px spacing
        sidebar.setAlignment(Pos.TOP_CENTER); // Align content at the top center
        sidebar.setStyle("-fx-background-color: #444;"); // Set a background color for the sidebar
        sidebar.setPrefWidth(200); // Set the sidebar's preferred width to 200px

        Label title = new Label("    View Reports"); // Label for the "View Reports" section
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px; -fx-pref-width: 180px;");

        // Buttons for viewing reports
        Button dailyReportsButton = new Button("Daily Reports");
        Button monthlyReportsButton = new Button("Monthly Reports");
        Button annualReportsButton = new Button("Annual Reports");

        // Apply common button styling
        styleSidebarButton(dailyReportsButton);
        styleSidebarButton(monthlyReportsButton);
        styleSidebarButton(annualReportsButton);

        // Close button
        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 180px;");
        closeButton.setOnAction(e -> closeSecondSidebar(sidebar));

        sidebar.getChildren().addAll(title, dailyReportsButton, monthlyReportsButton, annualReportsButton, closeButton); // Add buttons to sidebar
        return sidebar; // Return the completed sidebar
    }

    // Sidebar for viewing data
    private VBox createSidebarForViewData() {
        VBox sidebar = new VBox(20); // Create a vertical box layout with 20px spacing
        sidebar.setAlignment(Pos.TOP_CENTER); // Align content at the top center
        sidebar.setStyle("-fx-background-color: #444;"); // Set a background color for the sidebar
        sidebar.setPrefWidth(200); // Set the sidebar's preferred width to 200px

        Label title = new Label("     View Data"); // Label for the "View Data" section
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px; -fx-pref-width: 180px;");

        // Buttons for viewing data
        Button salesDataButton = new Button("Sales Data");
        Button customerDataButton = new Button("Customer Data");

        // Apply common button styling
        styleSidebarButton(salesDataButton);
        styleSidebarButton(customerDataButton);

        // Close button
        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 180px;");
        closeButton.setOnAction(e -> closeSecondSidebar(sidebar));

        sidebar.getChildren().addAll(title, salesDataButton, customerDataButton, closeButton); // Add buttons to sidebar
        return sidebar; // Return the completed sidebar
    }

    // Helper method to style sidebar buttons
    private void styleSidebarButton(Button button) {
        button.setStyle("-fx-background-color: #555; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 180px;");
    }
}
