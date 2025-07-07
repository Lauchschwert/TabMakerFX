package xyz.lauchschwert.tabmaker.functions;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import xyz.lauchschwert.tabmaker.logging.TmLogger;
import xyz.lauchschwert.tabmaker.ui.panels.tabpanel.TabPanel;

import java.util.ArrayList;
import java.util.List;

public class NoteFill extends Dialog<Boolean> {
    private final List<TabPanel> tabPanels;
    private final List<TabPanel> selectedPanels;

    private String failMessage;

    public NoteFill(List<TabPanel> tabPanels) {
        super();
        this.tabPanels = tabPanels;
        this.selectedPanels = new ArrayList<>();

        setTitle("Initiate Note-Fill");
        setHeaderText("Configure your note-fill settings");

        // Set dialog buttons
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Get OK button and disable it initially
        final Button okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);

        // Create your UI components
        final VBox content = createContent(okButton); // Pass button reference
        getDialogPane().setContent(content);

        setResultConverter(this::convertResult);
        TmLogger.debug("Instrument Panel Builder initialized");
    }

    private VBox createContent(Button okBtn) {
        final VBox vbox = new VBox(10);

        vbox.getChildren().addAll(
                new Label(
                        "Choose Panels"
                )
        );
        for (TabPanel tabPanel : tabPanels) {
            final CheckBox panelCheckBox = new CheckBox(tabPanel.getStringName());
            panelCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal == false) {
                    selectedPanels.remove(tabPanel);
                    if (selectedPanels.isEmpty()) {
                        okBtn.setDisable(true);
                    }
                } else {
                    selectedPanels.add(tabPanel);
                    if (okBtn.isDisable()) {
                        okBtn.setDisable(false);
                    }
                }
            });
            vbox.getChildren().add(panelCheckBox);
        }
        return vbox;
    }

    private boolean convertResult(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            for (TabPanel tabPanel : selectedPanels) {

            }
        }
        setFailMessage("Note-Fill failed for unknown reason");
        return false;
    }

    private void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
    }

    public String getFailMessage() {
        return failMessage;
    }
}