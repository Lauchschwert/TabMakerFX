package xyz.lauchschwert.tabmaker.ui.builder.tabpanel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import xyz.lauchschwert.tabmaker.ui.panels.tabpanel.TabPanel;

public class TabPanelBuilder extends Dialog<TabPanel> {
    RadioButton radioGuitar;
    RadioButton radioBass;

    public TabPanelBuilder(ObservableList<Tab> items) {
        super();

        setTitle("Create New Tab Panel");
        setHeaderText("Configure your tab panel settings");

        // Set dialog buttons
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Create your UI components
        VBox content = createContent(items);
        getDialogPane().setContent(content);

        // Set result converter
        setResultConverter(this::convertResult);
    }

    private VBox createContent(ObservableList<Tab> items) {
        VBox vbox = new VBox(10);

        Tab createNewTab = new Tab("Create new Tab");
        createNewTab.selectedProperty().addListener((observableValue, oldBool, newBool) -> {
            System.out.println("old: " + oldBool + "new: " + newBool);
        });

        final ObservableList<String> displayItems = FXCollections.observableArrayList();
        for (Tab tab : items) {
            if (!tab.getText().equalsIgnoreCase("welcome")) {
                displayItems.add(tab.getText());
            }
        }
        displayItems.add(createNewTab.getText());

        ComboBox<String> dropDownSelector = new ComboBox<>();
        dropDownSelector.setItems(displayItems);
        dropDownSelector.setValue("Create new Tab");

        ToggleGroup panelTypeGroup = new ToggleGroup();

        radioGuitar = new RadioButton("Guitar-Panel");
        radioGuitar.selectedProperty().setValue(true);
        radioBass = new RadioButton("Bass-Panel");

        radioGuitar.setToggleGroup(panelTypeGroup);
        radioBass.setToggleGroup(panelTypeGroup);

        vbox.getChildren().addAll(
                new Label(
                        "Choose tab"
                ),
                dropDownSelector,
                radioGuitar,
                radioBass
        );

        return vbox;
    }

    private TabPanel convertResult(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            // Extract values from UI components and create TabPanel
            // You'll need to store references to the fields
            System.out.println("e");
            return new TabPanel("E");
        }
        return null;
    }
}