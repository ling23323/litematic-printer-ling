package ling.printer.enums;

import ling.printer.I18n;
import ling.printer.config.ConfigOptionListEntry;

public enum PrintModeType implements ConfigOptionListEntry<PrintModeType> {
    PRINTER("printMode.printer"),
    MINE("printMode.mine"),
    FLUID("printMode.fluid"),
    FILL("printMode.fill"),
    COVER("printMode.cover"),
    // REPLACE("printMode.replace"),
    BEDROCK("printMode.bedrock"),
    /** 自定义：单模式下也按各功能开关运行，可以只开打印+挖掘+填充这类组合。 */
    CUSTOM("printMode.custom");

    private final I18n i18n;

    PrintModeType(String translateKey) {
        this.i18n = I18n.of(translateKey);
    }

    @Override
    public I18n getI18n() {
        return i18n;
    }
}
