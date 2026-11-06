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

import fi.dy.masa.litematica.world.WorldSchematic;
import ling.printer.placement.PrinterBox;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

/**
 * 一次扫描请求的完整参数。
 *
 * <p>扫描入口原先有 5 个只差一两个参数的重载（单个盒子/多个盒子、带不带 preFilter、
 * 有没有守卫上限），调用方与实现两侧各写一遍同样的参数列表。改成参数对象后，
 * 「一次扫描到底需要哪些东西」只在这里定义一次，{@link ScanCache} 与
 * {@link ScanEngine} 各自只剩一个真正的实现方法。</p>
 *
 * <p>字段语义：</p>
 * <ul>
 *   <li>{@code sourceBoxes}：本次要遍历的选区；空表示没有可扫描范围</li>
 *   <li>{@code level} / {@code schematic}：世界快照；{@code raw} 搜索两者都为 null</li>
 *   <li>{@code player}：可达性判定的中心</li>
 *   <li>{@code exactPredicate}：最终候选判定，命中才会进入结果集</li>
 *   <li>{@code preFilter}：廉价预筛，在可达/选区过滤之前执行，用于尽早剪枝</li>
 *   <li>{@code unbounded}：放弃守卫上限，只用于需要完整结果集的一次性调用</li>
 * </ul>
 */
public record ScanQuery(
        String ownerKey,
        List<PrinterBox> sourceBoxes,
        @Nullable ClientLevel level,
        @Nullable WorldSchematic schematic,
        @Nullable LocalPlayer player,
        int scanGuardLimit,
        ScanIntent intent,
        Predicate<BlockPos> exactPredicate,
        Predicate<BlockPos> preFilter,
        boolean unbounded
) {
    /** 预筛/判定谓词的「全部接受」占位，避免各处重复写同一个 lambda。 */
    private static final Predicate<BlockPos> ACCEPT_ALL = pos -> true;

    public ScanQuery {
        // 不复制选区列表：扫描在每个 tick 每个功能各调用一次，这里保持零额外分配。
        sourceBoxes = sourceBoxes == null ? List.of() : sourceBoxes;
    }

    public static Predicate<BlockPos> acceptAll() {
        return ACCEPT_ALL;
    }

    /** 多选区查询；不带预筛。 */
    public static ScanQuery boxes(
            String ownerKey,
            List<PrinterBox> sourceBoxes,
            @Nullable ClientLevel level,
            @Nullable WorldSchematic schematic,
            @Nullable LocalPlayer player,
            int scanGuardLimit,
            ScanIntent intent,
            Predicate<BlockPos> exactPredicate
    ) {
        return boxes(ownerKey, sourceBoxes, level, schematic, player, scanGuardLimit, intent, exactPredicate, ACCEPT_ALL);
    }

    /** 多选区查询；{@code preFilter} 为廉价预筛。 */
    public static ScanQuery boxes(
            String ownerKey,
            List<PrinterBox> sourceBoxes,
            @Nullable ClientLevel level,
            @Nullable WorldSchematic schematic,
            @Nullable LocalPlayer player,
            int scanGuardLimit,
            ScanIntent intent,
            Predicate<BlockPos> exactPredicate,
            Predicate<BlockPos> preFilter
    ) {
        return new ScanQuery(ownerKey, sourceBoxes, level, schematic, player, scanGuardLimit,
                intent, exactPredicate, preFilter, false);
    }

    /** 单选区查询；{@code preFilter} 为廉价预筛。 */
    public static ScanQuery box(
            String ownerKey,
            PrinterBox sourceBox,
            @Nullable ClientLevel level,
            @Nullable WorldSchematic schematic,
            @Nullable LocalPlayer player,
            int scanGuardLimit,
            ScanIntent intent,
            Predicate<BlockPos> exactPredicate,
            Predicate<BlockPos> preFilter
    ) {
        return boxes(ownerKey, List.of(sourceBox), level, schematic, player, scanGuardLimit,
                intent, exactPredicate, preFilter);
    }

    /** 单选区查询；不带预筛。 */
    public static ScanQuery box(
            String ownerKey,
            PrinterBox sourceBox,
            @Nullable ClientLevel level,
            @Nullable WorldSchematic schematic,
            @Nullable LocalPlayer player,
            int scanGuardLimit,
            ScanIntent intent,
            Predicate<BlockPos> exactPredicate
    ) {
        return box(ownerKey, sourceBox, level, schematic, player, scanGuardLimit, intent, exactPredicate, ACCEPT_ALL);
    }

    /** 单选区、不限守卫上限的查询，用于需要拿到完整结果集的一次性遍历。 */
    public static ScanQuery unbounded(
            String ownerKey,
            PrinterBox sourceBox,
            @Nullable ClientLevel level,
            @Nullable WorldSchematic schematic,
            @Nullable LocalPlayer player,
            ScanIntent intent,
            Predicate<BlockPos> exactPredicate,
            Predicate<BlockPos> preFilter
    ) {
        return new ScanQuery(ownerKey, List.of(sourceBox), level, schematic, player, Integer.MAX_VALUE,
                intent, exactPredicate, preFilter, true);
    }

    /** 纯位置搜索：不涉及原理图世界，也不带守卫上限。 */
    public static ScanQuery raw(
            String ownerKey,
            PrinterBox sourceBox,
            @Nullable LocalPlayer player,
            int scanGuardLimit,
            Predicate<BlockPos> preFilter
    ) {
        return new ScanQuery(ownerKey, List.of(sourceBox), null, null, player, scanGuardLimit,
                ScanIntent.CUSTOM, ACCEPT_ALL, preFilter, false);
    }
}
