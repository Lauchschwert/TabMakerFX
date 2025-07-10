package xyz.lauchschwert.tabmaker.ui;

import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import xyz.lauchschwert.tabmaker.TabMaker;
import xyz.lauchschwert.tabmaker.handler.ImportExportHandler;

public class UserInterface {
    private final TabMaker tabMaker;
    private VBox root;
    private TabPane tabPanelPane;
    private ImportExportHandler importExportHandler;

    public UserInterface(TabMaker tabMaker) {
        this.tabMaker = tabMaker;
        initComponents();
    }

    private void initComponents() {
        this.root = new VBox();
        this.tabPanelPane = new TabPane();
    }

    public Scene createScene() {
        configureComponents();
        return new Scene(this.root);
    }

    private void configureComponents() {

    }

    public VBox getRoot() {
        return root;
    }

    public void setRoot(VBox root) {
        this.root = root;
    }

    public ImportExportHandler getImportExportHandler() {
        return importExportHandler;
    }

    public void setImportExportHandler(ImportExportHandler importExportHandler) {
        this.importExportHandler = importExportHandler;
    }
}
