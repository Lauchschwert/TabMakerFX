package xyz.lauchschwert.tabmaker.core.enums;


import static xyz.lauchschwert.tabmaker.core.enums.Tunings.*;

public enum InstrumentType {
    GUITAR(STANDARD_GUITAR_TUNING),
    BASS(STANDARD_BASS_TUNING);

    private final Tunings[] tuning;

    InstrumentType(Tunings[] tuning) {
        this.tuning = tuning;
    }

    public Tunings[] getTuning() {
        return tuning.clone(); // Defensive copy
    }

    public int getStringCount() {
        return tuning.length;
    }
}
