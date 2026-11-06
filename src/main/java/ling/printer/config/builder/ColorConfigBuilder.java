package ling.printer.config.builder;

import fi.dy.masa.malilib.config.options.ConfigColor;
import ling.printer.I18n;
import ling.printer.config.PrinterConfigOptions;

public class ColorConfigBuilder extends BaseConfigBuilder<ConfigColor, ColorConfigBuilder> {
    private String defaultValue = "";

    public ColorConfigBuilder(I18n i18n) {
        super(i18n);
    }

    public ColorConfigBuilder(String translateKey) {
        this(I18n.of(translateKey));
    }

    public ColorConfigBuilder defaultValue(String value) {
        this.defaultValue = value;
        return this;
    }

    @Override
    public ConfigColor build() {
        ConfigColor config = new PrinterConfigOptions.ColorOption(i18n.getNameKey(), defaultValue, descKey);
        return buildExtension(config);
    }
}
