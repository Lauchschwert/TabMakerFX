package xyz.lauchschwert.tabmaker.ui.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser.ExtensionFilter;
import xyz.lauchschwert.tabmaker.ui.dialog.dialogs.FileDialog;
import xyz.lauchschwert.tabmaker.ui.dialog.dialogs.InstrumentPanelDialog;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;

import java.io.File;
import java.util.Optional;

public class DialogService {
    public static String GetStringViaDialog() {
        TextInputDialog tabNameDialog = new TextInputDialog("Default");
        tabNameDialog.setTitle("Enter Tab name");
        tabNameDialog.setHeaderText("Please enter the Tab name of the imported panel!\n(leave empty for default)");
        tabNameDialog.showAndWait();

        return tabNameDialog.getResult();
    }

    public static InstrumentPanel GetInstrumentPanelViaDialog() {
        InstrumentPanelDialog instrumentPanelDialog = new InstrumentPanelDialog();
        Optional<InstrumentPanel> instrumentPanel = instrumentPanelDialog.showAndWait();
        return instrumentPanel.orElse(null);
    }

    public static File OpenFileDialog(boolean save, ExtensionFilter... filters) {
        return FileDialog.GetFileViaFileChooser(save, filters);
    }

    public static void ShowAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
