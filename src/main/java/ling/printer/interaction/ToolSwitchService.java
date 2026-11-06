package ling.printer.interaction;

import ling.printer.integration.tweakeroo.TweakerooToolSwitchPort;
import ling.printer.utils.InventorySwitchGuard;
import ling.printer.utils.InventoryUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;

/** Coordinates Printer's ordinary effective-tool selection with Tweakeroo's own tool policies. */
public final class ToolSwitchService {
    private final Minecraft client;
    private final TweakerooToolSwitchPort tweakeroo;
    private final InventorySwitchGuard switchGuard;

    public ToolSwitchService(
            Minecraft client,
            TweakerooToolSwitchPort tweakeroo,
            InventorySwitchGuard switchGuard
    ) {
        this.client = client;
        this.tweakeroo = tweakeroo;
        this.switchGuard = switchGuard;
    }

    /**
     * Prepares the hand for one damage-producing break packet.
     *
     * <p>工具选择完全交给 Tweakeroo：本模组不再内置“自动切换工具”逻辑，
     * 避免两套选择器互相覆盖。Tweakeroo 没装或没开自动切工具时，就保持玩家当前手持物品。</p>
     */
    public ToolPreparationResult prepareForBreak(BlockPos pos, BlockState state, boolean allowEffectiveSwitch) {
        LocalPlayer player = this.client.player;
        if (player == null || player.getAbilities().instabuild || pos == null || state == null
                || state.isAir() || state.getBlock() instanceof LiquidBlock) {
            return player == null ? ToolPreparationResult.UNAVAILABLE : ToolPreparationResult.READY;
        }
        if (this.switchGuard.isWaiting()) {
            return ToolPreparationResult.SWITCHED_WAITING_SYNC;
        }

        int beforeSlot = InventoryUtils.getSelectedSlot(player.getInventory());
        ItemStack before = player.getMainHandItem().copy();
        if (allowEffectiveSwitch && this.tweakeroo.isEffectiveToolSwitchEnabled()) {
            this.tweakeroo.switchToEffectiveTool(pos);
        }
        if (this.tweakeroo.isDurabilityGuardActive()
                && !this.tweakeroo.prepareCurrentTool(pos, state)) {
            return ToolPreparationResult.BLOCKED_BY_DURABILITY;
        }
        int afterSlot = InventoryUtils.getSelectedSlot(player.getInventory());
        if (beforeSlot != afterSlot || stackFingerprintChanged(before, player.getMainHandItem())) {
            this.switchGuard.markSwitchIfNeeded(player.getMainHandItem());
            return ToolPreparationResult.SWITCHED_WAITING_SYNC;
        }
        return ToolPreparationResult.READY;
    }

    public boolean isDurabilityGuardActive() {
        return this.tweakeroo.isDurabilityGuardActive();
    }

    private static boolean stackFingerprintChanged(ItemStack before, ItemStack after) {
        if (before.isEmpty() || after.isEmpty()) {
            return before.isEmpty() != after.isEmpty();
        }
        return before.getItem() != after.getItem()
                || before.getDamageValue() != after.getDamageValue()
                || before.getCount() != after.getCount();
    }
}
