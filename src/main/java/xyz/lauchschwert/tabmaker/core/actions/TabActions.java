package xyz.lauchschwert.tabmaker.core.actions;

import xyz.lauchschwert.tabmaker.core.logging.TmLogger;
import xyz.lauchschwert.tabmaker.core.ui.UserInterface;
import xyz.lauchschwert.tabmaker.core.ui.components.panels.InstrumentPanel;

public class TabActions {
    private final UserInterface userInterface;

    public TabActions(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public void createNewTab() {
        // Build a new InstrumentPanel
        InstrumentPanel instrumentPanel = userInterface.getDialogService().GetInstrumentPanelViaDialog();

        if (instrumentPanel == null) {
            TmLogger.warn("Instrument Panel Builder did not succeed in building an instrument panel.");
        } else {
            // Create and set panel inside a new tab
            userInterface.createNewTab(instrumentPanel);
            TmLogger.info("Instrument Panel Builder succeeded in adding another panel.");
        }
    }
}
