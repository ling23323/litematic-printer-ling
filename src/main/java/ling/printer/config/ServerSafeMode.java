/*
 * Litematic Printer Ling — a modified version of Litematica Printer.
 * Copyright (C) 2026 Ling
 * Copyright (C) the Litematica Printer authors (see NOTICE.md)
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details: <https://www.gnu.org/licenses/>.
 */
package ling.printer.config;

import fi.dy.masa.malilib.config.IConfigBase;
import ling.printer.I18n;
import ling.printer.gui.ConfigUi;
import ling.printer.utils.minecraft.MessageUtils;

/**
 * 「服务器安全模式」预设开关。
 *
 * <p>公共服上最容易踩的两个雷是<b>放置太快</b>和<b>一次 tick 破坏太多方块</b>，
 * 而这两个值散落在「放置」和「破坏」两个页签里，来回改很麻烦。
 * 这个开关把它们打包成两套预设，一个热键就能在保守档和性能档之间切换：</p>
 *
 * <ul>
 *   <li><b>保守档</b>（开关开启）：放置间隔 / 每刻放置数 / 每刻破坏数取自
 *       {@code safePlaceInterval}、{@code safePlacementsPerTick}、{@code safeBreaksPerTick}，
 *       这三项随时可以在「核心」页签里改，等于自定义的安全档。</li>
 *   <li><b>性能档</b>（开关关闭）：0 tick 间隔、每刻 8 个、破坏不限制、关闭 RTT 自适应。</li>
 * </ul>
 *
 * <p>两条行为约定：</p>
 * <ol>
 *   <li>只有开关<b>真正翻转</b>时才套用预设，手动微调过的单项数值不会被每次启动覆盖。</li>
 *   <li>如果开关开着时你又手动改了上述任一速率项，说明你想自己控制节奏 ——
 *       这时会自动关掉安全模式，但<b>不会</b>用性能档覆盖你刚改的值。</li>
 * </ol>
 */
public final class ServerSafeMode {
    /** 性能档：单人/自建服可以拉到的上限。 */
    public static final int FAST_PLACE_INTERVAL = 0;
    public static final int FAST_BLOCKS_PER_TICK = 8;
    public static final int FAST_BREAK_BLOCKS_PER_TICK = 0;
    public static final boolean FAST_RTT_ADAPTIVE = false;

    /** 上一次已套用的值；null 表示还没同步过（首次切换一定生效）。 */
    private static Boolean lastApplied;
    /** 套用预设期间为 true，避免内部 set 触发「手动修改」判定。 */
    private static boolean applyingPreset;
    /** 因为用户手动改值而关闭时置位：下一次同步只记账，不套用性能档。 */
    private static boolean keepUserValues;

    private ServerSafeMode() {
    }

    /** 供安全模式开关的值变化回调使用。 */
    public static void sync(@SuppressWarnings("unused") IConfigBase config) {
        boolean safe = Configs.Core.SERVER_SAFE_MODE.getBooleanValue();
        if (lastApplied != null && lastApplied == safe) {
            return;
        }
        lastApplied = safe;
        if (keepUserValues) {
            // 是「手动改速率 → 自动关掉安全模式」这条路，保留用户刚设的值
            keepUserValues = false;
            MessageUtils.setOverlayMessage(I18n.SAFE_MODE_CUSTOM.getName());
            return;
        }
        apply(safe);
        MessageUtils.setOverlayMessage(
                safe ? I18n.SAFE_MODE_ON.getName() : I18n.SAFE_MODE_OFF.getName());
    }

    /**
     * 供放置/破坏速率项的值变化回调使用：安全模式开着时手动改这些项，等于放弃预设。
     */
    public static void onRateConfigChanged(@SuppressWarnings("unused") IConfigBase config) {
        if (applyingPreset || !Configs.Core.SERVER_SAFE_MODE.getBooleanValue()) {
            return;
        }
        keepUserValues = true;
        Configs.Core.SERVER_SAFE_MODE.setBooleanValue(false);
        Configs.saveToFile();
        ConfigUi.refresh();
    }

    /** 把速率相关配置套用为指定档位。 */
    public static void apply(boolean safe) {
        applyingPreset = true;
        try {
            Configs.Placement.PLACE_INTERVAL.setIntegerValue(safe
                    ? Configs.Core.SAFE_PLACE_INTERVAL.getIntegerValue() : FAST_PLACE_INTERVAL);
            Configs.Placement.PLACE_BLOCKS_PER_TICK.setIntegerValue(safe
                    ? Configs.Core.SAFE_PLACEMENTS_PER_TICK.getIntegerValue() : FAST_BLOCKS_PER_TICK);
            Configs.Break.BREAK_BLOCKS_PER_TICK.setIntegerValue(safe
                    ? Configs.Core.SAFE_BREAKS_PER_TICK.getIntegerValue() : FAST_BREAK_BLOCKS_PER_TICK);
            Configs.Placement.RTT_ADAPTIVE_INTERVAL.setBooleanValue(safe);
        } finally {
            applyingPreset = false;
        }
        Configs.saveToFile();
        ConfigUi.refresh();
    }

    /** 初始化时调用，确保第一次切换一定会套用预设。 */
    public static void resetTracking() {
        lastApplied = null;
        keepUserValues = false;
        applyingPreset = false;
    }
}
