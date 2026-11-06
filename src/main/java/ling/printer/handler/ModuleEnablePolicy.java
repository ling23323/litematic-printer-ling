package ling.printer.handler;

import ling.printer.utils.ConfigUtils;

/** Resolves global, single-mode and multi-mode enable settings for a feature. */
final class ModuleEnablePolicy {
    private ModuleEnablePolicy() {
    }

    static boolean allows(FeatureModuleBase module) {
        if (!ConfigUtils.isEnable()) return false;
        if (module.printMode != null && module.enableConfig != null) {
            return ConfigUtils.isFeatureActive(module.printMode, module.enableConfig);
        }
        return module.enableConfig == null || module.enableConfig.getBooleanValue();
    }
}
