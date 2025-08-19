package xyz.lauchschwert.tabmaker.core.enums;

public enum InstrumentType {
    GUITAR(StringConstants.STANDARD_GUITAR_TUNING),
    BASS(StringConstants.BASS_TUNING);

    private final StringConstants[] tuning;

    InstrumentType(StringConstants[] tuning) {
        this.tuning = tuning;
    }

    public StringConstants[] getTuning() {
        return tuning.clone(); // Defensive copy
    }

    public int getStringCount() {
        return tuning.length;
    }
}
