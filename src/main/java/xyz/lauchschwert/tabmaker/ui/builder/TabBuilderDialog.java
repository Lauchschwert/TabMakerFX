package xyz.lauchschwert.tabmaker.ui.builder;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

public class TabBuilderDialog extends Dialog<ButtonType> {
    private TextField nameField;
    private CheckBox closeableCheckBox;

    public TabBuilderDialog() {
        initComponents();
        setupLayout();
        setupButtonTypes();
        setTitle("Create Tab");
        setHeaderText("Configure your new tab");
    }

    private void initComponents() {
        nameField = new TextField();
        nameField.setPromptText("Enter tab name");

        closeableCheckBox = new CheckBox("Closeable");
        closeableCheckBox.setSelected(true);
    }

    private void setupLayout() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);

        grid.add(new Label("Options:"), 0, 1);
        grid.add(closeableCheckBox, 1, 1);

        getDialogPane().setContent(grid);
    }

    private void setupButtonTypes() {
        ButtonType okButton = ButtonType.OK;
        ButtonType cancelButton = ButtonType.CANCEL;

        getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

        // Enable/disable OK button based on validation
        Button okBtn = (Button) getDialogPane().lookupButton(okButton);
        okBtn.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            if (!isValid()) {
                event.consume(); // Prevent dialog from closing
                showValidationError();
            }
        });
    }

    private void loadAvailablePanels() {
        // Load your available panels here
        // availablePanels.addAll(PanelService.getAllPanels());
    }

    private boolean isValid() {
        return nameField.getText() != null && !nameField.getText().trim().isEmpty();
    }

    private void showValidationError() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText("Please fix the following issues:");
        alert.setContentText("Tab name is required");
        alert.showAndWait();
    }

    // Getters for the form data
    public String getName() {
        return nameField.getText();
    }

    public boolean isCloseable() {
        return closeableCheckBox.isSelected();
    }
}