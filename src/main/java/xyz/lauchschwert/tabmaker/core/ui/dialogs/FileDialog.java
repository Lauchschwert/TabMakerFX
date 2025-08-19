package xyz.lauchschwert.tabmaker.core.ui.dialogs;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import xyz.lauchschwert.tabmaker.core.ex.ExportException;
import xyz.lauchschwert.tabmaker.core.ex.ImportException;
import xyz.lauchschwert.tabmaker.core.services.importexport.TmImportExportService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileDialog {
    public static File GetFileViaFileChooser(ExtensionFilter... filters) throws ExportException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose an import file.");

        fc.getExtensionFilters().addAll(
                filters
        );

        fc.setInitialDirectory(TmImportExportService.SAVE_PATH.toFile());

        final File file = fc.showOpenDialog(null);

        if (!Files.exists(file.toPath())) {
            try {
                Files.createFile(file.toPath());
            } catch (IOException e) {
                throw new ExportException("Could not export to file: " + e.getMessage());
            }
        }

        if (fileInvalid(file)) {
            throw new ExportException("Could not import from target file. Please make sure the file is of valid .json format AND is readable!");
        }

        return file;
    }

    private static File handleNonExistingFile(File file) throws ExportException {


        return file;
    }

    public static File SaveFileViaFileChooser(ExtensionFilter... filters) throws ImportException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save file");
        fc.setInitialFileName(TmImportExportService.INITIAL_FILE_NAME);

        fc.getExtensionFilters().addAll(
                filters
        );

        fc.setInitialDirectory(TmImportExportService.SAVE_PATH.toFile());

        final File file = fc.showSaveDialog(null);
        if (file == null) {
            return null;
        }

        if (!Files.exists(file.toPath())) {
            try {
                Files.createFile(file.toPath());
            } catch (IOException e) {
                throw new ImportException("Could not generate export file: " + e.getMessage());
            }
        }

        if (fileInvalid(file)) {
            throw new ImportException("Could not import from target file. Please make sure the file is of valid .json format AND is readable!");
        }
        return file;
    }

    private static boolean fileInvalid(File file) {
        return file == null || file.isDirectory() || !file.canRead() || !file.canWrite();
    }
}
