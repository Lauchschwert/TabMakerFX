package xyz.lauchschwert.tabmaker.core.services.dialog;

import javafx.scene.control.TextInputDialog;
import xyz.lauchschwert.tabmaker.core.ui.dialogs.InstrumentPanelDialog;
import xyz.lauchschwert.tabmaker.core.ui.components.panels.InstrumentPanel;

import java.util.Optional;

public class TmDialogService implements DialogService {
    @Override
    public String GetTabNameViaDialog() {
        TextInputDialog tabNameDialog = new TextInputDialog("Default");
        tabNameDialog.setTitle("Enter Tab name");
        tabNameDialog.setHeaderText("Please enter a name for the imported panel!\n(leave empty for default)");
        tabNameDialog.showAndWait();

        return tabNameDialog.getResult();
    }

    @Override
    public InstrumentPanel GetInstrumentPanelViaDialog() {
        InstrumentPanelDialog instrumentPanelDialog = new InstrumentPanelDialog();
        Optional<InstrumentPanel> instrumentPanel = instrumentPanelDialog.showAndWait();
        return instrumentPanel.orElse(null);
    }
}
