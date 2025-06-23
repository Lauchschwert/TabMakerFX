module xyz.lauchschwert.tabmaker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires java.desktop;

    opens xyz.lauchschwert.tabmaker to javafx.fxml;
    exports xyz.lauchschwert.tabmaker;
}