module xyz.lauchschwert.tabmaker {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.desktop;
    requires com.google.gson;
    requires java.rmi;
    requires java.logging;

    opens xyz.lauchschwert.tabmaker to javafx.fxml;
    exports xyz.lauchschwert.tabmaker;
    exports xyz.lauchschwert.tabmaker.logging;
    opens xyz.lauchschwert.tabmaker.logging to javafx.fxml;
}