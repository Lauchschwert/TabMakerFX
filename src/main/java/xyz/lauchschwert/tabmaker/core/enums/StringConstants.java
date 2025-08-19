package xyz.lauchschwert.tabmaker.core.enums;

public enum StringConstants {
    // Standard tuning (first 5 from your array)
    LOW_E("E", 0),
    A("A", 1),
    D("D", 2),
    G("G", 3),
    B("B", 4),
    HIGH_E("^E", 5),

    // Additional chromatic notes for alternate tunings
    C("C", 5),
    C_SHARP("C#", 6),
    D_SHARP("D#", 7),
    F("F", 8),
    F_SHARP("F#", 9),
    G_SHARP("G#", 10),
    A_SHARP("A#", 11);

    private final String note;
    private final int index;

    StringConstants(String note, int index) {
        this.note = note;
        this.index = index;
    }

    public String getNote() {
        return note;
    }

    public int getIndex() {
        return index;
    }

    // Define tuning arrays
    public static final StringConstants[] STANDARD_GUITAR_TUNING =
            {LOW_E, A, D, G, B, HIGH_E};

    public static final StringConstants[] BASS_TUNING =
            {LOW_E, A, D, G};
}