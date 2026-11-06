package ling.printer.handler.handlers;

import ling.printer.config.Configs;
import ling.printer.handler.FeatureModuleBase;
import ling.printer.runtime.PrinterRuntime;

public class GuiHandler extends FeatureModuleBase {
    public static final String NAME = "gui";

    public GuiHandler(PrinterRuntime runtime) {
        super(runtime, NAME, null, Configs.Core.RENDER_HUD, null, false);
    }
}
