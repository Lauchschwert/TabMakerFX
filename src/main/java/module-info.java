module xyz.lauchschwert.tabmaker {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.desktop;
    requires com.google.gson;
    requires java.rmi;
    requires java.logging;
    requires javafx.graphics;
    requires xyz.lauchschwert.tabmaker;

    opens xyz.lauchschwert.tabmaker to javafx.fxml;
    exports xyz.lauchschwert.tabmaker;
    exports xyz.lauchschwert.tabmaker.core.logging;
    exports xyz.lauchschwert.tabmaker.core.enums;
    opens xyz.lauchschwert.tabmaker.core.logging to javafx.fxml;
    exports xyz.lauchschwert.tabmaker.core.logging.formatter;
    opens xyz.lauchschwert.tabmaker.core.logging.formatter to javafx.fxml;
}