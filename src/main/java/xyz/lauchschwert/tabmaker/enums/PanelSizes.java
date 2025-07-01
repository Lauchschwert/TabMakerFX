package xyz.lauchschwert.tabmaker.enums;

public enum PanelSizes {
    GUITAR(6), BASS(4);

    private final int length;

    PanelSizes(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }
}
