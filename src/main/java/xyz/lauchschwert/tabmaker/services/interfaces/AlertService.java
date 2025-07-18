package xyz.lauchschwert.tabmaker.services.interfaces;

import javafx.scene.control.Alert;

public interface AlertService {
    void ShowAlert(Alert.AlertType type, String title, String header, String content);
}
