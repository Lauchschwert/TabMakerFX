package xyz.lauchschwert.tabmaker.functions;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import xyz.lauchschwert.tabmaker.logging.TmLogger;
import xyz.lauchschwert.tabmaker.results.NoteFillResult;
import xyz.lauchschwert.tabmaker.ui.panels.tabpanel.TabPanel;

import java.util.ArrayList;
import java.util.List;

public class NoteFill extends Dialog<NoteFillResult> {
    private final List<TabPanel> tabPanels;
    private final List<TabPanel> selectedPanels;
    private final List<String> notes;
    private String failMessage;


    private TextField lengthInput;

    public NoteFill(List<TabPanel> tabPanels) {
        super();
        this.tabPanels = tabPanels;
        this.selectedPanels = new ArrayList<>();
        this.notes = new ArrayList<>();

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
        final HBox noteBox = new HBox(5);

        vbox.getChildren().addAll(
                new Label(
                        "Choose Panels"
                )
        );
        createCheckBoxes(okBtn, vbox);

        lengthInput = new TextField();
        lengthInput.setPromptText("Enter note amount (0-100)");

        vbox.getChildren().addAll(lengthInput);
        return vbox;
    }

    private void createCheckBoxes(Button okBtn, VBox vbox) {
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
    }

    private NoteFillResult convertResult(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            NoteFillResult result = new NoteFillResult();

            result.setTabPanels(selectedPanels);
            result.setNotes(notes);

            String fillLengthString = lengthInput.getText();
            int fillLength;
            try {
                fillLength = Integer.parseInt(fillLengthString);
            } catch (NumberFormatException e) {
                fillLength = 0;
            }
            result.setFillLength(fillLength);

            return result;
        }

        setFailMessage();
        return null;
    }

    private void setFailMessage() {
        this.failMessage = "Note-Fill failed for unknown reason";
    }

    public String getFailMessage() {
        return failMessage;
    }
}