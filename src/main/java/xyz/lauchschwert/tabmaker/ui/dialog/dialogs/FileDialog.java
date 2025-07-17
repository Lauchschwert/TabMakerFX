package xyz.lauchschwert.tabmaker.ui.dialog.dialogs;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import xyz.lauchschwert.tabmaker.exceptions.ImportException;
import xyz.lauchschwert.tabmaker.handler.ImportExportService;
import xyz.lauchschwert.tabmaker.logging.TmLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.rmi.server.ExportException;

public class FileDialog {
    public static File GetFileViaFileChooser(boolean save, ExtensionFilter... filters) throws ImportException, ExportException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose an import file.");

        fc.getExtensionFilters().addAll(
                filters
        );

        fc.setInitialDirectory(ImportExportService.SAVE_PATH.toFile());

        File file;
        if (save) {
            fc.setTitle("Save file");
            fc.setInitialFileName(ImportExportService.INITIAL_FILE_NAME);
            file = fc.showSaveDialog(null);
        } else {
            file = fc.showOpenDialog(null);
        }

        if (!file.exists() && save) {
            try {
                Files.createFile(file.toPath());
            } catch (IOException e) {
                TmLogger.warn("Could not generate File: " + e.getMessage());
                return null;
            }
        }

        if (fileInvalid(file) && save) {
            throw new ExportException("Could not export to target file. Please make sure the file is of .json format!");
        } else if (fileInvalid(file) && !save) {
            throw new ImportException("Could not import from target file. Please make sure the file is of .json format!");
        }
        return file;
    }

    private static boolean fileInvalid(File file) {
        return file == null || file.isDirectory() || !file.canRead() || !file.canWrite();
    }
}
