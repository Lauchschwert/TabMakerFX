package xyz.lauchschwert.tabmaker.enums;

public enum PanelSize {
    GUITAR(6), BASS(4);

    private final int length;

    PanelSize(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }
}
