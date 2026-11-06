package ling.printer.enums;

import ling.printer.I18n;
import ling.printer.config.ConfigOptionListEntry;

public enum WorkingModeType implements ConfigOptionListEntry<WorkingModeType> {
    MULTI("modeType.multi"),
    SINGLE("modeType.single");

    private final I18n i18n;

    WorkingModeType(String translateKey) {
        this.i18n = I18n.of(translateKey);
    }

    @Override
    public I18n getI18n() {
        return i18n;
    }
}
