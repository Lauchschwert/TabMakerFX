package xyz.lauchschwert.tabmaker.core.services.dialog;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import xyz.lauchschwert.tabmaker.core.ex.ExportException;
import xyz.lauchschwert.tabmaker.core.ex.ImportException;
import xyz.lauchschwert.tabmaker.core.logging.TmLogger;
import xyz.lauchschwert.tabmaker.core.ui.dialogs.FileDialog;
import xyz.lauchschwert.tabmaker.core.ui.components.panels.InstrumentPanel;

import java.io.File;

public interface DialogService {
    String GetTabNameViaDialog();

    InstrumentPanel GetInstrumentPanelViaDialog();

    static void ShowAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    static File OpenFileDialog() {
        try {
            return FileDialog.GetFileViaFileChooser();
        } catch (ExportException e) {
            TmLogger.warn("Error getting file: " + e.getMessage());
            return null;
        }
    }

    static File OpenSaveDialog(FileChooser.ExtensionFilter jsonFiles) {
        try {
            return FileDialog.SaveFileViaFileChooser();
        } catch (ImportException e) {
            TmLogger.warn("Error saving file: " + e.getMessage());
            return null;
        }
    }
}
