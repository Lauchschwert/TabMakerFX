package xyz.lauchschwert.tabmaker.ui.builders.actions.tabitems;

import xyz.lauchschwert.tabmaker.logging.TmLogger;
import xyz.lauchschwert.tabmaker.ui.UserInterface;
import xyz.lauchschwert.tabmaker.ui.dialog.dialogs.InstrumentPanelDialog;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;

public class TabActions {
    private final UserInterface userInterface;

    public TabActions(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public void addNewTab() {
        // Build a new InstrumentPanel
        InstrumentPanelDialog ipb = new InstrumentPanelDialog();
        ipb.showAndWait();
        InstrumentPanel instrumentPanel = ipb.getResult();

        if (instrumentPanel == null) {
            TmLogger.warn("Instrument Panel Builder did not succeed in building an instrument panel.");
        } else {
            // Create and set panel inside a new tab
            userInterface.createNewTab(instrumentPanel);
            TmLogger.info("Instrument Panel Builder succeeded in adding another panel");
        }
    }
}
