package ling.printer.mixin.printer.tweakeroo;

import ling.printer.config.Configs;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "fi.dy.masa.tweakeroo.event.InputHandler", remap = false)
public abstract class MixinInputHandler {
    @Redirect(
            method = "onMouseClick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;isCreative()Z"
            ),
            require = 0
    )
    private boolean ling$allowAngelBlockOutsideCreative(LocalPlayer player) {
        if (!Configs.Special.TWEAKEROO_ANGEL_BLOCK_MAY_BUILD.getBooleanValue()) {
            return player.isCreative();
        }

        return player.isCreative() || player.getAbilities().mayBuild;
    }
}
