package xyz.lauchschwert.tabmaker.ui.dialogs;

import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import xyz.lauchschwert.tabmaker.handler.ImportExportService;
import xyz.lauchschwert.tabmaker.ui.UserInterface;

import java.io.File;

public class DialogService {
    private final UserInterface userInterface;

    public DialogService(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public File getFileViaDialog() {
        File importFile = getFileViaFileChooser(false,
                new FileChooser.ExtensionFilter("JSON Files", ImportExportService.VALID_IMPORTTYPE) // later on text files etc....
        );
        if (importFile == null || importFile.isDirectory() || !importFile.canRead()) {
            return null;
        }
        return importFile;
    }

    public File getFileViaFileChooser(boolean save, FileChooser.ExtensionFilter... filters) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose an import file.");

        fc.getExtensionFilters().addAll(
                filters
        );

        fc.setInitialDirectory(ImportExportService.SAVE_PATH.toFile());

        if (save) {
            fc.setTitle("Save file");
            fc.setInitialFileName(ImportExportService.INITIAL_FILE_NAME);
            return fc.showSaveDialog(null);
        }
        return fc.showOpenDialog(null);
    }

    public String getStringViaTextDialog() {
        TextInputDialog tabNameDialog = new TextInputDialog("Default");
        tabNameDialog.setTitle("Enter Tab name");
        tabNameDialog.setHeaderText("Please enter the Tab name of the imported panel!\n(leave empty for default)");
        tabNameDialog.showAndWait();

        return tabNameDialog.getResult();
    }

//        if (file != null) {
//            try (FileWriter fw = new FileWriter(file, StandardCharsets.UTF_8);
//                 BufferedWriter bw = new BufferedWriter(fw)) {
//                bw.write(content);
//            } catch (IOException e) {
//                UserInterface.ShowAlert(
//                        Alert.AlertType.ERROR,
//                        "Save Error",
//                        "An error has occurred",
//                        "Failed to save file: " + e.getMessage()
//                );
//            }
//        }

}
