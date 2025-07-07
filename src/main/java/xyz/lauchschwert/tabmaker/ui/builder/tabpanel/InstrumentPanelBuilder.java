package xyz.lauchschwert.tabmaker.ui.builder.tabpanel;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import xyz.lauchschwert.tabmaker.TabMaker;
import xyz.lauchschwert.tabmaker.logging.TmLogger;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.BassPanel;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.GuitarPanel;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;

public class InstrumentPanelBuilder extends Dialog<InstrumentPanel> {
    private final TabMaker tabMaker;

    private ToggleGroup panelTypeGroup;
    private String failMessage, selectedName;

    public InstrumentPanelBuilder(TabMaker tabMaker) {
        super();
        this.tabMaker = tabMaker;

        setTitle("Create New Tab Panel");
        setHeaderText("Configure your tab panel settings");

        // Set dialog buttons
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Get OK button and disable it initially
        final Button okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);

        // Create your UI components
        final VBox content = createContent(okButton); // Pass button reference
        getDialogPane().setContent(content);

        setResultConverter(this::convertResult);
        TmLogger.debug("Instrument Panel Builder initialized");
    }

    private VBox createContent(Button okBtn) {
        final VBox vbox = new VBox(10);

        final RadioButton radioGuitar = new RadioButton("Guitar-Panel");
        radioGuitar.selectedProperty().setValue(true);
        final RadioButton radioBass = new RadioButton("Bass-Panel");

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
            final TextInputDialog tid = new TextInputDialog();
            tid.setTitle("Create a new Tab");
            tid.setHeaderText("Set a tab name");
            tid.showAndWait();
            setSelectedName(tid.getResult());
            if (selectedName.isEmpty()) {
                selectedName = "Default";
            }

            // Get selected radio button
            final Toggle selectedToggle = panelTypeGroup.getSelectedToggle();
            if (selectedToggle == null) {
                setFailMessage("No radio button selected");
                return null; // No radio button selected
            }

            final RadioButton selectedRadio = (RadioButton) selectedToggle;
            final String panelType = selectedRadio.getText(); // "Guitar-Panel" or "Bass-Panel"
            switch (panelType) {
                case "Guitar-Panel" -> {
                    return new GuitarPanel();
                }
                case "Bass-Panel" -> {
                    return new BassPanel();
                }
                default -> {
                    setFailMessage("No radio button selected or unknown panel-preset configured.");
                    return null;
                }
            }
        }
        setFailMessage("TabMaker failed for unknown reason");
        return null;
    }

    private void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
    }

    public String getFailMessage() {
        return failMessage;
    }

    public String getSelectedName() {
        return selectedName;
    }

    public void setSelectedName(String selectedName) {
        this.selectedName = selectedName;
    }
}