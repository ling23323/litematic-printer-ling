package ling.printer.mixin.printer.mc;

import ling.printer.interaction.MiningInteractionController;
import ling.printer.interaction.MiningInteractionPort;
import ling.printer.mixin_extension.BlockBreakResult;
import ling.printer.mixin_extension.MultiPlayerGameModeExtension;
import ling.printer.utils.minecraft.NetworkUtils;
import ling.printer.runtime.RuntimeAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Thin Minecraft field bridge. Mining policy and state transitions live outside the Mixin. */
// Priority rationale: the port must observe Tweakeroo/Litematica's final interaction decision;
// it delegates policy and only replaces behavior while an explicit printer mining session runs.
@Mixin(value = MultiPlayerGameMode.class, priority = 1020)
public abstract class MixinMultiPlayerGameMode implements MultiPlayerGameModeExtension, MiningInteractionPort {
    @Shadow private BlockPos destroyBlockPos;
    @Shadow private ItemStack destroyingItem;
    @Shadow private float destroyProgress;
    @Shadow private boolean isDestroying;
    @Shadow @Final private Minecraft minecraft;
    @Unique private MiningInteractionController ling$mining;

    @Shadow public abstract boolean destroyBlock(BlockPos pos);
    @Shadow protected abstract boolean sameDestroyTarget(BlockPos pos);
    @Shadow protected abstract void ensureHasSentCarriedItem();
    //#if MC > 11802
    @Shadow public abstract InteractionResult useItemOn(LocalPlayer player, InteractionHand hand, BlockHitResult hit);
    //#else
    //$$ @Shadow public abstract InteractionResult useItemOn(LocalPlayer player, ClientLevel level, InteractionHand hand, BlockHitResult hit);
    //#endif

    @Unique
    private MiningInteractionController ling$controller() {
        if (this.ling$mining == null) {
            this.ling$mining = new MiningInteractionController(
                    this,
                    RuntimeAccess.get().toolSwitchService()
            );
        }
        return this.ling$mining;
    }

    //#if MC >= 260100
    @Inject(method = "handleContainerInput", at = @At("HEAD"), cancellable = true)
    //#else
    //$$ @Inject(method = "handleInventoryMouseClick", at = @At("HEAD"), cancellable = true)
    //#endif
    private void printer$guardContainerSyncInput(CallbackInfo ci) {
        if (RuntimeAccess.get().containerSync().guardManualInput()
                || RuntimeAccess.get().chestTrackerAdapter().guardManualInput()) ci.cancel();
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void ling$tickMiningController(CallbackInfo ci) {
        this.ling$controller().tick();
    }

    @Override public void ling$resetRuntime() { this.ling$controller().reset(); }
    @Override public boolean ling$isPendingDelayedDestroy(BlockPos pos) {
        return this.ling$controller().isPendingDelayedDestroy(pos);
    }
    @Override public void ling$confirmServerBlockUpdate(BlockPos pos) {
        this.ling$controller().confirmServerBlockUpdate(pos);
    }
    @Override public BlockBreakResult ling$continueDestroyBlockForMine(
            BlockPos pos, Direction direction, boolean allowToolSwitch) {
        return this.ling$controller().continueForMine(pos, direction, allowToolSwitch);
    }
    @Override public BlockBreakResult ling$continueDestroyBlock(
            boolean localPrediction, BlockPos pos, Direction direction,
            boolean forceDelayedDestroy, boolean allowToolSwitch) {
        return this.ling$controller().continueDestroy(
                localPrediction, pos, direction, forceDelayedDestroy, allowToolSwitch);
    }

    @Override
    public InteractionResult ling$useItemOn(
            boolean localPrediction, InteractionHand hand, BlockHitResult hit) {
        if (localPrediction) {
            //#if MC > 11802
            return this.useItemOn(this.minecraft.player, hand, hit);
            //#else
            //$$ return this.useItemOn(this.minecraft.player, this.minecraft.level, hand, hit);
            //#endif
        }
        this.ensureHasSentCarriedItem();
        if (!this.minecraft.level.getWorldBorder().isWithinBounds(hit.getBlockPos())) return InteractionResult.FAIL;
        //#if MC > 11802
        NetworkUtils.sendPacket(sequence -> new ServerboundUseItemOnPacket(hand, hit, sequence));
        //#else
        //$$ NetworkUtils.sendPacket(sequence -> new ServerboundUseItemOnPacket(hand, hit));
        //#endif
        return InteractionResult.PASS;
    }

    @Override public Minecraft client() { return this.minecraft; }
    @Override public BlockPos destroyPos() { return this.destroyBlockPos; }
    @Override public void destroyPos(BlockPos pos) { this.destroyBlockPos = pos; }
    @Override public ItemStack destroyingItem() { return this.destroyingItem; }
    @Override public void destroyingItem(ItemStack stack) { this.destroyingItem = stack; }
    @Override public float destroyProgress() { return this.destroyProgress; }
    @Override public void destroyProgress(float progress) { this.destroyProgress = progress; }
    @Override public boolean isDestroying() { return this.isDestroying; }
    @Override public void isDestroying(boolean destroying) { this.isDestroying = destroying; }
    @Override public boolean matchesDestroyTarget(BlockPos pos) { return this.sameDestroyTarget(pos); }
    @Override public boolean isInventorySwitchPending() {
        return RuntimeAccess.get().inventorySwitchGuard().isWaiting();
    }
    @Override public void ensureCarriedItemSent() { this.ensureHasSentCarriedItem(); }

    @Override
    public ServerboundPlayerActionPacket actionPacket(
            ServerboundPlayerActionPacket.Action action, BlockPos pos, Direction direction, int sequence) {
        //#if MC > 11802
        return new ServerboundPlayerActionPacket(action, pos, direction, sequence);
        //#else
        //$$ return new ServerboundPlayerActionPacket(action, pos, direction);
        //#endif
    }
}
