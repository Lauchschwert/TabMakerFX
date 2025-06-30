module xyz.lauchschwert.tabmaker {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.desktop;
    requires com.google.gson;
    requires java.rmi;

    opens xyz.lauchschwert.tabmaker to javafx.fxml;
    exports xyz.lauchschwert.tabmaker;
}