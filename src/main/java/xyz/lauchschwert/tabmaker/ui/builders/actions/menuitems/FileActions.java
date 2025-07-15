package xyz.lauchschwert.tabmaker.ui.builders.actions.menuitems;

import javafx.stage.FileChooser;
import xyz.lauchschwert.tabmaker.exceptions.ImportException;
import xyz.lauchschwert.tabmaker.handler.ImportExportService;
import xyz.lauchschwert.tabmaker.logging.TmLogger;
import xyz.lauchschwert.tabmaker.ui.UserInterface;
import xyz.lauchschwert.tabmaker.ui.dialog.DialogService;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;

import java.io.File;

public class FileActions {
    private final UserInterface userInterface;

    public FileActions(final UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public void importAction() {
        File importFile = DialogService.OpenFileDialog(false,
                new FileChooser.ExtensionFilter("JSON Files", ImportExportService.VALID_IMPORTTYPE)
        );

        if (importFile == null || importFile.isDirectory() || !importFile.canRead()) {
            return;
        }

        try {
            InstrumentPanel panel = userInterface.getImportExportService().handleImport(importFile);
            userInterface.importPanel(panel);
        } catch (ImportException ex) {
            TmLogger.error("Import failed: " + ex.getMessage());
        }
    }

    public void exportAction(InstrumentPanel panel) {
        userInterface.getImportExportService().handleExport(panel);
    }
}
