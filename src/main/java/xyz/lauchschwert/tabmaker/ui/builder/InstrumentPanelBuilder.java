package xyz.lauchschwert.tabmaker.ui.builder;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.BassPanel;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.GuitarPanel;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;

public class InstrumentPanelBuilder extends Dialog<InstrumentPanel> {

    private ToggleGroup panelTypeGroup;

    public InstrumentPanelBuilder() {
        super();

        setTitle("Create New Tab Panel");
        setHeaderText("Configure your tab panel settings");

        // Set dialog buttons
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        // Create your UI components
        VBox content = createContent(); // Pass button reference
        getDialogPane().setContent(content);

        setResultConverter(this::convertResult);
    }

    private VBox createContent() {
        VBox vbox = new VBox(10);

        RadioButton radioGuitar = new RadioButton("Guitar-Panel");
        radioGuitar.selectedProperty().setValue(true);
        RadioButton radioBass = new RadioButton("Bass-Panel");

        panelTypeGroup = new ToggleGroup();
        radioGuitar.setToggleGroup(panelTypeGroup);
        radioBass.setToggleGroup(panelTypeGroup);

        vbox.getChildren().addAll(
                new Label(
                        "Choose tab"
                ),
                radioGuitar,
                radioBass
        );

        return vbox;
    }

    private InstrumentPanel convertResult(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            // Get selected radio button
            Toggle selectedToggle = panelTypeGroup.getSelectedToggle();
            if (selectedToggle == null) {
                return null; // No radio button selected
            }

            RadioButton selectedRadio = (RadioButton) selectedToggle;
            String panelType = selectedRadio.getText(); // "Guitar-Panel" or "Bass-Panel"
            switch (panelType) {
                case "Guitar-Panel" -> {
                    return new GuitarPanel();
                }
                case "Bass-Panel" -> {
                    return new BassPanel();
                }
                default -> {
                    return null;
                }
            }
        }
        return null;
    }
}