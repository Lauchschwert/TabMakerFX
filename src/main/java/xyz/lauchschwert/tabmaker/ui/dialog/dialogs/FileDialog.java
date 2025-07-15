package xyz.lauchschwert.tabmaker.ui.dialog.dialogs;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import xyz.lauchschwert.tabmaker.handler.ImportExportService;

import java.io.File;

public class FileDialog {
    public static File GetFileViaFileChooser(boolean save, ExtensionFilter... filters) {
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
        if (!isFileValid(file)) {
            return null;
        }
        return file;
    }

    private static boolean isFileValid(File file) {
        return file != null && !file.isDirectory() && file.canRead();
    }
}
