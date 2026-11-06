package ling.printer.mixin_extension;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;

@SuppressWarnings("UnusedReturnValue")
public interface MultiPlayerGameModeExtension {
    InteractionResult ling$useItemOn(boolean localPrediction, InteractionHand hand, BlockHitResult blockHit);

    default BlockBreakResult ling$continueDestroyBlock(boolean localPrediction, BlockPos blockPos, Direction direction) {
        return this.ling$continueDestroyBlock(localPrediction, blockPos, direction, false);
    }

    default BlockBreakResult ling$continueDestroyBlock(boolean localPrediction, BlockPos blockPos, Direction direction, boolean forceDelayedDestroy) {
        return this.ling$continueDestroyBlock(localPrediction, blockPos, direction, forceDelayedDestroy, true);
    }

    BlockBreakResult ling$continueDestroyBlock(boolean localPrediction, BlockPos blockPos, Direction direction, boolean forceDelayedDestroy, boolean allowToolSwitch);

    default BlockBreakResult ling$continueDestroyBlockForMine(BlockPos blockPos, Direction direction) {
        return this.ling$continueDestroyBlockForMine(blockPos, direction, true);
    }

    default BlockBreakResult ling$continueDestroyBlockForMine(BlockPos blockPos, Direction direction, boolean allowToolSwitch) {
        return this.ling$continueDestroyBlock(false, blockPos, direction, false, allowToolSwitch);
    }

    default boolean ling$isPendingDelayedDestroy(BlockPos blockPos) {
        return false;
    }

    default void ling$confirmServerBlockUpdate(BlockPos blockPos) {
    }

    default void ling$resetRuntime() {
    }
}
