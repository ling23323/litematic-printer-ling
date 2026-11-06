package ling.printer.mixin.printer.mc;

import ling.printer.runtime.ClientBlockUpdateRouter;
import ling.printer.utils.minecraft.PredictiveAction;
import ling.printer.utils.minecraft.NetworkUtils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public abstract class MixinClientLevel implements NetworkUtils.SequenceExtension {

    //#if MC > 11802
    @Final
    @Shadow
    private net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler blockStatePredictionHandler;

    @Override
    public Packet<ServerGamePacketListener> ling3$predict(PredictiveAction action) {
        try (net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler pendingUpdateManager = this.blockStatePredictionHandler.startPredicting()) {
            return action.predict(pendingUpdateManager.currentSequence());
        }
    }

    @Inject(method = "syncBlockState", at = @At("RETURN"))
    private void observePredictionReconciliation(
            BlockPos pos,
            BlockState authoritativeState,
            Vec3 playerPosition,
            CallbackInfo ci
    ) {
        ClientBlockUpdateRouter.accept(pos, authoritativeState);
    }
    //#endif
}
