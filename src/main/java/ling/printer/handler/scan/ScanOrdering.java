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
package ling.printer.handler.scan;

import ling.printer.config.Configs;
import ling.printer.enums.IterationDirectionType;

/**
 * 把「遍历方向」翻译成游标能用的排序中心与半径上限。
 *
 * <p>{@link BoxDistanceCursor} 的排序完全由图心的距离决定：把某一个轴的图心推到区域外很远，
 * 那个轴上的坐标就会单调推进，于是扫描从「围绕玩家扩散」变成「沿某个方向扫过去」。
 * 这时不能再按玩家半径剪枝（距离都被人为放大了），所以半径上限一并放开，
 * 到达范围仍由迭代层的可达性过滤保证。</p>
 */
final class ScanOrdering {
    /** 足够远的虚拟中心：超过世界边界两个数量级，坐标差值仍在 long 安全范围内。 */
    private static final int FAR_AXIS = 1 << 24;

    private ScanOrdering() {
    }

    record Ordering(int centerX, int centerY, int centerZ, int maxDistanceBand) {
    }

    static IterationDirectionType direction() {
        Object value = Configs.Core.ITERATION_DIRECTION.getOptionListValue();
        return value instanceof IterationDirectionType type ? type : IterationDirectionType.NEAREST;
    }

    static Ordering of(ScanRegion region) {
        IterationDirectionType direction = direction();
        if (!direction.isDirectional()) {
            return new Ordering(region.centerX(), region.centerY(), region.centerZ(), region.maxDistanceBand());
        }
        int centerX = region.centerX();
        int centerY = region.centerY();
        int centerZ = region.centerZ();
        switch (direction) {
            case BOTTOM_UP -> centerY = -FAR_AXIS;
            case TOP_DOWN -> centerY = FAR_AXIS;
            case NORTH_TO_SOUTH -> centerZ = -FAR_AXIS;
            case SOUTH_TO_NORTH -> centerZ = FAR_AXIS;
            case WEST_TO_EAST -> centerX = -FAR_AXIS;
            case EAST_TO_WEST -> centerX = FAR_AXIS;
            default -> {
            }
        }
        return new Ordering(centerX, centerY, centerZ, Integer.MAX_VALUE);
    }
}
