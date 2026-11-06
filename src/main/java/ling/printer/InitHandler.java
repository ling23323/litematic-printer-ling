package ling.printer;

import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import ling.printer.gui.ConfigUi;
import ling.printer.config.Configs;
import ling.printer.config.ServerSafeMode;
import ling.printer.enums.PrintModeType;
import ling.printer.runtime.RuntimeAccess;
import ling.printer.handler.handlers.bedrock.BedrockController;
import ling.printer.integration.quickshulker.HighlightBlockRenderer;
import ling.printer.utils.minecraft.MessageUtils;
import ling.printer.utils.mods.QuickShulkerBridge;

import static ling.printer.config.Configs.*;

public class InitHandler implements IInitializationHandler {
    @Override
    public void registerModHandlers() {
        Configs.init();
        initConfigCallback();
        HighlightBlockRenderer.init();  // 高亮显示方块渲染器
    }

    private void initConfigCallback() {
        // 服务器安全模式：只有开关真的翻转时才套用预设，手动微调的值不会被覆盖
        ServerSafeMode.resetTracking();
        Core.SERVER_SAFE_MODE.setValueChangeCallback(ServerSafeMode::sync);
        // 手动改这三项速率 = 放弃预设，自动退出安全模式（保留你刚设的值）
        Placement.PLACE_INTERVAL.setValueChangeCallback(ServerSafeMode::onRateConfigChanged);
        Placement.PLACE_BLOCKS_PER_TICK.setValueChangeCallback(ServerSafeMode::onRateConfigChanged);
        Break.BREAK_BLOCKS_PER_TICK.setValueChangeCallback(ServerSafeMode::onRateConfigChanged);

        Hotkeys.CLOSE_ALL_MODE.getKeybind().setCallback((action, keybind) -> {
            if (keybind.isKeybindHeld()) {
                Core.MINE.setBooleanValue(false);
                Core.FLUID.setBooleanValue(false);
                Core.WORK_SWITCH.setBooleanValue(false);
                Core.WORK_MODE_TYPE.setOptionListValue(PrintModeType.PRINTER);
                MessageUtils.setOverlayMessage(I18n.CLOSE_ALL_MODE_NOTICE.getName());
            }
            return true;
        });

        // 工作开关
        Core.WORK_SWITCH.setValueChangeCallback(b -> {
            if (!b.getBooleanValue()) {
                RuntimeAccess.get().reset("work_switch_off");
            }
        });

        Placement.QUICK_SHULKER.setValueChangeCallback(b -> {
            if (!b.getBooleanValue()) {
                QuickShulkerBridge.resetRuntime();
            }
            Configs.saveToFile();
        });

        // 切换模式时, 关闭破基岩
        Core.WORK_MODE_TYPE.setValueChangeCallback(b -> {
            if (!b.getOptionListValue().equals(PrintModeType.BEDROCK)) {
                BedrockController.reset();
            }
            // 切到「自定义」时功能开关才会出现，需要重画配置页
            ConfigUi.refresh();
        });

        // 特殊设置时，自动刷新界面
        Core.WORK_MODE.setValueChangeCallback(b -> ConfigUi.refresh());
        Print.FILL_COMPOSTER.setValueChangeCallback(b -> ConfigUi.refresh());
        Print.PRINT_RESERVE_ITEMS.setValueChangeCallback(b -> ConfigUi.refresh());
        Break.BREAK_LIMITER.setValueChangeCallback(b -> ConfigUi.refresh());
        Break.BREAK_LIMIT.setValueChangeCallback(b -> ConfigUi.refresh());
        Mine.EXCAVATE_LIMITER.setValueChangeCallback(b -> ConfigUi.refresh());
        Mine.EXCAVATE_LIMIT.setValueChangeCallback(b -> ConfigUi.refresh());
        Mine.MINE_TRENCH_MODE.setValueChangeCallback(b -> ConfigUi.refresh());
        Fill.FILL_BLOCK_MODE.setValueChangeCallback(b -> ConfigUi.refresh());
        Core.LAG_CHECK.setValueChangeCallback(b -> ConfigUi.refresh());
        Core.RENDER_HUD.setValueChangeCallback(b -> ConfigUi.refresh());
        Core.MISSING_MATERIAL_HUD.setValueChangeCallback(b -> ConfigUi.refresh());
        Configs.Placement.RTT_ADAPTIVE_INTERVAL.setValueChangeCallback(b -> {
            ServerSafeMode.onRateConfigChanged(b);
            ConfigUi.refresh();
        });
        Break.BREAK_USE_DELAYED_DESTROY.setValueChangeCallback(b -> ConfigUi.refresh());
        Special.REMOTE_TAKE.setValueChangeCallback(b -> {
            ConfigUi.refresh();
            if (!b.getBooleanValue()) {
                RuntimeAccess.get().chestTrackerAdapter().reset();
            }
        });
    }
}
