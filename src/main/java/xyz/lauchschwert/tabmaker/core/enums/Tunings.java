package xyz.lauchschwert.tabmaker.core.enums;

import java.util.Arrays;
import java.util.Comparator;

public enum Tunings {
    LOW_E("E", 0),
    A("A", 1),
    D("D", 2),
    G("G", 3),
    B("B", 4),
    HIGH_E("^E", 5),

    // Additional chromatic notes for alternate tunings
    C("C", 6),
    C_SHARP("C#", 7),
    D_SHARP("D#", 8),
    F("F", 9),
    F_SHARP("F#", 10),
    G_SHARP("G#", 11),
    A_SHARP("A#", 12);

    private final String note;
    private final int index;

    Tunings(String note, int index) {
        this.note = note;
        this.index = index;
    }

    public static String[] GetAllTunings() {
        return Arrays.stream(values())
                .sorted(Comparator.comparingInt(Tunings::getIndex))
                .map(Tunings::getNote)
                .toArray(String[]::new);
    }

    public String getNote() {
        return note;
    }

    public int getIndex() {
        return index;
    }

    // Define tuning arrays
    public static final Tunings[] STANDARD_GUITAR_TUNING =
            {LOW_E, A, D, G, B, HIGH_E};

    public static final Tunings[] STANDARD_BASS_TUNING =
            {LOW_E, A, D, G};
}