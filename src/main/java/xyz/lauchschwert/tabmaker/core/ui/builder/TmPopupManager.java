package xyz.lauchschwert.tabmaker.core.ui.builder;

import xyz.lauchschwert.tabmaker.core.ui.popups.TmPopup;

import java.util.function.Consumer;

public class TmPopupManager {
  public static TmPopup createPopup(Consumer<String> onButtonClick, String[] buttonNames, int buttonsPerRow) {
    return new TmPopup(onButtonClick, buttonNames, buttonsPerRow);
  }

  public static TmPopup createPopup(Consumer<String> onButtonClick, int buttonNames, int buttonsPerRow) {

    String[] buttonNamesArray = new String[buttonNames + 1];
    for (int i = 0; i < buttonNames + 1; i++) {
      buttonNamesArray[i] = String.valueOf(i);
    }

    return createPopup(onButtonClick, buttonNamesArray, buttonsPerRow);
  }
}
