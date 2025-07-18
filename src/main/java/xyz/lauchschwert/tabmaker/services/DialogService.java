package xyz.lauchschwert.tabmaker.services;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser.ExtensionFilter;
import xyz.lauchschwert.tabmaker.exceptions.ExportException;
import xyz.lauchschwert.tabmaker.exceptions.ImportException;
import xyz.lauchschwert.tabmaker.services.interfaces.AlertService;
import xyz.lauchschwert.tabmaker.ui.dialogs.FileDialog;
import xyz.lauchschwert.tabmaker.ui.dialogs.InstrumentPanelDialog;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.InstrumentPanel;

import java.io.File;
import java.util.Optional;

public class DialogService implements AlertService {
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

    public static File OpenFileDialog(boolean save, ExtensionFilter... filters) throws ImportException, ExportException {
        return FileDialog.GetFileViaFileChooser(save, filters);
    }

    @Override
    public void ShowAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
