package xyz.lauchschwert.tabmaker.core.actions;

import javafx.scene.control.Tab;
import javafx.stage.FileChooser;
import xyz.lauchschwert.tabmaker.core.ex.ExportException;
import xyz.lauchschwert.tabmaker.core.ex.ImportException;
import xyz.lauchschwert.tabmaker.core.services.dialog.DialogService;
import xyz.lauchschwert.tabmaker.core.services.importexport.TmImportExportService;
import xyz.lauchschwert.tabmaker.core.logging.TmLogger;
import xyz.lauchschwert.tabmaker.core.services.file.TmFileService;
import xyz.lauchschwert.tabmaker.core.ui.UserInterface;
import xyz.lauchschwert.tabmaker.core.ui.components.panels.InstrumentPanel;
import xyz.lauchschwert.tabmaker.core.ui.components.buttons.TmTab;

import java.io.File;

public class FileActions {
    private final TmImportExportService importExportService;
    private final UserInterface userInterface;

    public FileActions(UserInterface userInterface) {
        this.importExportService = new TmImportExportService();
        this.userInterface = userInterface;
    }

    public void importAction(File importFile) throws ImportException {
        try {
            TmFileService.validateFileForReading(importFile);
        } catch (IllegalArgumentException e) {
            throw new ImportException(e.getMessage());
        }

        try {
            InstrumentPanel panel = importExportService.handleInstrumentPanelImport(importFile);
            userInterface.importPanel(panel);
        } catch (ImportException ex) {
            TmLogger.error("Import failed: " + ex.getMessage());
        }
    }

    public void exportAction(Tab currentTab) {
        final TmTab parsedTab = (TmTab) currentTab;
        if (parsedTab == null) {
            return;
        }
        InstrumentPanel panel = parsedTab.getInstrumentPanel();

        try {
            File destination = DialogService.OpenSaveDialog(new FileChooser.ExtensionFilter(
                    "JSON Files", TmImportExportService.VALID_IMPORTTYPE)
            );

            importExportService.handleInstrumentPanelExport(destination, panel);
        } catch (ExportException e) {
            TmLogger.warn("Export failed: " + e.getMessage());
        }
    }
}