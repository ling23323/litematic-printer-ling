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
package ling.printer.enums;

import ling.printer.I18n;
import ling.printer.config.ConfigOptionListEntry;

/**
 * 遍历方向：候选方块按哪个方向优先被尝试。
 *
 * <p>{@link #NEAREST} 是原来的行为（离玩家最近优先）。其余选项把扫描的排序中心推到区域外侧，
 * 于是游标沿该轴单调推进：挖掘会从最底层一路往上、或者从北往南一层层剥，
 * 而不是绕着玩家画圈。</p>
 */
public enum IterationDirectionType implements ConfigOptionListEntry<IterationDirectionType> {
    NEAREST("iterationDirection.nearest"),
    BOTTOM_UP("iterationDirection.bottomUp"),
    TOP_DOWN("iterationDirection.topDown"),
    NORTH_TO_SOUTH("iterationDirection.northToSouth"),
    SOUTH_TO_NORTH("iterationDirection.southToNorth"),
    WEST_TO_EAST("iterationDirection.westToEast"),
    EAST_TO_WEST("iterationDirection.eastToWest");

    private final I18n i18n;

    IterationDirectionType(String translateKey) {
        this.i18n = I18n.of(translateKey);
    }

    @Override
    public I18n getI18n() {
        return i18n;
    }

    /** 是否启用了某个轴的单调遍历。 */
    public boolean isDirectional() {
        return this != NEAREST;
    }
}
