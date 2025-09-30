package xyz.lauchschwert.tabmaker.core.enums;


import static xyz.lauchschwert.tabmaker.core.enums.TuningConstants.*;

public enum InstrumentType {
    GUITAR(STANDARD_GUITAR_TUNING),
    BASS(STANDARD_BASS_TUNING);

    private final TuningConstants[] tuning;

    InstrumentType(TuningConstants[] tuning) {
        this.tuning = tuning;
    }

    public TuningConstants[] getTuning() {
        return tuning.clone(); // Defensive copy
    }

    public int getStringCount() {
        return tuning.length;
    }
}
