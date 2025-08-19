package xyz.lauchschwert.tabmaker.core.services.importexport;

import xyz.lauchschwert.tabmaker.core.ex.ExportException;
import xyz.lauchschwert.tabmaker.core.ex.ImportException;
import xyz.lauchschwert.tabmaker.core.ui.components.panels.InstrumentPanel;

import java.io.File;

public interface ImportExportService {
    void handleInstrumentPanelExport(File destination, InstrumentPanel exportPanel) throws ExportException;
    InstrumentPanel handleInstrumentPanelImport(File importFile) throws ImportException;
}
