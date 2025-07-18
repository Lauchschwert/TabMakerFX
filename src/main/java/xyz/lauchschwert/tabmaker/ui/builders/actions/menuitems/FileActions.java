package xyz.lauchschwert.tabmaker.ui.builders.actions.menuitems;

import javafx.scene.control.Tab;
import javafx.stage.FileChooser;
import xyz.lauchschwert.tabmaker.exceptions.*;
import xyz.lauchschwert.tabmaker.handler.ImportExportService;
import xyz.lauchschwert.tabmaker.logging.TmLogger;
import xyz.lauchschwert.tabmaker.ui.UserInterface;
import xyz.lauchschwert.tabmaker.services.DialogService;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.InstrumentPanel;
import xyz.lauchschwert.tabmaker.ui.tabs.TmTab;

import java.io.File;

public class FileActions {
    private final UserInterface userInterface;

    public FileActions(final UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public void importAction() throws ImportException {
        File importFile = null;
        try {
            importFile = DialogService.OpenFileDialog(false,
                    new FileChooser.ExtensionFilter("JSON Files", ImportExportService.VALID_IMPORTTYPE)
            );
        } catch (ImportException e) {
            throw new RuntimeException(e);
        } catch (ExportException ignored) {
        }

        if (importFile.isDirectory() || !importFile.canRead()) {
            return;
        }

        try {
            InstrumentPanel panel = userInterface.getImportExportService().handleImport(importFile);
            userInterface.importPanel(panel);
        } catch (ImportException ex) {
            TmLogger.error("Import failed: " + ex.getMessage());
        }
    }

    public void exportAction(Tab currentTab) {
        TmTab parsedTab = (TmTab) currentTab;
        if (parsedTab == null) {
            return;
        }
        InstrumentPanel panel = parsedTab.getInstrumentPanel();

        try {
            File destination = DialogService.OpenFileDialog(true, new FileChooser.ExtensionFilter(
                    "JSON Files", ImportExportService.VALID_IMPORTTYPE)
            );

            userInterface.getImportExportService().handleExport(destination, panel);
        } catch (ImportException ignored) {

        } catch (ExportException e) {
            TmLogger.warn("Export failed: " + e.getMessage());
        }
    }
}
