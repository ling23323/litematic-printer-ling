package ling.printer.enums;

import ling.printer.I18n;
import ling.printer.config.ConfigOptionListEntry;

public enum QuickShulkerModeType implements ConfigOptionListEntry<QuickShulkerModeType> {
    CLICK_SLOT("quickShulkerMode.click_slot"),
    INVOKE("quickShulkerMode.invoke");

    private final I18n i18n;

    QuickShulkerModeType(String translateKey) {
        this.i18n = I18n.of(translateKey);
    }

    @Override
    public I18n getI18n() {
        return i18n;
    }
}
