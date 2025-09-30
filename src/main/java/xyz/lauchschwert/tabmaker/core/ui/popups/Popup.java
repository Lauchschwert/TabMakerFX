package xyz.lauchschwert.tabmaker.core.ui.popups;

import javafx.scene.Node;

public interface Popup {
  void initContents(String[] buttonNames, int buttonsPerRow);

  void show(Node owner, double xPos, double yPos);

  void dispose();
}
