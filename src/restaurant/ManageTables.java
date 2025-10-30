package restaurant;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ManageTables {

    private VBox contentArea;

    // Main layout
    private BorderPane createMainLayout() {
        BorderPane mainLayout = new BorderPane();

        // Sidebar with buttons
        VBox sidebar = new VBox(20);
        sidebar.setStyle("-fx-background-color: #2F4F4F; -fx-padding: 20px;");
        sidebar.setAlignment(Pos.TOP_CENTER);

        Button addButton = new Button("Add Table");
        Button editButton = new Button("Edit Table");
        Button removeButton = new Button("Remove Table");

        styleButton(addButton);
        styleButton(editButton);
        styleButton(removeButton);

        addButton.setOnAction(e -> showAddTableForm());
        editButton.setOnAction(e -> showEditTableForm());
        removeButton.setOnAction(e -> showRemoveTableForm());

        sidebar.getChildren().addAll(addButton, editButton, removeButton);

        // Content area for dynamic forms
        contentArea = new VBox(20);
        contentArea.setAlignment(Pos.CENTER);

        mainLayout.setLeft(sidebar);
        mainLayout.setCenter(contentArea);

        return mainLayout;
    }

    // Helper method to style buttons
    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px;");
    }

    // Show Add Table Form
    private void showAddTableForm() {
        contentArea.getChildren().clear();
        VBox addForm = new VBox(20);
        addForm.setAlignment(Pos.CENTER);

        Label title = new Label("Add Table");
        title.setStyle("-fx-text-fill: black; -fx-font-size: 20px; -fx-font-weight: bold;");

        TextField tableIdField = new TextField();
        tableIdField.setPromptText("Table ID");

        TextField categoryField = new TextField();
        categoryField.setPromptText("Category");

        TextField capacityField = new TextField();
        capacityField.setPromptText("Capacity");

        CheckBox reservedCheckbox = new CheckBox("Reserved");

        Button addButton = new Button("Add Table");
        styleButton(addButton);

        addButton.setOnAction(e -> handleAddTable(tableIdField, categoryField, capacityField, reservedCheckbox));

        Button closeButton = new Button("Close");
        styleButton(closeButton);
        closeButton.setOnAction(e -> closeForm());

        addForm.getChildren().addAll(title, tableIdField, categoryField, capacityField, reservedCheckbox, addButton, closeButton);

        contentArea.getChildren().add(addForm);
        
    }

    // Show Edit Table Form
    private void showEditTableForm() {
        contentArea.getChildren().clear();
        VBox editForm = new VBox(20);
        editForm.setAlignment(Pos.CENTER);

        Label title = new Label("Edit Table");
        title.setStyle("-fx-text-fill: black; -fx-font-size: 20px; -fx-font-weight: bold;");

        TextField tableIdField = new TextField();
        tableIdField.setPromptText("Table ID");

        TextField categoryField = new TextField();
        categoryField.setPromptText("Category");

        TextField capacityField = new TextField();
        capacityField.setPromptText("Capacity");

        CheckBox reservedCheckbox = new CheckBox("Reserved");

        Button editButton = new Button("Edit Table");
        styleButton(editButton);

        editButton.setOnAction(e -> handleEditTable(tableIdField, categoryField, capacityField, reservedCheckbox));

        Button closeButton = new Button("Close");
        styleButton(closeButton);
        closeButton.setOnAction(e -> closeForm());

        editForm.getChildren().addAll(title, tableIdField, categoryField, capacityField, reservedCheckbox, editButton, closeButton);

        contentArea.getChildren().add(editForm);
    }

    // Show Remove Table Form
    private void showRemoveTableForm() {
        contentArea.getChildren().clear();
        VBox removeForm = new VBox(20);
        removeForm.setAlignment(Pos.CENTER);

        Label title = new Label("Remove Table");
        title.setStyle("-fx-text-fill: black; -fx-font-size: 20px; -fx-font-weight: bold;");

        TextField tableIdField = new TextField();
        tableIdField.setPromptText("Table ID");

        Button removeButton = new Button("Remove Table");
        styleButton(removeButton);

        removeButton.setOnAction(e -> handleRemoveTable(tableIdField));

        Button closeButton = new Button("Close");
        styleButton(closeButton);
        closeButton.setOnAction(e -> closeForm());

        removeForm.getChildren().addAll(title, tableIdField, removeButton, closeButton);

        contentArea.getChildren().add(removeForm);
    }

    // Handle Add Table action
    private void handleAddTable(TextField tableIdField, TextField categoryField, TextField capacityField, CheckBox reservedCheckbox) {
        try {
            int tableId = Integer.parseInt(tableIdField.getText());
            String category = categoryField.getText();
            int capacity = Integer.parseInt(capacityField.getText());
            boolean reserved = reservedCheckbox.isSelected();

            Table newTable = new Table(tableId, category, capacity, reserved);
            ArrayList<Table> tables = Table.loadTablesFromFile();
            tables.add(newTable);
            Table.saveTablesToFile(tables);

            showAlert("Success", "Table added successfully!");
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter valid numeric values for Table ID and Capacity.");
        }
    }

    // Handle Edit Table action
    private void handleEditTable(TextField tableIdField, TextField categoryField, TextField capacityField, CheckBox reservedCheckbox) {
        try {
            int tableId = Integer.parseInt(tableIdField.getText());
            String category = categoryField.getText();
            int capacity = Integer.parseInt(capacityField.getText());
            boolean reserved = reservedCheckbox.isSelected();

            ArrayList<Table> tables = Table.loadTablesFromFile();
            for (Table table : tables) {
                if (table.getTableId() == tableId) {
                    table.setCategory(category);
                    table.setCapacity(capacity);
                    table.setReserved(reserved);
                    Table.saveTablesToFile(tables);
                    showAlert("Success", "Table edited successfully!");
                    return;
                }
            }
            showAlert("Error", "Table with the given ID not found.");
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter valid numeric values for Table ID and Capacity.");
        }
    }

    // Handle Remove Table action
    private void handleRemoveTable(TextField tableIdField) {
        try {
            int tableId = Integer.parseInt(tableIdField.getText());
            ArrayList<Table> tables = Table.loadTablesFromFile();
            tables.removeIf(table -> table.getTableId() == tableId);
            Table.saveTablesToFile(tables);
            showAlert("Success", "Table removed successfully!");
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid Table ID.");
        }
    }

    // Helper method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Close the current form
    private void closeForm() {
        contentArea.getChildren().clear();
    }

    // Create and display the main window
    public void showTableManagementWindow(Stage primaryStage) {
        BorderPane mainLayout = createMainLayout();
        Scene scene = new Scene(mainLayout, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Manage Tables");
        primaryStage.show();
    }
}
